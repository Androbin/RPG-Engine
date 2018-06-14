package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import de.androbin.rpg.dir.*;
import java.util.*;
import java.util.logging.*;

public final class MoveEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final String agentRaw = (String) args[ 0 ];
    int agent;
    
    try {
      agent = Integer.parseInt( agentRaw );
    } catch ( final Exception e ) {
      agent = agentRaw.hashCode();
    }
    
    final Direction[] dirs = fill( new Direction[ args.length - 1 ], i -> {
      final String name = (String) args[ i + 1 ];
      return Direction.valueOf( name.toUpperCase() );
    } );
    return new MoveEvent( agent, dirs );
  };
  
  public final int agent;
  public final Direction[] dirs;
  
  public MoveEvent( final int agent, final Direction ... dirs ) {
    this.agent = agent;
    this.dirs = dirs;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.finest( "move { agent: " + agent
        + ", dirs: " + Arrays.toString( dirs ) + " }" );
  }
}