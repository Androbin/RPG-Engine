package de.androbin.rpg.event;

import java.util.*;

public final class CustomEvent implements Event {
  private final String name;
  
  private final Event[] events;
  
  public CustomEvent( final String name, final Event[] events ) {
    this.name = name;
    this.events = events;
  }
  
  @ Override
  public void run( final Map<String, Object> args ) {
    Arrays.stream( events ).forEach( event -> event.run( args ) );
  }
  
  @ Override
  public String toString() {
    return name;
  }
}