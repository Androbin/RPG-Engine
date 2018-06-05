package de.androbin.rpg.event;

import java.util.logging.*;

public final class StoryEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final String id = (String) args[ 0 ];
    return new StoryEvent( id );
  };
  
  public final String id;
  
  public StoryEvent( final String id ) {
    this.id = id;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.finest( "story { id: '" + id + "' }" );
  }
}