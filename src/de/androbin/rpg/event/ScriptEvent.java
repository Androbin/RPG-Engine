package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import java.util.*;
import java.util.logging.*;

public final class ScriptEvent implements Event {
  public final String name;
  public final boolean masking;
  public final Event[][] script;
  
  public ScriptEvent( final Event ... script ) {
    this( fill( new Event[ script.length ][], i -> new Event[] { script[ i ] } ) );
  }
  
  public ScriptEvent( final Event[][] script ) {
    this( null, false, script );
  }
  
  public ScriptEvent( final String name, final boolean masking, final Event[][] script ) {
    this.name = name;
    this.masking = masking;
    this.script = script;
  }
  
  @ Override
  public void log( final Logger logger ) {
    if ( name == null ) {
      logger.finest( "script" );
    } else {
      logger.info( "script { name: '" + name + "' }" );
    }
  }
  
  @ FunctionalInterface
  interface Static {
    Event[][] compile( Map<String, Object> values );
  }
}