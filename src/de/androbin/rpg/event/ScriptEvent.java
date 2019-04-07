package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import de.androbin.rpg.*;
import java.util.logging.*;

public final class ScriptEvent implements Event {
  public final String name;
  public final Event[][] script;
  public final Intervention intervention;
  
  public ScriptEvent( final Event ... script ) {
    this( fill( new Event[ script.length ][], i -> new Event[] { script[ i ] } ) );
  }
  
  public ScriptEvent( final Event[][] script ) {
    this( null, script, Intervention.TRANSPARENT );
  }
  
  public ScriptEvent( final String name, final Event[][] script, final Intervention intervention ) {
    this.name = name;
    this.script = script;
    this.intervention = intervention;
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