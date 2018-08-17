package de.androbin.rpg.event;

import de.androbin.json.*;
import java.util.logging.*;

public final class PropertyEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final Object entity = args[ 0 ].raw();
    final XObject details = args[ 1 ].asObject();
    return new PropertyEvent( entity, details );
  };
  
  public final Object entity;
  public final XObject details;
  
  public PropertyEvent( final Object entity, final XObject details ) {
    this.entity = entity;
    this.details = details;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.info( "property { entity: " + entity
        + ", details: " + details + " }" );
  }
}