package de.androbin.rpg.overlay;

import de.androbin.rpg.event.*;
import de.androbin.shell.*;
import java.util.*;

public final class ScriptOverlay extends AbstractShell implements Overlay {
  private final Queue<Event> script;
  private Overlay current;
  
  public ScriptOverlay( final Event ... script ) {
    this.script = new ArrayDeque<>( Arrays.asList( script ) );
  }
  
  @ Override
  public void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void update( final float delta ) {
    if ( current == null ) {
      if ( script.isEmpty() ) {
        setRunning( false );
      } else {
        Events.QUEUE.enqueue( new CustomEvent( master -> {
          return current = Events.handle( master, script.remove() );
        } ) );
      }
    } else if ( !current.isRunning() ) {
      current = null;
    }
  }
}