package de.androbin.rpg.event;

import java.util.*;
import java.util.logging.*;

public final class CustomEvent implements Event {
  public final String name;
  private final Event[] events;
  
  public CustomEvent( final String name, final Event[] events ) {
    this.name = name;
    this.events = events;
  }
  
  @ Override
  public String getLogMessage() {
    return name;
  }
  
  @ Override
  public void run( final Map<String, Object> args ) {
    for ( final Event event : events ) {
      Logger.getGlobal().log( Level.INFO, event.getLogMessage() );
      event.run( args );
    }
  }
}