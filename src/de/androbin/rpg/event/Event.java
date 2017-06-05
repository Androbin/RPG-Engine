package de.androbin.rpg.event;

import java.util.*;

public interface Event {
  Event NULL = screen -> {
  };
  
  void run( Map<String, Object> args );
  
  interface Builder {
    Event build( String[] args );
  }
}