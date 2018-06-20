package de.androbin.rpg.world;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public final class EntityLayer {
  private final World world;
  
  private final List<Entity> entities;
  private final List<Agent> agents;
  
  private final SpaceTime<Entity> strong;
  private final SpaceTime<Entity> weak;
  
  public EntityLayer( final World world ) {
    this.world = world;
    
    entities = new ArrayList<>();
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
    
    entities.add( entity );
    
    if ( entity instanceof Agent ) {
      final Agent agent = (Agent) entity;
      agents.add( agent );
    }
    
    return true;
  }
  
  public Entity findById( final int id ) {
    if ( id == 0 ) {
      return null;
    }
    
    for ( final Entity entity : entities ) {
      if ( entity.id == id ) {
        return entity;
      }
    }
    
    return null;
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
  
  public List<Entity> list() {
    return Collections.unmodifiableList( entities );
  }
  
  public List<Agent> listAgents() {
    return Collections.unmodifiableList( agents );
  }
  
  public void move( final Entity entity, final Rectangle target ) {
    getSpaceTime( entity ).set( entity, target );
  }
  
  public boolean remove( final Entity entity ) {
    if ( entity == null ) {
      return false;
    }
    
    entities.remove( entity );
    
    if ( entity instanceof Agent ) {
      final Agent agent = (Agent) entity;
      agents.remove( agent );
    }
    
    getSpaceTime( entity ).remove( entity );
    entity.setSpot( null );
    return true;
  }
  
  public boolean tryMove( final Entity entity, final Rectangle target ) {
    return getSpaceTime( entity ).trySet( entity, target );
  }
}