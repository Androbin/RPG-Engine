package de.androbin.rpg.event.handler;

import java.util.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.tile.*;

public final class TileEnterEventHandler implements Event.Handler<TileEnterEvent> {
  @ Override
  public void handle( final RPGScreen master, final TileEnterEvent event ) {
    final Tile tile = event.tile;
    final Entity entity = event.entity;
    
    final Map<String, Object> values = new HashMap<>();
    values.put( "entity", entity );
    
    if ( tile.data.enterEvent != null ) {
      Events.QUEUE.enqueue( tile.data.enterEvent.apply( values ) );
    }
    
    if ( tile.enterEvent != null ) {
      Events.QUEUE.enqueue( tile.enterEvent.apply( values ) );
    }
  }
}