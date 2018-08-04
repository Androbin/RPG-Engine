package de.androbin.rpg;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.overlay.*;
import de.androbin.rpg.world.*;
import de.androbin.shell.*;
import de.androbin.shell.gfx.*;
import de.androbin.shell.input.lock.*;
import de.androbin.shell.input.tee.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.function.*;

public abstract class RPGScreen<M extends Master> extends AbstractShell implements AWTGraphics {
  public M master;
  
  protected WorldRenderer<World> worldRenderer;
  protected final Point2D.Float trans;
  protected float scale;
  
  private boolean silent;
  
  public RPGScreen() {
    // TODO: accumulate keyReleased and mouseReleased
    final Iterable<Overlay> source = () -> master.listOverlaysDown().iterator();
    final Predicate<Overlay> mask = overlay -> overlay.isMasking() || overlay.isFreezing();
    InputTees.putShellTee( getInputs(), source, mask );
    InputLocks.apply( getInputs(), () -> !silent );
    
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
  
  private Rectangle2D.Float calcView() {
    final Dimension size = master.world.size;
    
    final float startY = Math.max( 0f, -trans.y / scale );
    final float endY = Math.min( ( getHeight() - trans.y ) / scale, size.height );
    
    final float startX = Math.max( 0f, -trans.x / scale );
    final float endX = Math.min( ( getWidth() - trans.x ) / scale, size.width );
    
    return new Rectangle2D.Float( startX, startY, endX - startX, endY - startY );
  }
  
  @ Override
  public void onAfterResumed() {
    silent = false;
  }
  
  @ Override
  public void onBeforePaused() {
    silent = true;
  }
  
  @ Override
  protected void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    
    final Iterable<Overlay> overlays = master.listOverlaysUp();
    
    for ( final Overlay overlay : overlays ) {
      overlay.setSize( getWidth(), getHeight() );
    }
    
    if ( master.world != null ) {
      final AffineTransform savedTransform = g.getTransform();
      g.translate( trans.x, trans.y );
      
      final Rectangle2D.Float view = calcView();
      worldRenderer.render( g, master.world, view, scale );
      
      for ( final Overlay overlay : overlays ) {
        overlay.renderWorld( g, view, scale );
      }
      
      g.setTransform( savedTransform );
    }
    
    for ( final Overlay overlay : overlays ) {
      overlay.renderScreen( g );
    }
  }
  
  @ Override
  public void update( final float delta ) {
    boolean freeze = false;
    
    for ( final Overlay overlay : master.listOverlaysDown() ) {
      overlay.update( delta );
      
      if ( overlay.isFreezing() ) {
        freeze = true;
        break;
      }
    }
    
    if ( !freeze ) {
      final List<Agent> agents = master.world.entities.listAgents();
      
      for ( final Agent agent : agents ) {
        agent.update( delta );
      }
    }
    
    master.story.update();
    Events.QUEUE.process( master );
    master.cleanOverlays();
    
    master.camera.update( delta );
    calcTranslation();
  }
  
  @ Override
  public void updateUI( final float delta ) {
    for ( final Overlay overlay : master.listOverlaysUp() ) {
      overlay.updateUI( delta );
    }
  }
}