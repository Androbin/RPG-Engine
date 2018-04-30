package de.androbin.rpg;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;

public class World {
  public final Ident id;
  public final Dimension size;
  
  private final Tile[] tiles;
  private final List<Entity> entities;
  
  private final SpaceTime<Entity> strong;
  private final SpaceTime<Entity> weak;
  
  public World( final Ident id, final Dimension size ) {
    this.id = id;
    this.size = size;
    
    this.tiles = new Tile[ size.width * size.height ];
    this.entities = new ArrayList<>();
    
    this.strong = new SpaceTime<>();
    this.weak = new SpaceTime<>();
  }
  
  public final boolean addEntity( final Entity entity, final Point pos ) {
    if ( entity == null ) {
      return false;
    }
    
    final Spot spot = new Spot( this, pos );
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
  
  public final boolean checkBounds( final Point pos ) {
    return pos.x >= 0 && pos.x < size.width
        && pos.y >= 0 && pos.y < size.height;
  }
  
  public Entity getEntity( final boolean solid, final Point pos ) {
    return getSpaceTime( solid ).get( pos );
  }
  
  public SpaceTime<Entity> getSpaceTime( final boolean solid ) {
    return solid ? strong : weak;
  }
  
  public SpaceTime<Entity> getSpaceTime( final Entity entity ) {
    return getSpaceTime( entity.data.solid );
  }
  
  public Tile getTile( final Point pos ) {
    if ( !checkBounds( pos ) ) {
      return null;
    }
    
    return tiles[ pos.y * size.width + pos.x ];
  }
  
  public final List<Agent> listAgents() {
    return listEntities( Agent.class );
  }
  
  public final List<Entity> listEntities() {
    return Collections.unmodifiableList( entities );
  }
  
  @ SuppressWarnings( "unchecked" )
  public <T> List<T> listEntities( final Class<T> type ) {
    return entities.stream()
        .filter( entity -> type.isAssignableFrom( entity.getClass() ) )
        .map( entity -> (T) entity )
        .collect( Collectors.toList() );
  }
  
  private List<Entity> listEntities( final Predicate<Entity> filter ) {
    return entities.stream()
        .filter( filter )
        .collect( Collectors.toList() );
  }
  
  public final List<Entity> listEntities( final boolean solid ) {
    return listEntities( entity -> entity.data.solid == solid );
  }
  
  public final boolean removeEntity( final Entity entity ) {
    if ( entity == null ) {
      return false;
    }
    
    entities.remove( entity );
    entity.setSpot( null );
    
    getSpaceTime( entity ).remove( entity );
    return true;
  }
  
  public final boolean setTile( final Point pos, final Tile tile ) {
    if ( !checkBounds( pos ) ) {
      return false;
    }
    
    tiles[ pos.y * size.width + pos.x ] = tile;
    return true;
  }
}