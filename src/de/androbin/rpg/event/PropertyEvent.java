package de.androbin.rpg.event;

import de.androbin.json.*;
import java.util.logging.*;

public final class PropertyEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final Object entity = args[ 0 ];
    
    final String detailsRaw = (String) args[ 1 ];
    final XObject details = XUtil.parseJSON( detailsRaw ).asObject();
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