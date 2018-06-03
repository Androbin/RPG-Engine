package de.androbin.rpg.event;

import de.androbin.rpg.*;
import java.util.logging.*;

public final class CustomEvent implements Event {
  public final Handler handler;
  
  public CustomEvent( final Handler handler ) {
    this.handler = handler;
  }
  
  @ Override
  public void log( final Logger logger ) {
  }
  
  @ FunctionalInterface
  public interface Handler {
    void handle( Master master );
  }
}