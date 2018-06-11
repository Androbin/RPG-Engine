package de.androbin.rpg.event;

import de.androbin.rpg.*;
import de.androbin.rpg.overlay.*;
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
    Overlay handle( Master master );
  }
}