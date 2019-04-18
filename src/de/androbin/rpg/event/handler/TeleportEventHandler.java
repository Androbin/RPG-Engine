package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.overlay.*;
import de.androbin.rpg.space.*;
import de.androbin.rpg.world.*;
import java.awt.*;

public final class TeleportEventHandler implements Event.Handler<Master, TeleportEvent> {
  @ Override
  public Overlay handle( final Master master, final TeleportEvent event ) {
    final Entity entity = EventHandlers.getEntity( master, event.entity );
    final World world = EventHandlers.getWorld( master, event.world );
    final Point pos = event.pos;
    final DirectionPair orientation = event.orientation;
    
    final Spot spot = entity.getSpot();
    final World srcWorld = spot.world;
    final World destWorld = world == null ? srcWorld : world;
    
    srcWorld.entities.remove( entity );
    final boolean success = destWorld.entities.add( entity, pos );
    
    if ( !success ) {
      srcWorld.entities.add( entity, spot.getPos() );
      return null;
    }
    
    if ( entity instanceof Agent ) {
      final Agent agent = (Agent) entity;
      
      if ( orientation != null ) {
        agent.orientation = orientation;
      }
      
      if ( master.isPlayer( agent ) ) {
        master.world = destWorld;
      }
    }
    
    return null;
  }
}