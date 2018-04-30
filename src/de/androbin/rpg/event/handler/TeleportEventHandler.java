package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.tile.*;
import java.awt.*;

public final class TeleportEventHandler implements Event.Handler<TeleportEvent> {
  @ Override
  public void handle( final RPGScreen master, final TeleportEvent event ) {
    final Entity entity = event.entity;
    final Ident worldId = event.world;
    final Point pos = event.pos;
    
    if ( worldId == null ) {
      final World world = entity.getSpot().world;
      final Tile tile = world.getTile( pos );
      
      if ( tile == null || !tile.data.passable ) {
        return;
      }
      
      final Rectangle target = new Rectangle( pos, entity.data.size );
      final boolean success = world.getSpaceTime( entity ).trySet( entity, target );
      
      if ( !success ) {
        return;
      }
      
      entity.setSpot( new Spot( world, pos ) );
    } else {
      if ( entity == master.player ) {
        master.switchWorld( worldId, pos );
      }
    }
  }
}