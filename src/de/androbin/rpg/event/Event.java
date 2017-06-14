package de.androbin.rpg.event;

import java.util.*;
import java.util.function.*;

public interface Event {
  Event NULL = func( "null", args -> {
  } );
  
  static Event func( final String name, final Consumer<Map<String, Object>> action ) {
    return new Event() {
      @ Override
      public String getLogMessage() {
        return name;
      }
      
      @ Override
      public void run( final Map<String, Object> args ) {
        action.accept( args );
      }
    };
  };
  
  String getLogMessage();
  
  void run( Map<String, Object> args );
  
  interface Builder {
    Event build( String[] args );
  }
}