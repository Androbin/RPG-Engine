package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class VisionEventHandler implements Event.Handler<Master, VisionEvent> {
  @ Override
  public Overlay handle( final Master master, final VisionEvent event ) {
    final Ident world = event.world;
    master.world = master.getWorld( world );
    return null;
  }
}