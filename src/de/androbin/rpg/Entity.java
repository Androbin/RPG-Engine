package de.androbin.rpg;

import de.androbin.rpg.gfx.*;
import de.androbin.rpg.tile.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public abstract class Entity implements Sprite {
  protected transient World world;
  protected transient Renderer renderer;
  
  private Point pos;
  public final Dimension size;
  
  public Direction viewDir;
  
  public final Handle<Direction, Void> move;
  
  public Entity( final World world, final Point pos ) {
    this.world = world;
    this.pos = pos;
    this.size = new Dimension( 1, 1 );
    this.viewDir = Direction.DOWN;
    
    move = new Handle<Direction, Void>() {
      @ Override
      protected boolean canHandle( final Direction dir ) {
        return canMove( dir );
      }
      
      @ Override
      protected Void doHandle( final RPGScreen master, final Direction dir ) {
        doMove( master, dir );
        return null;
      }
      
      @ Override
      protected boolean handle( final Direction dir ) {
        return move( dir );
      }
    };
  }
  
  public boolean canMove( final Direction dir ) {
    final Tile tile = nextTile( dir );
    return tile != null && tile.isPassable();
  }
  
  private void doMove( final RPGScreen master, final Direction dir ) {
    pos = dir.from( pos );
    
    final Rectangle target = new Rectangle( pos, size );
    world.strong.set( this, target );
    
    final Map<String, Object> args = new HashMap<>();
    args.put( "entity", this );
    
    getTile().trigger( master.events, args );
  }
  
  @ Override
  public final Rectangle getBounds() {
    return new Rectangle( pos, size );
  }
  
  public final Point2D.Float getFloatPos() {
    if ( move.hasCurrent() ) {
      final Direction dir = move.getCurrent();
      final float progress = move.getProgress();
      return new Point2D.Float( pos.x + dir.dx * progress, pos.y + dir.dy * progress );
    } else {
      return new Point2D.Float( pos.x, pos.y );
    }
  }
  
  public final Point getPos() {
    return pos;
  }
  
  private Tile getTile() {
    return world.getTile( pos );
  }
  
  @ Override
  public final Rectangle2D.Float getViewBounds() {
    final float h = 1f; // TODO infer from renderer
    return new Rectangle2D.Float( pos.x, pos.y + size.height - h, size.width, h );
  }
  
  private boolean move( final Direction dir ) {
    viewDir = dir;
    
    if ( !canMove( dir ) ) {
      return false;
    }
    
    final Rectangle target = dir.expand( pos );
    return world.strong.trySet( this, target );
  }
  
  public abstract float moveSpeed();
  
  public final boolean moveTo( final Point pos ) {
    final Tile tile = world.getTile( pos );
    
    if ( tile == null || !tile.isPassable() ) {
      return false;
    }
    
    final Rectangle target = new Rectangle( pos, size );
    final boolean success = world.strong.trySet( this, target );
    
    if ( !success ) {
      return false;
    }
    
    this.viewDir = Direction.DOWN;
    this.pos = pos;
    return true;
  }
  
  private Tile nextTile( final Direction dir ) {
    return world.getTile( dir.from( pos ) );
  }
  
  protected final void reattach( final World world, final Point pos ) {
    this.pos = pos;
    this.world = world;
  }
  
  @ Override
  public void render( final Graphics2D g, final float scale ) {
    if ( renderer == null ) {
      return;
    }
    
    final Point2D.Float pos = getFloatPos();
    final float px = pos.x * scale;
    final float py = pos.y * scale;
    
    g.translate( px, py );
    renderer.setScale( scale );
    renderer.render( g );
    g.translate( -px, -py );
  }
  
  public void updateStrong( final RPGScreen master ) {
    move.updateStrong( master );
  }
  
  public void updateWeak( final float delta ) {
    move.updateWeak( delta * moveSpeed() );
  }
}