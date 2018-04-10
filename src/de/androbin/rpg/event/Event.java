package de.androbin.rpg.event;

import de.androbin.rpg.*;
import java.util.*;

public abstract class Event {
  public abstract String getMessage();
  
  @ FunctionalInterface
  public interface Raw {
    Event compile( Map<String, Object> values );
  }
  
  @ FunctionalInterface
  public interface Builder {
    Event build( Object[] args );
  }
  
  @ FunctionalInterface
  public interface Handler<E extends Event> {
    void handle( RPGScreen master, E event );
  }
}