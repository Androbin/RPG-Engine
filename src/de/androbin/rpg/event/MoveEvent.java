package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import de.androbin.rpg.dir.*;
import java.util.*;
import java.util.logging.*;

public final class MoveEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final Object agent = args[ 0 ];
    
    final Direction[] dirs = fill( new Direction[ args.length - 1 ], i -> {
      final String name = (String) args[ i + 1 ];
      return Direction.valueOf( name.toUpperCase() );
    } );
    
    return new MoveEvent( agent, dirs );
  };
  
  public final Object agent;
  public final Direction[] dirs;
  
  public MoveEvent( final Object agent, final Direction ... dirs ) {
    this.agent = agent;
    this.dirs = dirs;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.finest( "move { agent: " + agent
        + ", dirs: " + Arrays.toString( dirs ) + " }" );
  }
}