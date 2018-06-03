package de.androbin.rpg.event;

import de.androbin.rpg.*;
import java.util.logging.*;

public final class StoryEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final String id = (String) args[ 0 ];
    return new StoryEvent( id );
  };
  
  public final Ident id;
  
  public StoryEvent( final String id ) {
    this( Ident.fromSerial( id ) );
  }
  
  public StoryEvent( final Ident id ) {
    this.id = id;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.finest( "story { id: '" + id + "' }" );
  }
}