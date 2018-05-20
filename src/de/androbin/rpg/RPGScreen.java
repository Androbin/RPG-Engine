package de.androbin.rpg;

import de.androbin.mixin.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.*;
import de.androbin.shell.*;
import de.androbin.shell.gfx.*;
import de.androbin.shell.input.tee.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public abstract class RPGScreen<M extends Master> extends BasicShell implements AWTGraphics {
  public M master;
  private DirectionPair requestDir;
  
  protected WorldRenderer worldRenderer;
  protected final Point2D.Float trans;
  protected float scale;
  
  public RPGScreen() {
    keyboardTee.mask = true;
    keyInputs.add( new KeyInputTee( new MixIterable<>( () -> new PipeIterator<>(
        master.overlays.iterator(),
        overlay -> overlay.getInputs().keyboard ) ) ) );
    keyInputs.add( new MoveKeyInput( () -> requestDir, dir -> requestDir = dir ) );
    
    worldRenderer = new SimpleWorldRenderer();
    
    trans = new Point2D.Float();
  }
  
  private void calcTranslation() {
    final Dimension size = master.world.size;
    
    final float pw = scale * size.width;
    final float ph = scale * size.height;
    
    trans.x = master.camera.calcTranslationX( getWidth(), pw, scale );
    trans.y = master.camera.calcTranslationY( getHeight(), ph, scale );
  }
  
  private Rectangle2D.Float getView() {
    final Dimension size = master.world.size;
    
    final float startY = Math.max( 0f, -trans.y / scale );
    final float endY = Math.min( ( getHeight() - trans.y ) / scale, size.height );
    
    final float startX = Math.max( 0f, -trans.x / scale );
    final float endX = Math.min( ( getWidth() - trans.x ) / scale, size.width );
    
    return new Rectangle2D.Float( startX, startY, endX - startX, endY - startY );
  }
  
  private boolean isAcceptingMoveRequest( final DirectionPair dir ) {
    final MoveHandle move = master.player.move;
    final DirectionPair current = move.getCurrent();
    
    if ( current == null || dir == null ) {
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
  public void render( final Graphics2D g ) {
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    
    if ( master.world == null ) {
      return;
    }
    
    g.translate( trans.x, trans.y );
    worldRenderer.render( g, master.world, getView(), scale );
    g.translate( -trans.x, -trans.y );
    
    for ( final Overlay overlay : master.overlays ) {
      overlay.setSize( getWidth(), getHeight() );
      overlay.render( g );
    }
  }
  
  @ Override
  public void update( final float delta ) {
    if ( master.player != null ) {
      final DirectionPair dir = requestDir;
      
      if ( isAcceptingMoveRequest( dir ) ) {
        master.player.move.request( dir );
      }
    }
    
    final List<Entity> entities = master.world.entities.list();
    
    for ( final Entity entity : entities ) {
      entity.update( delta );
    }
    
    Events.QUEUE.process( master );
    master.story.update();
    
    master.camera.update( delta );
    calcTranslation();
    
    for ( final Iterator<Overlay> iter = master.overlays.iterator(); iter.hasNext(); ) {
      final Overlay overlay = iter.next();
      
      if ( overlay.isRunning() ) {
        overlay.update( delta );
      } else {
        iter.remove();
      }
    }
  }
}