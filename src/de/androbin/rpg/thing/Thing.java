package de.androbin.rpg.thing;

import de.androbin.rpg.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.event.EventQueue;
import de.androbin.rpg.gfx.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Thing implements Sprite {
  protected transient Renderer renderer;
  
  public final ThingData data;
  public final int id;
  private Point pos;
  
  private Event event;
  
  public Thing( final ThingData data ) {
    this( data, 0 );
  }
  
  public Thing( final ThingData data, final int id ) {
    this.data = data;
    this.id = id;
    
    renderer = new ThingRenderer( this );
  }
  
  public final void attach( final Point pos ) {
    this.pos = pos;
  }
  
  @ Override
  public final Rectangle getBounds() {
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
    Thing build( ThingData data, int id );
  }
}