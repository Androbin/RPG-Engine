package de.androbin.rpg.event;

import de.androbin.rpg.*;
import java.util.logging.*;

public final class VisionEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    return new VisionEvent( (String) args[ 0 ] );
  };
  
  public final Ident world;
  
  public VisionEvent( final String world ) {
    this.world = Ident.parse( world );
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.fine( "vision { world: '" + world + "' }" );
  }
}