package de.androbin.rpg.event;

import de.androbin.json.*;
import java.util.logging.*;

public final class PropertyEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    final String entityRaw = (String) args[ 0 ];
    int entity;
    
    try {
      entity = Integer.parseInt( entityRaw );
    } catch ( final Exception e ) {
      entity = entityRaw.hashCode();
    }
    
    final String detailsRaw = (String) args[ 1 ];
    final XObject details = XUtil.parseJSON( detailsRaw ).asObject();
    return new PropertyEvent( entity, details );
  };
  
  public final int entity;
  public final XObject details;
  
  public PropertyEvent( final int entity, final XObject details ) {
    this.entity = entity;
    this.details = details;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.info( "property { entity: " + entity
        + ", details: " + details + " }" );
  }
}