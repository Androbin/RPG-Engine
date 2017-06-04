package de.androbin.rpg;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import de.androbin.game.*;
import de.androbin.rpg.event.EventQueue;
import de.androbin.rpg.gfx.*;

public class RPGScreen extends Screen {
  private final Map<String, World> worlds = new HashMap<>();
  
  public World world;
  public Entity player;
  
  public final Camera camera;
  
  public final EventQueue events;
  
  private float dx;
  private float dy;
  
  public float scale;
  
  public RPGScreen( final Game game, final float scale ) {
    super( game );
    
    this.camera = new Camera();
    
    this.events = new EventQueue();
    
    this.scale = scale;
  }
  
  private void calcTranslation() {
    final float pw = scale * world.size.width;
    final float ph = scale * world.size.height;
    
    dx = camera.calcTranslationX( getWidth(), pw, scale );
    dy = camera.calcTranslationY( getHeight(), ph, scale );
  }
  
  protected void checkMoveRequest( final Entity entity ) {
    if ( entity.getMoveDir() == null && entity.moveRequestDir != null ) {
      // TODO bundle in Request class
      
      final boolean result = entity.move( entity.moveRequestDir );
      entity.moveRequestDir = null;
      
      if ( entity.moveRequestCallback != null ) {
        entity.moveRequestCallback.accept( result );
        entity.moveRequestCallback = null;
      }
    }
  }
  
  protected World createWorld( final String name ) {
    return null;
  }
  
  @ Override
  public KeyListener getKeyListener() {
    return new KeyAdapter() {
      @ Override
      public void keyPressed( final KeyEvent event ) {
        final int keycode = event.getKeyCode();
        final Direction dir = Direction.byKeyCode( keycode );
        
        if ( dir != null ) {
          player.moveRequestDir = dir;
        }
      }
    };
  }
  
  public float getTranslationX() {
    return dx;
  }
  
  public float getTranslationY() {
    return dy;
  }
  
  private final World getWorld( final String name ) {
    return worlds.computeIfAbsent( name, this::createWorld );
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    
    if ( world == null ) {
      return;
    }
    
    g.translate( dx, dy );
    
    final float startY = Math.max( 0f, -dy / scale );
    final float endY = Math.min( ( getHeight() - dy ) / scale, world.size.height );
    
    final float startX = Math.max( 0f, -dx / scale );
    final float endX = Math.min( ( getWidth() - dx ) / scale, world.size.width );
    
    world.render( g, new Rectangle2D.Float( startX, startY, endX - startX, endY - startY ), scale );
    
    g.translate( -dx, -dy );
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
  protected void onResized( final int width, final int height ) {
  }
  
  @ Override
  protected void update( final float delta ) {
    if ( world == null ) {
      return;
    }
    
    for ( final Entity entity : world.listEntities() ) {
      entity.update( delta, this );
    }
    
    for ( final Entity entity : world.listEntities() ) {
      checkMoveRequest( entity );
    }
    
    events.run();
    
    calcTranslation();
    camera.update( delta );
  }
}