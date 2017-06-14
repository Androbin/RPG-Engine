package de.androbin.rpg.event;

import java.util.*;

public final class EventQueue implements Runnable {
  private final Queue<Item> queue = new ArrayDeque<>();
  
  public void enqueue( final Event event, final Map<String, Object> args ) {
    queue.add( new Item( event, args ) );
  }
  
  @ Override
  public void run() {
    queue.forEach( Item::run );
    queue.clear();
  }
  
  private static class Item implements Runnable {
    private final Event event;
    private final Map<String, Object> args;
    
    public Item( final Event event, final Map<String, Object> args ) {
      this.event = event;
      this.args = args;
    }
    
    @ Override
    public void run() {
      event.run( args );
    }
  }
}