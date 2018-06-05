package de.androbin.rpg.event.handler;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;

public final class StoryEventHandler implements Event.Handler<Master, StoryEvent> {
  @ Override
  public void handle( final Master master, final StoryEvent event ) {
    final String id = event.id;
    master.story.setDone( id );
  }
}