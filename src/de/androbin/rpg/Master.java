package de.androbin.rpg;

import de.androbin.mixin.iter.*;
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
    overlays.add( overlay );
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
  
  public World getWorld( final Ident id ) {
    return worlds.computeIfAbsent( id, this::createWorld );
  }
  
  public void invalidate() {
    worlds.clear();
    final World newWorld = getWorld( world.id );
    
    for ( final Agent agent : world.entities.listAgents() ) {
      if ( isPlayer( agent ) ) {
        newWorld.entities.remove( newWorld.entities.findById( agent.id ) );
        newWorld.entities.add( agent, agent.getSpot().getPos() );
      }
    }
    
    world = newWorld;
  }
  
  public abstract boolean isPlayer( Agent agent );
  
  public Iterable<Overlay> listOverlaysUp() {
    return Collections.unmodifiableList( overlays );
  }
  
  public Iterable<Overlay> listOverlaysDown() {
    return () -> new ReverseIterator<>( Collections.unmodifiableList( overlays ) );
  }
  
  public Collection<World> listWorlds() {
    return worlds.values();
  }
}