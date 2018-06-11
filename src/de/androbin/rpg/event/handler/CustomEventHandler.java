package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class CustomEventHandler implements Event.Handler<Master, CustomEvent> {
  @ Override
  public Overlay handle( final Master master, final CustomEvent event ) {
    return event.handler.handle( master );
  }
}