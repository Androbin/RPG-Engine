package de.androbin.rpg.phantom;

import de.androbin.rpg.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.event.EventQueue;
import de.androbin.rpg.gfx.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Phantom implements Sprite {
  protected transient Renderer renderer;
  
  public final PhantomData data;
  private Point pos;
  
  private Event event;
  
  public Phantom( final PhantomData data ) {
    this.data = data;
    
    renderer = new PhantomRenderer( this );
  }
  
  public final void attach( final Point pos ) {
    this.pos = pos;
  }
  
  @ Override
  public Rectangle getBounds() {
    return new Rectangle( pos, data.size );
  }
  
  public final Point getPos() {
    return pos;
  }
  
  public final Renderer getRenderer() {
    return renderer;
  }
  
  @ Override
  public final Rectangle2D.Float getViewBounds() {
    return renderer == null ? null : renderer.getBounds( getPos() );
  }
  
  @ Override
  public void render( final Graphics2D g, final float scale ) {
    if ( renderer != null ) {
      renderer.render( g, getPos(), scale );
    }
  }
  
  public final void setEvent( final Event event ) {
    this.event = event;
  }
  
  public final void trigger( final EventQueue events, final Map<String, Object> args ) {
    if ( data.event != null ) {
      events.enqueue( data.event, args );
    }
    
    if ( event != null ) {
      events.enqueue( event, args );
    }
  }
  
  public interface Builder {
    Phantom build( PhantomData data );
  }
}