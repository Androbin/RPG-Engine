package de.androbin.rpg;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.story.*;
import de.androbin.rpg.world.*;
import java.util.*;

public abstract class Master {
  private final Map<Ident, World> worlds = new HashMap<>();
  public World world;
  
  public final StoryState story;
  
  public final Camera camera;
  public final List<Overlay> overlays;
  
  public Master( final StoryState story ) {
    this.story = story;
    
    camera = new Camera();
    overlays = new ArrayList<>();
  }
  
  public void addOverlay( final Overlay overlay ) {
    overlay.setRunning( true );
    overlays.add( overlay );
  }
  
  protected abstract World createWorld( Ident id );
  
  public abstract Agent getPlayer();
  
  public World getWorld( final Ident id ) {
    return worlds.computeIfAbsent( id, this::createWorld );
  }
  
  public Collection<World> listWorlds() {
    return worlds.values();
  }
}