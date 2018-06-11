package de.androbin.rpg;

import de.androbin.mixin.iter.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.overlay.*;
import de.androbin.rpg.world.*;
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
  
  protected WorldRenderer<World> worldRenderer;
  protected final Point2D.Float trans;
  protected float scale;
  
  public RPGScreen() {
    keyboardTee.mask = true;
    keyInputs.add( new KeyInputTee( new MixIterable<>( () -> new FilterIterator<>(
        new PipeIterator<>(
            master.overlays.iterator(),
            overlay -> overlay.getInputs().keyboard ),
        value -> value != null ) ) ) );
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
  
  private Rectangle2D.Float calcView() {
    final Dimension size = master.world.size;
    
    final float startY = Math.max( 0f, -trans.y / scale );
    final float endY = Math.min( ( getHeight() - trans.y ) / scale, size.height );
    
    final float startX = Math.max( 0f, -trans.x / scale );
    final float endX = Math.min( ( getWidth() - trans.x ) / scale, size.width );
    
    return new Rectangle2D.Float( startX, startY, endX - startX, endY - startY );
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    
    for ( final Overlay overlay : master.overlays ) {
      overlay.setSize( getWidth(), getHeight() );
    }
    
    if ( master.world != null ) {
      final AffineTransform savedTransform = g.getTransform();
      g.translate( trans.x, trans.y );
      
      final Rectangle2D.Float view = calcView();
      worldRenderer.render( g, master.world, view, scale );
      
      for ( final Overlay overlay : master.overlays ) {
        overlay.renderWorld( g, view, scale );
      }
      
      g.setTransform( savedTransform );
    }
    
    for ( final Overlay overlay : master.overlays ) {
      overlay.renderScreen( g );
    }
  }
  
  @ Override
  public void update( final float delta ) {
    final Agent player = master.getPlayer();
    
    if ( player != null ) {
      MoveKeyInput.applyRequest( player.move, requestDir );
    }
    
    final List<Agent> agents = master.world.entities.listAgents();
    
    for ( final Agent agent : agents ) {
      agent.update( delta );
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