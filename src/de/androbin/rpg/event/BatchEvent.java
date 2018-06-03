package de.androbin.rpg.event;

import java.util.logging.*;

public final class BatchEvent implements Event {
  public final String name;
  public final Event[] parts;
  
  public BatchEvent( final String name, final Event[] parts ) {
    this.name = name;
    this.parts = parts;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.info( name );
  }
}