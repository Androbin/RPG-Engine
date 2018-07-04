package de.androbin.rpg.event;

import static de.androbin.collection.util.ObjectCollectionUtil.*;
import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.handler.*;
import de.androbin.rpg.overlay.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

public final class Events {
  private static final Map<String, Event.Builder> BUILDERS;
  private static final Map<Class<? extends Event>, Event.Handler<?, ?>> HANDLERS;
  private static final Map<String, ScriptEvent.Static> SCRIPTS;
  
  public static final EventQueue QUEUE;
  public static final Logger LOGGER;
  
  static {
    BUILDERS = new HashMap<>();
    HANDLERS = new HashMap<>();
    SCRIPTS = new HashMap<>();
    
    QUEUE = new EventQueue();
    
    final Handler logHandler = new ConsoleHandler();
    logHandler.setLevel( Level.ALL );
    logHandler.setFormatter( new Formatter() {
      @ Override
      public String format( final LogRecord record ) {
        final String name = record.getLoggerName();
        final String level = record.getLevel().getName();
        final String message = record.getMessage();
        
        final String format = "%1$s %2$-7s %3$s\n";
        return String.format( format, name, level, message );
      }
    } );
    
    LOGGER = Logger.getLogger( "events" );
    LOGGER.setUseParentHandlers( false );
    LOGGER.addHandler( logHandler );
  }
  
  static {
    Events.BUILDERS.put( "move", MoveEvent.BUILDER );
    Events.BUILDERS.put( "property", PropertyEvent.BUILDER );
    Events.BUILDERS.put( "story", StoryEvent.BUILDER );
    Events.BUILDERS.put( "teleport", TeleportEvent.BUILDER );
    Events.BUILDERS.put( "vision", VisionEvent.BUILDER );
    Events.BUILDERS.put( "wait", WaitEvent.BUILDER );
    
    putHandler( CustomEvent.class, new CustomEventHandler() );
    putHandler( MoveEvent.class, new MoveEventHandler() );
    putHandler( PropertyEvent.class, new PropertyEventHandler() );
    putHandler( ScriptEvent.class, new ScriptEventHandler() );
    putHandler( StoryEvent.class, new StoryEventHandler() );
    putHandler( TeleportEvent.class, new TeleportEventHandler() );
    putHandler( TileEnterEvent.class, new TileEnterEventHandler() );
    putHandler( VisionEvent.class, new VisionEventHandler() );
    putHandler( WaitEvent.class, new WaitEventHandler() );
  }
  
  private Events() {
  }
  
  private static Event[][] buildScript( final XArray script, final Map<String, Object> values ) {
    return fill( new Event[ script.size() ][], i -> {
      final XValue value = script.get( i );
      
      try {
        final XArray array = value.asArray();
        return fill( new Event[ array.size() ], j -> {
          final XValue value2 = array.get( j );
          
          try {
            final XArray array2 = value2.asArray();
            return new ScriptEvent( buildScript( array2, values ) );
          } catch ( final ClassCastException e ) {
            final String event = value2.asString();
            return parse( event ).compile( values );
          }
        } );
      } catch ( final ClassCastException e ) {
        final String event = value.asString();
        return new Event[] { parse( event ).compile( values ) };
      }
    } );
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
  public static <M extends Master, E extends Event> Overlay handle( final M master,
      final E event ) {
    event.log( LOGGER );
    
    if ( !HANDLERS.containsKey( event.getClass() ) ) {
      return null;
    }
    
    final Event.Handler<M, E> handler = (Event.Handler<M, E>) HANDLERS.get( event.getClass() );
    final Overlay overlay = handler.handle( master, event );
    
    if ( overlay != null ) {
      master.addOverlay( overlay );
    }
    
    return overlay;
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
    
    final String[] argSplit = argString.split( "(?<!\\\\)," );
    final String[] args = fill( new String[ argSplit.length ],
        i -> argSplit[ i ].replace( "\\,", "," ).trim() );
    
    if ( func.equals( "script" ) ) {
      final String name = args[ 0 ];
      final boolean masking = Boolean.parseBoolean( args[ 1 ] );
      
      final ScriptEvent.Static raw = SCRIPTS.computeIfAbsent( name, foo -> values -> {
        final XArray script = XUtil.readJSON( "event/" + name + ".json" ).get().asArray();
        return buildScript( script, values );
      } );
      return values -> new ScriptEvent( name, masking, raw.compile( values ) );
    }
    
    final Event.Builder builder = BUILDERS.get( func );
    
    if ( builder == null ) {
      System.err.println( "Unknown event type '" + func + "'" );
      return values -> null;
    }
    
    return values -> builder.build( compile( args, values ) );
  }
}