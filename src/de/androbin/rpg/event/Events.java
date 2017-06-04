package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import java.util.*;
import de.androbin.util.*;

public final class Events {
  public static final Map<String, Event.Builder> BUILDERS = new HashMap<String, Event.Builder>();
  
  static {
    BUILDERS.put( "teleport", TeleportEvent.BUILDER );
  }
  
  private Events() {
  }
  
  public static Event parse( final String text ) {
    if ( text == null ) {
      return null;
    }
    
    if ( "none".equals( text ) ) {
      return Event.NULL;
    }
    
    final int l = text.indexOf( '(' );
    final int r = text.lastIndexOf( ')' );
    
    final String func = text.substring( 0, l );
    final String[] args = text.substring( l + 1, r ).split( ",\\s?" );
    
    final Event.Builder builder;
    
    if ( BUILDERS.containsKey( func ) ) {
      builder = BUILDERS.get( func );
      return builder.build( args );
    } else {
      final String[] events = JSONUtil.toStringArray( JSONUtil.parseJSON( func ) );
      return new CustomEvent( func,
          fillParallel( new Event[ events.length ], i -> parse( events[ i ] ) ) );
    }
  }
}