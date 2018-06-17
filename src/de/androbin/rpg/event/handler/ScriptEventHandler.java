package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class ScriptEventHandler implements Event.Handler<Master, ScriptEvent> {
  @ Override
  public Overlay handle( final Master master, final ScriptEvent event ) {
    final boolean masking = event.masking;
    final Event[][] script = event.script;
    
    return new ScriptOverlay( masking, script );
  }
}