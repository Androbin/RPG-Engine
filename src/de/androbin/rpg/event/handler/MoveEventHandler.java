package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class MoveEventHandler implements Event.Handler<Master, MoveEvent> {
  @ Override
  public Overlay handle( final Master master, final MoveEvent event ) {
    final int agentRaw = event.agent;
    final Direction[] dirs = event.dirs;
    
    final Agent agent = (Agent) master.world.entities.findById( agentRaw );
    return new MoveOverlay( agent.move, dirs );
  }
}