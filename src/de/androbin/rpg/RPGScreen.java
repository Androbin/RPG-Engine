package de.androbin.rpg;

import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.story.*;
import de.androbin.shell.*;
import de.androbin.shell.gfx.*;
import de.androbin.shell.input.supply.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public abstract class RPGScreen extends BasicShell implements AWTGraphics {
  private final Map<Ident, World> worlds = new HashMap<>();
  
  protected World world;
  
  protected StoryState story;
  
  public Agent player;
  private DirectionPair requestDir;
  
  protected WorldRenderer worldRenderer;
  
  private Overlay overlay;
  
  protected final Camera camera;
  protected final Point2D.Float trans;
  
  protected float scale;
  
  public RPGScreen( final StoryState story, final float scale ) {
    keyboardTee.mask = true;
    addKeyInput( KeyInputSupply.fromShell( () -> overlay ) );
    addKeyInput( new MoveKeyInput( () -> requestDir, dir -> requestDir = dir ) );
    
    this.story = story;
    
    worldRenderer = new SimpleWorldRenderer();
    
    camera = new Camera();
    trans = new Point2D.Float();
    
    this.scale = scale;
  }
  
  private void calcTranslation() {
    final Dimension size = world.size;
    
    final float pw = scale * size.width;
    final float ph = scale * size.height;
    
    trans.x = camera.calcTranslationX( getWidth(), pw, scale );
    trans.y = camera.calcTranslationY( getHeight(), ph, scale );
  }
  
  protected abstract World createWorld( Ident id );
  
  private Rectangle2D.Float getView() {
    final Dimension size = world.size;
    
    final float startY = Math.max( 0f, -trans.y / scale );
    final float endY = Math.min( ( getHeight() - trans.y ) / scale, size.height );
    
    final float startX = Math.max( 0f, -trans.x / scale );
    final float endX = Math.min( ( getWidth() - trans.x ) / scale, size.width );
    
    return new Rectangle2D.Float( startX, startY, endX - startX, endY - startY );
  }
  
  protected final World getWorld( final Ident id ) {
    return worlds.computeIfAbsent( id, this::createWorld );
  }
  
  private boolean isAcceptingMoveRequest( final DirectionPair dir ) {
    if ( dir == null ) {
      return false;
    }
    
    final MoveHandle move = player.move;
    final DirectionPair current = move.getCurrent();
    
    if ( current == null ) {
      return true;
    }
    
    if ( current.second != null || dir.second != null ) {
      return true;
    }
    
    if ( current.first != dir.first ) {
      return true;
    }
    
    return move.getProgress() >= 0.4f;
  }
  
  @ Override
  protected void onResized( final int width, final int height ) {
    if ( overlay != null ) {
      overlay.setSize( width, height );
    }
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
    
    if ( overlay != null ) {
      overlay.render( g );
    }
  }
  
  public void setOverlay( final Overlay overlay ) {
    overlay.setRunning( true );
    overlay.setSize( getWidth(), getHeight() );
    this.overlay = overlay;
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
      final DirectionPair dir = requestDir;
      
      if ( isAcceptingMoveRequest( dir ) ) {
        player.move.request( dir );
      }
    }
    
    final List<Entity> entities = world.listEntities();
    
    for ( final Entity entity : entities ) {
      entity.update( delta );
    }
    
    Events.QUEUE.process( this );
    story.update();
    
    camera.update( delta );
    calcTranslation();
    
    if ( overlay != null ) {
      if ( overlay.isRunning() ) {
        overlay.update( delta );
      } else {
        overlay = null;
      }
    }
  }
}