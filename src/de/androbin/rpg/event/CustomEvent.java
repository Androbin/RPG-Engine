package de.androbin.rpg.event;

import de.androbin.rpg.*;

public final class CustomEvent extends Event {
  public final String name;
  public final Event[] parts;
  
  public CustomEvent( final String name, final Event[] parts ) {
    this.name = name;
    this.parts = parts;
  }
  
  @ Override
  public String getMessage() {
    return name;
  }
  
  public static void handle( final RPGScreen master, final CustomEvent event ) {
    for ( final Event part : event.parts ) {
      Events.handle( master, part );
    }
  }
}