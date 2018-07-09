package de.androbin.rpg.event;

import de.androbin.rpg.dir.*;
import java.awt.*;
import java.util.logging.*;

public final class TeleportEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final Object entity = args[ 0 ];
    final String world = (String) args[ 1 ];
    final Point pos = new Point(
        Integer.parseInt( (String) args[ 2 ] ),
        Integer.parseInt( (String) args[ 3 ] ) );
    final DirectionPair orientation = DirectionPair.parse( (String) args[ 4 ] );
    
    return new TeleportEvent( entity, world, pos, orientation );
  };
  
  public final Object entity;
  public final Object world;
  public final Point pos;
  public final DirectionPair orientation;
  
  public TeleportEvent( final Object entity, final Object world, final Point pos,
      final DirectionPair orientation ) {
    this.entity = entity;
    this.world = world;
    this.pos = pos;
    this.orientation = orientation;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.fine( "teleport { world: '" + world
        + "', pos: (" + pos.x + ", " + pos.y
        + "), orientation: " + orientation + " }" );
  }
}