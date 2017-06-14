package de.androbin.rpg;

import de.androbin.func.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.tile.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public abstract class Entity implements Interactable, Sprite {
  protected transient World world;
  
  protected Renderer renderer;
  
  private Point pos;
  
  public Direction viewDir;
  
  private Direction moveDir;
  private float moveProgress;
  public Runnable moveCallback;
  
  public Direction moveRequestDir;
  public BooleanConsumer moveRequestCallback;
  
  public Entity( final World world, final Point pos ) {
    this.world = world;
    this.pos = pos;
    this.viewDir = Direction.DOWN;
  }
  
  public boolean canMove( final Direction dir ) {
    final Tile tile = nextTile( dir );
    return tile != null && tile.isPassable();
  }
  
  private final void doMove( final RPGScreen screen ) {
    getTile().release();
    
    pos = new Point( pos );
    pos.translate( moveDir.dx, moveDir.dy );
    
    moveDir = null;
    
    final Map<String, Object> args = new HashMap<>();
    args.put( "screen", screen );
    
    getTile().trigger( screen.events, args );
    
    if ( moveCallback != null ) {
      moveCallback.run();
    }
  }
  
  @ Override
  public Rectangle getBounds() {
    // TODO handle movement for larger sizes
    final Dimension size = new Dimension( 1, 1 );
    return new Rectangle( pos, size );
  }
  
  public final Point2D.Float getFloatPos() {
    return moveDir == null ? new Point2D.Float( pos.x, pos.y )
        : new Point2D.Float( pos.x + moveDir.dx * moveProgress, pos.y + moveDir.dy * moveProgress );
  }
  
  public final Direction getMoveDir() {
    return moveDir;
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
  public Rectangle2D.Float getViewBounds() {
    final float h = 1f; // TODO(Saltuk) infer from renderer
    return new Rectangle2D.Float( pos.x, pos.y + 1f - h, 1f, h );
  }
  
  public final Object interact( final Object ... args ) {
    final Tile tile = nextTile( viewDir );
    return tile == null ? null : tile.interact( getClass(), args );
  }
  
  private final boolean move( final Direction dir ) {
    viewDir = dir;
    
    if ( !canMove( dir ) ) {
      return false;
    }
    
    final Tile tile = nextTile( viewDir );
    
    if ( !tile.request( this ) ) {
      return false;
    }
    
    moveDir = dir;
    return true;
  }
  
  public abstract float moveSpeed();
  
  public boolean moveTo( final Point pos ) {
    final Tile target = world.getTile( pos );
    
    if ( target == null || !target.isPassable() || !target.request( this ) ) {
      return false;
    }
    
    viewDir = Direction.DOWN;
    
    getTile().release();
    
    this.pos = pos;
    
    return true;
  }
  
  private final Tile nextTile( final Direction dir ) {
    return world.getTile( new Point( pos.x + dir.dx, pos.y + dir.dy ) );
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
  
  protected void reattach( final World world, final Point pos ) {
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
  
  public final Class< ? > reservationType() {
    final Tile tile = nextTile( viewDir );
    return tile == null ? null : tile.reservationType();
  }
  
  public void update( final float delta ) {
    if ( moveDir != null ) {
      moveProgress += delta * moveSpeed();
    }
  }
}