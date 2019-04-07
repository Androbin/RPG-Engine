package de.androbin.rpg.overlay;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.shell.*;
import java.util.*;
import java.util.stream.*;

public final class ScriptOverlay extends AbstractShell implements Overlay {
  private final Queue<Event[]> script;
  private List<Overlay> current;
  
  private final Intervention intervention;
  
  public ScriptOverlay( final Event[][] script, final Intervention intervention ) {
    this.script = new ArrayDeque<>( Arrays.asList( script ) );
    current = Collections.emptyList();
    
    this.intervention = intervention;
  }
  
  @ Override
  public Intervention getIntervention() {
    return intervention;
  }
  
  @ Override
  public void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void update( final float delta ) {
    if ( !current.isEmpty() && current.stream().anyMatch( overlay -> {
      return overlay.isRunning() && overlay.isActive();
    } ) ) {
      return;
    }
    
    current.clear();
    
    if ( script.isEmpty() ) {
      setRunning( false );
      return;
    }
    
    Events.QUEUE.enqueue( new CustomEvent( master -> {
      current = Stream.of( script.remove() )
          .map( event -> Events.handle( master, event ) )
          .filter( Objects::nonNull )
          .collect( Collectors.toList() );
      return null;
    } ) );
  }
}