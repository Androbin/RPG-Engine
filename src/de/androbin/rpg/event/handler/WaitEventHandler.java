package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class WaitEventHandler implements Event.Handler<Master, WaitEvent> {
  @ Override
  public Overlay handle( final Master master, final WaitEvent event ) {
    final float duration = event.duration;
    return new WaitOverlay( duration );
  }
}