package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;

public final class CustomEventHandler implements Event.Handler<Master, CustomEvent> {
  @ Override
  public void handle( final Master master, final CustomEvent event ) {
    event.handler.handle( master );
  }
}