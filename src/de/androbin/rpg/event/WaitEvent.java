package de.androbin.rpg.event;

import java.util.logging.*;

public final class WaitEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final float duration = Float.parseFloat( (String) args[ 0 ] );
    return new WaitEvent( duration );
  };
  
  public final float duration;
  
  public WaitEvent( final float duration ) {
    this.duration = duration;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.info( "wait { duration: " + duration + " }" );
  }
}