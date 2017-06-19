package de.androbin.rpg.event;

import de.androbin.rpg.*;
import java.util.*;
import java.util.function.*;

public interface Event {
  Event NULL = func( "null", ( master, args ) -> {
  } );
  
  static Event func( final String name, final BiConsumer<RPGScreen, Map<String, Object>> action ) {
    return new Event() {
      @ Override
      public String getLogMessage() {
        return name;
      }
      
      @ Override
      public void run( final RPGScreen master, final Map<String, Object> args ) {
        action.accept( master, args );
      }
    };
  };
  
  String getLogMessage();
  
  void run( RPGScreen master, Map<String, Object> args );
  
  interface Builder {
    Event build( String[] args );
  }
}