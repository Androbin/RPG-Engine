package de.androbin.rpg;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.overlay.*;
import de.androbin.rpg.story.*;
import de.androbin.rpg.world.*;
import java.util.*;

public abstract class Master {
  private final Map<Ident, World> worlds = new HashMap<>();
  public World world;
  
  public final StoryState story;
  
  public final Camera camera;
  private final List<Overlay> overlays;
  
  public Master( final StoryState story ) {
    this.story = story;
    
    camera = new Camera();
    overlays = new ArrayList<>();
  }
  
  public void addOverlay( final Overlay overlay ) {
    if ( overlays.contains( overlay ) ) {
      return;
    }
    
    overlay.setRunning( true );
    overlays.add( 0, overlay );
  }
  
  public void cleanOverlays() {
    for ( final Iterator<Overlay> iter = overlays.iterator(); iter.hasNext(); ) {
      final Overlay overlay = iter.next();
      
      if ( !overlay.isRunning() ) {
        iter.remove();
      }
    }
  }
  
  protected abstract World createWorld( Ident id );
  
  public abstract Agent getPlayer();
  
  public World getWorld( final Ident id ) {
    return worlds.computeIfAbsent( id, this::createWorld );
  }
  
  public List<Overlay> listOverlays() {
    return Collections.unmodifiableList( overlays );
  }
  
  public Collection<World> listWorlds() {
    return worlds.values();
  }
}