package de.androbin.rpg.tile;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.util.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.event.EventQueue;

public class Tile {
  public final TileData data;
  
  private Event event;
  
  private Object reservation;
  
  public Tile( final TileData data ) {
    this.data = data;
  }
  
  public final Object interact( final Class< ? extends Entity> type, final Object ... args ) {
    return reservation instanceof Interactable
        ? ( (Interactable) reservation ).onInteract( type, args ) : null;
  }
  
  public boolean isPassable() {
    return data.passEvent != null && reservation == null;
  }
  
  public void render( final Graphics2D g, final Point pos, final float scale ) {
    final float px = pos.x * scale;
    final float py = pos.y * scale;
    
    drawImage( g, data.image, px, py, scale, scale );
  }
  
  public final void release() {
    reservation = null;
  }
  
  public final boolean request( final Object object ) {
    assert object != null;
    
    if ( reservation != object ) {
      if ( reservation == null ) {
        reservation = object;
      } else {
        return false;
      }
    }
    
    return true;
  }
  
  public final Class< ? > reservationType() {
    return reservation == null ? null : reservation.getClass();
  }
  
  public void setEvent( final Event event ) {
    this.event = event;
  }
  
  public void trigger( final EventQueue events, final Map<String, Object> args ) {
    args.put( "entity", reservation );
    
    if ( data.passEvent != null && data.passEvent != Event.NULL ) {
      events.enqueue( data.passEvent, args );
    }
    
    if ( event != null ) {
      events.enqueue( event, args );
    }
  }
  
  public interface Builder {
    Tile build( TileData data );
  }
}