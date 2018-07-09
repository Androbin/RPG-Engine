package de.androbin.rpg.event;

import de.androbin.rpg.*;
import de.androbin.rpg.overlay.*;
import java.util.*;
import java.util.function.*;

public final class EventQueue {
  private final Queue<Event> queue = new ArrayDeque<>();
  
  public void enqueue( final Event event ) {
    if ( event == null ) {
      return;
    }
    
    queue.add( event );
  }
  
  public void enqueue( final Event.Raw event, final Map<String, Object> values ) {
    if ( event == null ) {
      return;
    }
    
    queue.add( event.compile( values ) );
  }
  
  public void enqueueAwait( final Event event, final Consumer<Overlay> c ) {
    enqueue( new CustomEvent( master -> {
      c.accept( Events.handle( master, event ) );
      return null;
    } ) );
  }
  
  public void process( final Master master ) {
    while ( !queue.isEmpty() ) {
      Events.handle( master, queue.remove() );
    }
  }
}