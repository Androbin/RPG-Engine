package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;

public final class BatchEventHandler implements Event.Handler<Master, BatchEvent> {
  @ Override
  public void handle( final Master master, final BatchEvent event ) {
    for ( final Event part : event.parts ) {
      Events.handle( master, part );
    }
  }
}