package de.androbin.rpg.event;

import de.androbin.rpg.*;
import java.util.*;

public final class EventQueue {
  private final Queue<Item> queue = new ArrayDeque<>();
  
  public void enqueue( final Event event, final Map<String, Object> args ) {
    queue.add( new Item( event, args ) );
  }
  
  public void run( final RPGScreen master ) {
    queue.forEach( item -> item.run( master ) );
    queue.clear();
  }
  
  private static class Item {
    private final Event event;
    private final Map<String, Object> args;
    
    public Item( final Event event, final Map<String, Object> args ) {
      this.event = event;
      this.args = args;
    }
    
    public void run( final RPGScreen master ) {
      event.run( master, args );
    }
  }
}