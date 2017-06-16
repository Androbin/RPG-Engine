package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import java.util.*;
import de.androbin.util.*;

public final class Events {
  public static final Map<String, Event.Builder> BUILDERS = new HashMap<>();
  
  static {
    BUILDERS.put( "teleport", TeleportEvent.BUILDER );
  }
  
  private Events() {
  }
  
  public static Event parse( final String text ) {
    if ( text == null ) {
      return null;
    }
    
    if ( text.equals( "none" ) ) {
      return Event.NULL;
    }
    
    final String func;
    final String argString;
    
    final int l = text.indexOf( '(' );
    
    if ( l == -1 ) {
      func = text;
      argString = "";
    } else {
      final int r = text.lastIndexOf( ')' );
      func = text.substring( 0, l );
      argString = text.substring( l + 1, r );
    }
    
    final String[] args = argString.split( ",\\s?" );
    
    if ( BUILDERS.containsKey( func ) ) {
      return BUILDERS.get( func ).build( args );
    } else {
      final String[] events = JSONUtil.toStringArray( JSONUtil.parseJSON( "event/" + func ).get() );
      return new CustomEvent( func,
          fillParallel( new Event[ events.length ], i -> parse( events[ i ] ) ) );
    }
  }
}