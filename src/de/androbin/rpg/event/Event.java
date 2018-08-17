package de.androbin.rpg.event;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.overlay.*;
import java.util.*;
import java.util.logging.*;

public interface Event {
  void log( Logger logger );
  
  @ FunctionalInterface
  interface Raw {
    Event compile( Map<String, Object> values );
  }
  
  @ FunctionalInterface
  interface Builder {
    Event build( XValue[] args );
  }
  
  @ FunctionalInterface
  interface Handler<M extends Master, E extends Event> {
    Overlay handle( M master, E event );
  }
}