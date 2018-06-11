package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.overlay.*;

public final class StoryEventHandler implements Event.Handler<Master, StoryEvent> {
  @ Override
  public Overlay handle( final Master master, final StoryEvent event ) {
    final String id = event.id;
    master.story.setDone( id );
    return null;
  }
}