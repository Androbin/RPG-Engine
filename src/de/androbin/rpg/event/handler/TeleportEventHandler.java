package de.androbin.rpg.event.handler;

import java.awt.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.tile.*;

public final class TeleportEventHandler implements Event.Handler<TeleportEvent> {
  @ Override
  public void handle( final RPGScreen master, final TeleportEvent event ) {
    final Entity entity = event.entity;
    final Ident world = event.world;
    final Point pos = event.pos;
    
    if ( world == null ) {
      final Tile tile = entity.world.getTile( pos );
      
      if ( tile == null || !tile.data.passable ) {
        return;
      }
      
      final Rectangle target = new Rectangle( pos, entity.data.size );
      final boolean success = entity.world.getSpaceTime( entity ).trySet( entity, target );
      
      if ( !success ) {
        return;
      }
      
      entity.orientation = entity.data.orientation;
      entity.pos = pos;
    } else {
      if ( entity == master.player ) {
        master.switchWorld( world, pos );
      }
    }
  }
}