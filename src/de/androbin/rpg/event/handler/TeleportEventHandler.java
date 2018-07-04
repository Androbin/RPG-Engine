package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.overlay.*;
import de.androbin.rpg.world.*;
import java.awt.*;

public final class TeleportEventHandler implements Event.Handler<Master, TeleportEvent> {
  @ Override
  public Overlay handle( final Master master, final TeleportEvent event ) {
    final Entity entity = EventHandlers.getEntity( master, event.entity );
    final Ident worldId = event.world;
    final Point pos = event.pos;
    
    final Spot spot = entity.getSpot();
    final World srcWorld = spot.world;
    
    if ( worldId == null ) {
      final Rectangle target = new Rectangle( pos, entity.getData().size );
      final boolean success = srcWorld.entities.tryMove( entity, target );
      
      if ( !success ) {
        return null;
      }
      
      entity.setSpot( new Spot( srcWorld, pos ) );
    } else {
      final World destWorld = master.getWorld( worldId );
      
      srcWorld.entities.remove( entity );
      final boolean success = destWorld.entities.add( entity, pos );
      
      if ( !success ) {
        srcWorld.entities.add( entity, spot.getPos() );
        return null;
      }
      
      if ( entity instanceof Agent ) {
        final Agent agent = (Agent) entity;
        
        if ( master.isPlayer( agent ) ) {
          master.world = destWorld;
        }
      }
    }
    
    return null;
  }
}