package de.androbin.rpg.event.handler;

import java.util.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;
import de.androbin.rpg.tile.*;

public final class TileEnterEventHandler implements Event.Handler<Master, TileEnterEvent> {
  @ Override
  public Overlay handle( final Master master, final TileEnterEvent event ) {
    final Tile tile = event.tile;
    final Entity entity = event.entity;
    
    final Map<String, Object> values = new HashMap<>();
    values.put( "entity", entity );
    
    Events.QUEUE.enqueue( tile.getData().enterEvent, values );
    Events.QUEUE.enqueue( Events.parse( tile.enterEvent ), values );
    return null;
  }
}