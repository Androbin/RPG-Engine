package de.androbin.rpg.event;

import java.util.*;
import de.androbin.rpg.*;

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
  
  public void process( final Master master ) {
    while ( !queue.isEmpty() ) {
      Events.handle( master, queue.remove() );
    }
  }
}