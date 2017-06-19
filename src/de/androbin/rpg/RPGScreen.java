package de.androbin.rpg;

import de.androbin.game.*;
import de.androbin.rpg.event.EventQueue;
import de.androbin.rpg.gfx.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public abstract class RPGScreen extends Screen {
  private final Map<String, World> worlds = new HashMap<>();
  
  protected World world;
  
  public Entity player;
  private Direction requestDir;
  
  protected final Camera camera;
  protected final EventQueue events;
  protected final Point2D.Float trans;
  
  protected float scale;
  
  public RPGScreen( final Game game, final float scale ) {
    super( game );
    
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
  
  protected abstract World createWorld( String name );
  
  @ Override
  public KeyListener getKeyListener() {
    return new KeyAdapter() {
      @ Override
      public void keyPressed( final KeyEvent event ) {
        final int keycode = event.getKeyCode();
        final Direction dir = Directions.byKeyCode( keycode );
        
        if ( dir != null ) {
          requestDir = dir;
        }
      }
      
      @ Override
      public void keyReleased( final KeyEvent event ) {
        final int keycode = event.getKeyCode();
        final Direction dir = Directions.byKeyCode( keycode );
        
        if ( dir == requestDir ) {
          requestDir = null;
        }
      }
    };
  }
  
  protected final World getWorld( final String name ) {
    return worlds.computeIfAbsent( name, this::createWorld );
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
  
  public void switchWorld( final String name, final Point pos ) {
    if ( world != null ) {
      world.removeEntity( player );
    }
    
    world = getWorld( name );
    player.reattach( world, pos );
    world.addEntity( player );
  }
  
  @ Override
  protected void update( final float delta ) {
    if ( player != null && requestDir != null ) {
      player.move.request( requestDir );
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
}