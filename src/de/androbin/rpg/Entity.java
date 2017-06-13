package de.androbin.rpg;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import de.androbin.func.consume.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.tile.*;

public abstract class Entity implements Interactable, Sprite {
  // TODO(Saltuk) outsource to SpaceTime
  protected transient World world;
  
  private Point pos;
  
  public Direction viewDir;
  
  public Direction moveRequestDir;
  public BooleanConsumer moveRequestCallback;
  
  public Renderer renderer;
  
  private Direction moveDir;
  private float moveProgress;
  
  public Entity( final World world, final Point pos ) {
    this.world = world;
    this.pos = pos;
    this.viewDir = Direction.DOWN;
  }
  
  protected void reattach( final World world, final Point pos ) {
    this.pos = pos;
    this.world = world;
  }
  
  public final boolean canMove() {
    return canMove( viewDir );
  }
  
  public boolean canMove( final Direction dir ) {
    final Tile tile = nextTile( dir );
    return tile != null && tile.isPassable();
  }
  
  public final Object interact( final Object ... args ) {
    final Tile tile = nextTile();
    return tile == null ? null : tile.interact( getClass(), args );
  }
  
  private final void doMove( final RPGScreen screen ) {
    getTile().release();
    
    pos = new Point( pos );
    pos.translate( moveDir.dx, moveDir.dy );
    
    moveDir = null;
    
    final Map<String, Object> args = new HashMap<>();
    args.put( "screen", screen );
    
    getTile().trigger( screen.events, args );
  }
  
  @ Override
  public Rectangle getBounds() {
    final Dimension size = new Dimension( 1, 1 );
    return new Rectangle( pos, size );
  }
  
  @ Override
  public Rectangle2D.Float getViewBounds() {
    final float h = 1f; // TODO(Saltuk) infer from renderer
    return new Rectangle2D.Float( pos.x, pos.y + 1f - h, 1f, h );
  }
  
  public final Point2D.Float getFloatPos() {
    return moveDir == null ? new Point2D.Float( pos.x, pos.y )
        : new Point2D.Float( pos.x + moveDir.dx * moveProgress, pos.y + moveDir.dy * moveProgress );
  }
  
  public Direction getMoveDir() {
    return moveDir;
  }
  
  public float getMoveProgress() {
    return moveProgress;
  }
  
  private final Tile getTile() {
    return world.getTile( pos );
  }
  
  public Direction getViewDir() {
    return viewDir;
  }
  
  public final Point getPos() {
    return pos;
  }
  
  public boolean move( final Direction dir ) {
    viewDir = dir;
    
    if ( !canMove() ) {
      return false;
    }
    
    final Tile tile = nextTile();
    
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
  
  private final Tile nextTile() {
    return nextTile( viewDir );
  }
  
  private final Tile nextTile( final Direction dir ) {
    final Point tPos = new Point( pos.x + dir.dx, pos.y + dir.dy );
    
    return world.getTile( tPos );
  }
  
  @ Override
  public final void render( final Graphics2D g, final float scale ) {
    if ( renderer == null ) {
      return;
    }
    
    final float px = getFloatPos().x * scale;
    final float py = getFloatPos().y * scale;
    
    g.translate( px, py );
    renderer.setScale( scale );
    renderer.render( g );
    g.translate( -px, -py );
  }
  
  public final Class< ? > reservationType() {
    final Tile tile = nextTile();
    return tile == null ? null : tile.reservationType();
  }
  
  public final void turn( final Direction dir ) {
    viewDir = dir;
  }
  
  public void update( final float delta, final RPGScreen screen ) {
    if ( moveDir != null ) {
      moveProgress += delta * moveSpeed();
      
      while ( moveProgress >= 1f && moveDir != null ) {
        doMove( screen );
        moveProgress--;
      }
      
      if ( moveDir == null ) {
        moveProgress = 0f;
      }
    }
  }
}