package de.androbin.rpg.event;

import java.util.*;

public interface Event {
  Event NULL = func( "null", () -> {
  } );
  
  static Event func( final String name, final Runnable action ) {
    return new Event() {
      @ Override
      public String getLogMessage() {
        return name;
      }
      
      @ Override
      public void run( final Map<String, Object> args ) {
        action.run();
      }
    };
  };
  
  String getLogMessage();
  
  void run( Map<String, Object> args );
  
  interface Builder {
    Event build( String[] args );
  }
}