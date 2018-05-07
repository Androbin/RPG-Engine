package de.androbin.rpg.world;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;

public final class EntityLayer extends Layer {
  private final List<Entity> entities;
  
  private final SpaceTime<Entity> strong;
  private final SpaceTime<Entity> weak;
  
  public EntityLayer( final World world ) {
    super( world );
    
    entities = new ArrayList<>();
    
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
    return true;
  }
  
  public Entity get( final boolean solid, final Point pos ) {
    return getSpaceTime( solid ).get( pos );
  }
  
  private SpaceTime<Entity> getSpaceTime( final boolean solid ) {
    return solid ? strong : weak;
  }
  
  private SpaceTime<Entity> getSpaceTime( final Entity entity ) {
    return getSpaceTime( entity.data.solid );
  }
  
  public List<Agent> listAgents() {
    return list( Agent.class );
  }
  
  public List<Entity> list() {
    return Collections.unmodifiableList( entities );
  }
  
  @ SuppressWarnings( "unchecked" )
  public <T> List<T> list( final Class<T> type ) {
    return entities.stream()
        .filter( entity -> type.isAssignableFrom( entity.getClass() ) )
        .map( entity -> (T) entity )
        .collect( Collectors.toList() );
  }
  
  private List<Entity> list( final Predicate<Entity> filter ) {
    return entities.stream()
        .filter( filter )
        .collect( Collectors.toList() );
  }
  
  public List<Entity> list( final boolean solid ) {
    return list( entity -> entity.data.solid == solid );
  }
  
  public void move( final Entity entity, final Rectangle target ) {
    getSpaceTime( entity ).set( entity, target );
  }
  
  public boolean remove( final Entity entity ) {
    if ( entity == null ) {
      return false;
    }
    
    entities.remove( entity );
    entity.setSpot( null );
    
    getSpaceTime( entity ).remove( entity );
    return true;
  }
  
  public boolean tryMove( final Entity entity, final Rectangle target ) {
    return getSpaceTime( entity ).trySet( entity, target );
  }
}