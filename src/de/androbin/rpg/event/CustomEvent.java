package de.androbin.rpg.event;

import java.util.*;

public final class CustomEvent implements Event {
  public final String name;
  private final Event[] events;
  
  public CustomEvent( final String name, final Event[] events ) {
    this.name = name;
    this.events = events;
  }
  
  @ Override
  public void run( final Map<String, Object> args ) {
    for ( final Event event : events ) {
      event.run( args );
    }
  }
  
  @ Override
  public String toString() {
    return name;
  }
}