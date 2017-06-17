package de.androbin.rpg;

import de.androbin.func.*;
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
  
  private Direction moveDir;
  private float moveProgress;
  public Runnable moveCallback;
  
  public Direction moveRequestDir;
  public BooleanConsumer moveRequestCallback;
  
  public Entity( final World world, final Point pos ) {
    this.world = world;
    this.pos = pos;
    this.size = new Dimension( 1, 1 );
    this.viewDir = Direction.DOWN;
  }
  
  public final boolean canMove( final Direction dir ) {
    final Tile tile = nextTile( dir );
    return tile != null && tile.isPassable();
  }
  
  private final void doMove( final RPGScreen screen ) {
    pos = moveDir.from( pos );
    moveDir = null;
    
    final Rectangle target = new Rectangle( pos, size );
    world.spaceTime.set( this, target );
    
    final Map<String, Object> args = new HashMap<>();
    args.put( "screen", screen );
    args.put( "entity", this );
    
    getTile().trigger( screen.events, args );
    
    if ( moveCallback != null ) {
      moveCallback.run();
    }
  }
  
  @ Override
  public final Rectangle getBounds() {
    return new Rectangle( pos, size );
  }
  
  public final Point2D.Float getFloatPos() {
    return moveDir == null ? new Point2D.Float( pos.x, pos.y )
        : new Point2D.Float( pos.x + moveDir.dx * moveProgress, pos.y + moveDir.dy * moveProgress );
  }
  
  public final float getMoveProgress() {
    return moveProgress;
  }
  
  public final Point getPos() {
    return pos;
  }
  
  private final Tile getTile() {
    return world.getTile( pos );
  }
  
  @ Override
  public final Rectangle2D.Float getViewBounds() {
    final float h = 1f; // TODO infer from renderer
    return new Rectangle2D.Float( pos.x, pos.y + 1f - h, size.width, h );
  }
  
  private final boolean move( final Direction dir ) {
    viewDir = dir;
    
    if ( !canMove( dir ) ) {
      return false;
    }
    
    final Rectangle target = dir.expand( pos );
    final boolean success = world.spaceTime.trySet( this, target );
    
    if ( !success ) {
      return false;
    }
    
    moveDir = dir;
    return true;
  }
  
  public abstract float moveSpeed();
  
  public final boolean moveTo( final Point pos ) {
    final Tile tile = world.getTile( pos );
    
    if ( tile == null || !tile.isPassable() ) {
      return false;
    }
    
    final Rectangle target = new Rectangle( pos, size );
    final boolean success = world.spaceTime.trySet( this, target );
    
    if ( !success ) {
      return false;
    }
    
    this.viewDir = Direction.DOWN;
    this.pos = pos;
    return true;
  }
  
  private final Tile nextTile( final Direction dir ) {
    return world.getTile( dir.from( pos ) );
  }
  
  public final void processMove( final RPGScreen screen ) {
    if ( moveDir == null ) {
      if ( moveRequestDir == null ) {
        moveProgress = 0f;
      } else {
        final boolean success = move( moveRequestDir );
        moveRequestDir = null;
        
        if ( moveRequestCallback != null ) {
          moveRequestCallback.accept( success );
          moveRequestCallback = null;
        }
      }
    } else if ( moveProgress >= 1f ) {
      doMove( screen );
      moveProgress--;
    }
  }
  
  protected final void reattach( final World world, final Point pos ) {
    this.pos = pos;
    this.world = world;
  }
  
  @ Override
  public final void render( final Graphics2D g, final float scale ) {
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
  
  public void update( final float delta ) {
    if ( moveDir != null ) {
      moveProgress += delta * moveSpeed();
    }
  }
}