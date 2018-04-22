package de.androbin.rpg.dialog;

import de.androbin.rpg.event.*;
import java.util.function.*;

public final class Choice {
  public String label;
  public Supplier<Event> event;
  
  public Choice( final String label, final Supplier<Event> event ) {
    this.label = label;
    this.event = event;
  }
}