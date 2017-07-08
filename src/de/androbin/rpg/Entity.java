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
  
  public Entity() {
    this( new Dimension( 1, 1 ) );
  }
  
  public Entity( final Dimension size ) {
    this.pos = new Point();
    this.size = size;
    this.viewDir = Direction.DOWN;
    
    move = new MoveHandle();
  }
  
  protected final void attach( final World world, final Point pos ) {
    this.world = world;
    this.pos = pos;
  }
  
  @ Override
  public final Rectangle getBounds() {
    return new Rectangle( pos, size );
  }
  
  public final Point2D.Float getFloatPos() {
    if ( move.hasCurrent() ) {
      final Direction dir = move.getCurrent();
      final float progress = move.getProgress();
      return dir.from( pos, progress );
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
    return renderer == null ? null : renderer.getBounds();
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
  
  @ Override
  public void render( final Graphics2D g, final float scale ) {
    if ( renderer == null ) {
      return;
    }
    
    renderer.setScale( scale );
    renderer.render( g );
  }
  
  public void updateStrong( final RPGScreen master ) {
    move.updateStrong( master );
  }
  
  public void updateWeak( final float delta ) {
    move.updateWeak( delta * moveSpeed() );
  }
  
  private final class MoveHandle extends Handle<Direction, Void> {
    private final Entity entity = Entity.this;
    
    @ Override
    public boolean canHandle( final Direction dir ) {
      final Tile tile = nextTile( dir );
      return tile != null && tile.isPassable();
    }
    
    @ Override
    protected Void doHandle( final RPGScreen master, final Direction dir ) {
      pos = dir.from( pos );
      
      final Rectangle target = new Rectangle( pos, size );
      world.strong.set( entity, target );
      
      final Map<String, Object> args = new HashMap<>();
      args.put( "entity", entity );
      
      getTile().trigger( master.events, args );
      return null;
    }
    
    @ Override
    protected boolean handle( final Direction dir ) {
      viewDir = dir;
      
      if ( !canHandle( dir ) ) {
        return false;
      }
      
      final Rectangle target = dir.expand( pos );
      return world.strong.trySet( entity, target );
    }
  }
}