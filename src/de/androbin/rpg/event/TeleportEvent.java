package de.androbin.rpg.event;

import de.androbin.rpg.dir.*;
import java.awt.*;
import java.util.logging.*;

public final class TeleportEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final Object entity = args[ 0 ].raw();
    final String world = args[ 1 ].asString();
    final Point pos = new Point( args[ 2 ].asInt(), args[ 3 ].asInt() );
    final DirectionPair orientation = DirectionPair.parse( args[ 4 ].asString() );
    
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