package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;

public final class EventHandlers {
  private EventHandlers() {
  }
  
  public static Entity getEntity( final Master master, final Object raw ) {
    if ( raw instanceof Entity ) {
      return (Entity) raw;
    } else if ( raw instanceof String ) {
      return master.world.entities.findById( getId( (String) raw ) );
    } else {
      throw new InternalError();
    }
  }
  
  public static int getId( final String raw ) {
    try {
      return Integer.parseInt( raw );
    } catch ( final NumberFormatException e ) {
      return raw.hashCode();
    }
  }
}