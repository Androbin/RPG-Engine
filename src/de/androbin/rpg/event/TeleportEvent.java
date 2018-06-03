package de.androbin.rpg.event;

import java.awt.*;
import java.util.logging.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;

public final class TeleportEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final Entity entity = (Entity) args[ 0 ];
    
    switch ( args.length ) {
      case 4:
        return new TeleportEvent( entity, (String) args[ 1 ], new Point(
            Integer.parseInt( (String) args[ 2 ] ),
            Integer.parseInt( (String) args[ 3 ] ) ) );
      
      case 3:
        return new TeleportEvent( entity, new Point(
            Integer.parseInt( (String) args[ 1 ] ),
            Integer.parseInt( (String) args[ 2 ] ) ) );
    }
    
    return null;
  };
  
  public final Entity entity;
  public final Ident world;
  public final Point pos;
  
  public TeleportEvent( final Entity entity, final Point pos ) {
    this( entity, null, pos );
  }
  
  public TeleportEvent( final Entity entity, final String world, final Point pos ) {
    this.entity = entity;
    this.world = Ident.fromSerial( world );
    this.pos = pos;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.fine( "teleport { world: '" + world
        + "', pos: (" + pos.x + ", " + pos.y + ") }" );
  }
}