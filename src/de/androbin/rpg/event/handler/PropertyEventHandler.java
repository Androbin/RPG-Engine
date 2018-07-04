package de.androbin.rpg.event.handler;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class PropertyEventHandler implements Event.Handler<Master, PropertyEvent> {
  @ Override
  public Overlay handle( final Master master, final PropertyEvent event ) {
    final Entity entity = EventHandlers.getEntity( master, event.entity );
    final XObject details = event.details;
    
    entity.load( details );
    return null;
  }
}