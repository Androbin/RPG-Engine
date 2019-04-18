package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;
import de.androbin.space.*;
import java.util.*;

public final class MoveEventHandler implements Event.Handler<Master, MoveEvent> {
  @ Override
  public Overlay handle( final Master master, final MoveEvent event ) {
    final Agent agent = (Agent) EventHandlers.getEntity( master, event.agent );
    final List<Direction> dirs = event.dirs;
    final boolean autoStop = event.autoStop;
    
    return new MoveOverlay( agent.move, dirs, autoStop );
  }
}