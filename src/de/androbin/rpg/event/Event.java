package de.androbin.rpg.event;

import java.util.*;

public interface Event {
  Event NULL = screen -> {
  };
  
  void run( final Map<String, Object> args );
  
  interface Builder {
    Event build( final String[] args );
  }
}