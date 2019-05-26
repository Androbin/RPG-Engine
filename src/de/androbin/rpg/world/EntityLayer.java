package de.androbin.rpg.world;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.space.*;
import de.androbin.space.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public final class EntityLayer {
  private final World world;
  
  private final Map<Integer, Entity> ids;
  private final List<Agent> agents;
  
  private final SpaceTime<Entity> strong;
  private final SpaceTime<Entity> weak;
  
  public EntityLayer( final World world ) {
    this.world = world;
    
    ids = new HashMap<>();
    agents = new ArrayList<>();
    
    strong = new SpaceTime<>();
    weak = new SpaceTime<>();
  }
  
  public boolean add( final Entity entity, final Point pos ) {
    if ( entity == null ) {
      return false;
    }
    
    final Spot spot = new Spot( world, pos );
    entity.setSpot( spot );
    
    final SpaceTime<Entity> spaceTime = getSpaceTime( entity );
    final boolean success = spaceTime.tryAdd( entity, entity.getBounds() );
    
    if ( !success ) {
      entity.setSpot( null );
      return false;
    }
    
    if ( entity.id != 0 ) {
      ids.put( entity.id, entity );
    }
    
    if ( entity instanceof Agent ) {
      final Agent agent = (Agent) entity;
      agents.add( agent );
    }
    
    return true;
  }
  
  public Stream<Entity> filter( final Rectangle window ) {
    return strong.filter( window );
  }
  
  public Entity findById( final int id ) {
    return ids.get( id );
  }
  
  public Entity get( final boolean solid, final Point pos ) {
    return getSpaceTime( solid ).get( pos );
  }
  
  public Dimension getSize() {
    return world.size;
  }
  
  private SpaceTime<Entity> getSpaceTime( final boolean solid ) {
    return solid ? strong : weak;
  }
  
  private SpaceTime<Entity> getSpaceTime( final Entity entity ) {
    return getSpaceTime( entity.getData().solid );
  }
  
  public List<Agent> listAgents() {
    return Collections.unmodifiableList( agents );
  }
  
  public void move( final Entity entity, final Bounds target ) {
    getSpaceTime( entity ).set( entity, target );
  }
  
  public boolean remove( final Entity entity ) {
    if ( entity == null ) {
      return false;
    }
    
    if ( entity.id != 0 ) {
      ids.remove( entity.id );
    }
    
    if ( entity instanceof Agent ) {
      final Agent agent = (Agent) entity;
      agents.remove( agent );
    }
    
    getSpaceTime( entity ).remove( entity );
    entity.setSpot( null );
    return true;
  }
  
  public Stream<Entity> stream() {
    return Stream.concat( strong.stream(), weak.stream() );
  }
  
  public boolean tryMove( final Entity entity, final Bounds target ) {
    return getSpaceTime( entity ).trySet( entity, target );
  }
}