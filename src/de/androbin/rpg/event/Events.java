package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.handler.*;
import java.util.*;

public final class Events {
  private static final Map<String, Event.Builder> BUILDERS = new HashMap<>();
  private static final Map<Class<? extends Event>, Event.Handler<? extends Master, ? extends Event>> HANDLERS = new HashMap<>();
  
  public static final EventQueue QUEUE = new EventQueue();
  
  static {
    Events.BUILDERS.put( "teleport", TeleportEvent.BUILDER );
    putHandler( TeleportEvent.class, new TeleportEventHandler() );
    putHandler( TileEnterEvent.class, new TileEnterEventHandler() );
  }
  
  private Events() {
  }
  
  private static Object[] compile( final String[] args, final Map<String, Object> values ) {
    if ( values == null ) {
      return compile( args, Collections.emptyMap() );
    }
    
    return fill( new Object[ args.length ], i -> {
      final String arg = args[ i ];
      
      if ( arg.startsWith( "$" ) ) {
        return values.getOrDefault( arg.substring( 1 ), null );
      } else {
        return arg;
      }
    } );
  }
  
  public static void putBuilder( final String func, final Event.Builder builder ) {
    BUILDERS.put( func, builder );
  }
  
  public static <M extends Master, E extends Event> void putHandler(
      final Class<E> clazz, final Event.Handler<M, E> handler ) {
    HANDLERS.put( clazz, handler );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <M extends Master, E extends Event> void handle( final M master, final E event ) {
    final String message = event.getMessage();
    
    if ( message != null ) {
      System.out.println( message );
    }
    
    if ( HANDLERS.containsKey( event.getClass() ) ) {
      final Event.Handler<M, E> handler = (Event.Handler<M, E>) HANDLERS.get( event.getClass() );
      handler.handle( master, event );
    }
  }
  
  public static Event.Raw parse( final String text ) {
    if ( text == null ) {
      return null;
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
      final Event.Builder builder = BUILDERS.get( func );
      return values -> builder.build( compile( args, values ) );
    } else {
      final String[] events = JSONUtil.readJSON( "event/" + func ).get().asStringArray();
      return values -> new CustomEvent( func, fill( new Event[ events.length ], i -> {
        return parse( events[ i ] ).compile( values );
      } ) );
    }
  }
}