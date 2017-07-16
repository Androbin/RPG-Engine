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
  public final Point pos;
  
  private Event event;
  
  public Thing( final ThingData data, final Point pos ) {
    this( data, 0, pos );
  }
  
  public Thing( final ThingData data, final int id, final Point pos ) {
    this.data = data;
    this.id = id;
    this.pos = pos;
    
    renderer = new ThingRenderer( this );
  }
  
  @ Override
  public Rectangle getBounds() {
    return new Rectangle( pos, data.size );
  }
  
  @ Override
  public Rectangle2D.Float getViewBounds() {
    return renderer == null ? null : renderer.getBounds();
  }
  
  @ Override
  public void render( final Graphics2D g, final float scale ) {
    if ( renderer == null ) {
      return;
    }
    
    renderer.setScale( scale );
    renderer.render( g );
  }
  
  public void setEvent( final Event event ) {
    this.event = event;
  }
  
  public void trigger( final EventQueue events, final Map<String, Object> args ) {
    if ( data.event != null ) {
      events.enqueue( data.event, args );
    }
    
    if ( event != null ) {
      events.enqueue( event, args );
    }
  }
  
  public interface Builder {
    Thing build( ThingData data, int id, Point pos );
  }
}