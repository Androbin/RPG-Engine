package de.androbin.rpg.event;

import java.util.*;

public interface Event {
  Event NULL = args -> {
  };
  
  void run( Map<String, Object> args );
  
  interface Builder {
    Event build( String[] args );
  }
}