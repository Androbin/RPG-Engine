package de.androbin.rpg;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.*;
import de.androbin.shell.*;
import de.androbin.shell.gfx.*;
import de.androbin.shell.input.*;

public abstract class RPGScreen extends BasicShell implements AWTGraphics {
  private final Map<Ident, World> worlds = new HashMap<>();
  
  protected World world;
  
  public Entity player;
  private Direction requestDir;
  
  protected WorldRenderer worldRenderer;
  
  protected final Camera camera;
  protected final Point2D.Float trans;
  
  protected float scale;
  
  public RPGScreen( final float scale ) {
    addKeyInput( new RPGKeyInput() );
    
    this.worldRenderer = new WorldRenderer();
    
    this.camera = new Camera();
    this.trans = new Point2D.Float();
    
    this.scale = scale;
  }
  
  private void calcTranslation() {
    final float pw = scale * world.size.width;
    final float ph = scale * world.size.height;
    
    trans.x = camera.calcTranslationX( getWidth(), pw, scale );
    trans.y = camera.calcTranslationY( getHeight(), ph, scale );
  }
  
  protected abstract World createWorld( Ident id );
  
  private Rectangle2D.Float getView() {
    final float startY = Math.max( 0f, -trans.y / scale );
    final float endY = Math.min( ( getHeight() - trans.y ) / scale, world.size.height );
    
    final float startX = Math.max( 0f, -trans.x / scale );
    final float endX = Math.min( ( getWidth() - trans.x ) / scale, world.size.width );
    
    return new Rectangle2D.Float( startX, startY, endX - startX, endY - startY );
  }
  
  protected final World getWorld( final Ident id ) {
    return worlds.computeIfAbsent( id, this::createWorld );
  }
  
  private boolean isAcceptingMoveRequest( final Direction dir ) {
    if ( !player.move.hasCurrent() ) {
      return true;
    }
    
    if ( player.move.getCurrent() != dir ) {
      return true;
    }
    
    return player.move.getProgress() >= 0.4f;
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    
    if ( world == null ) {
      return;
    }
    
    g.translate( trans.x, trans.y );
    worldRenderer.render( g, world, getView(), scale );
    g.translate( -trans.x, -trans.y );
  }
  
  public void switchWorld( final Ident id, final Point pos ) {
    if ( world != null ) {
      world.removeEntity( player );
    }
    
    world = getWorld( id );
    world.addEntity( player, pos );
  }
  
  @ Override
  public void update( final float delta ) {
    if ( player != null ) {
      final Direction dir = requestDir;
      
      if ( isAcceptingMoveRequest( dir ) ) {
        player.move.makeNext( dir );
      }
    }
    
    final List<Entity> entities = world.listEntities();
    
    for ( final Entity entity : entities ) {
      entity.update( delta );
    }
    
    Events.QUEUE.process( this );
    
    camera.update( delta );
    calcTranslation();
  }
  
  private final class RPGKeyInput implements KeyInput {
    @ Override
    public void keyPressed( final int keycode ) {
      final Direction dir = Directions.byKeyCode( keycode );
      
      if ( dir != null ) {
        requestDir = dir;
      }
    }
    
    @ Override
    public void keyReleased( final int keycode ) {
      final Direction dir = Directions.byKeyCode( keycode );
      
      if ( dir == requestDir ) {
        requestDir = null;
      }
    }
  };
}