package de.androbin.rpg.tile;

import static de.androbin.gfx.util.GraphicsUtil.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.event.EventQueue;
import java.awt.*;
import java.util.*;

public class Tile {
  public final TileData data;
  
  private Event event;
  
  public Tile( final TileData data ) {
    this.data = data;
  }
  
  public boolean isPassable() {
    return data.event != null;
  }
  
  public void render( final Graphics2D g, final Point pos, final float scale ) {
    final float px = pos.x * scale;
    final float py = pos.y * scale;
    
    drawImage( g, data.image, px, py, scale, scale );
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
    Tile build( TileData data );
  }
}