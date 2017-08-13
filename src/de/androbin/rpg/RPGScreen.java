package de.androbin.rpg;

import de.androbin.rpg.event.EventQueue;
import de.androbin.rpg.gfx.*;
import de.androbin.shell.*;
import de.androbin.shell.gfx.*;
import de.androbin.shell.input.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public abstract class RPGScreen extends BasicShell implements AWTGraphics {
  private final Map<Identifier, World> worlds = new HashMap<>();
  
  protected World world;
  
  public Entity player;
  private Direction requestDir;
  
  protected final Camera camera;
  protected final EventQueue events;
  protected final Point2D.Float trans;
  
  protected float scale;
  
  public RPGScreen( final float scale ) {
    addKeyInput( new RPGKeyInput() );
    
    this.camera = new Camera();
    this.events = new EventQueue();
    this.trans = new Point2D.Float();
    
    this.scale = scale;
  }
  
  private void calcTranslation() {
    final float pw = scale * world.size.width;
    final float ph = scale * world.size.height;
    
    trans.x = camera.calcTranslationX( getWidth(), pw, scale );
    trans.y = camera.calcTranslationY( getHeight(), ph, scale );
  }
  
  protected abstract World createWorld( Identifier id );
  
  protected final World getWorld( final Identifier id ) {
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
    
    final float startY = Math.max( 0f, -trans.y / scale );
    final float endY = Math.min( ( getHeight() - trans.y ) / scale, world.size.height );
    
    final float startX = Math.max( 0f, -trans.x / scale );
    final float endX = Math.min( ( getWidth() - trans.x ) / scale, world.size.width );
    
    world.render( g, new Rectangle2D.Float( startX, startY, endX - startX, endY - startY ), scale );
    
    g.translate( -trans.x, -trans.y );
  }
  
  public void switchWorld( final Identifier id, final Point pos ) {
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
        player.move.request( dir );
      }
    }
    
    final List<Entity> entities = world.listEntities();
    
    for ( final Entity entity : entities ) {
      entity.updateWeak( delta );
    }
    
    for ( final Entity entity : entities ) {
      entity.updateStrong( this );
    }
    
    events.run( this );
    
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