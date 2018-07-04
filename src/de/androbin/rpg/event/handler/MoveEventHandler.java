package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class MoveEventHandler implements Event.Handler<Master, MoveEvent> {
  @ Override
  public Overlay handle( final Master master, final MoveEvent event ) {
    final Agent agent = (Agent) EventHandlers.getEntity( master, event.agent );
    final Direction[] dirs = event.dirs;
    
    return new MoveOverlay( agent.move, dirs );
  }
}