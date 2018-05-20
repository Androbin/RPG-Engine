package de.androbin.rpg;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.*;
import de.androbin.rpg.story.*;
import de.androbin.rpg.world.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;

public class Master {
  private final Map<Ident, World> worlds = new HashMap<>();
  private final Function<Ident, World> worldCreator;
  public World world;
  
  public final StoryState story;
  public Agent player;
  
  public final Camera camera;
  public final List<Overlay> overlays;
  
  public Master( final Function<Ident, World> worldCreator, final StoryState story ) {
    this.worldCreator = worldCreator;
    this.story = story;
    
    camera = new Camera();
    overlays = new ArrayList<>();
  }
  
  public World getWorld( final Ident id ) {
    return worlds.computeIfAbsent( id, worldCreator );
  }
  
  public void addOverlay( final Overlay overlay ) {
    overlay.setRunning( true );
    overlays.add( overlay );
  }
  
  public void switchWorld( final Ident id, final Point pos ) {
    if ( world != null ) {
      world.entities.remove( player );
    }
    
    world = getWorld( id );
    world.entities.add( player, pos );
  }
}