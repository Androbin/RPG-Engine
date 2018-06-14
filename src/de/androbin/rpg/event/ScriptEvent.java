package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import java.util.logging.*;

public final class ScriptEvent implements Event {
  public final String name;
  public final Event[][] script;
  
  public ScriptEvent( final String name, final Event ... script ) {
    this( name, fill( new Event[ script.length ][], i -> new Event[] { script[ i ] } ) );
  }
  
  public ScriptEvent( final String name, final Event[][] script ) {
    this.name = name;
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
}