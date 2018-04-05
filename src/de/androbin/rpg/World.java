package de.androbin.rpg;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;

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
    
    entity.pos = pos;
    
    final SpaceTime<Entity> spaceTime = getSpaceTime( entity );
    final boolean success = spaceTime.tryAdd( entity, entity.getBounds() );
    
    if ( !success ) {
      // entity.pos = null;
      return false;
    }
    
    entity.world = this;
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
  
  public final List<Entity> listEntities() {
    return Collections.unmodifiableList( entities );
  }
  
  public final List<Entity> listEntities( final boolean solid ) {
    return entities.stream()
        .filter( entity -> entity.data.solid == solid )
        .collect( Collectors.toList() );
  }
  
  public final boolean removeEntity( final Entity entity ) {
    if ( entity == null ) {
      return false;
    }
    
    entities.remove( entity );
    entity.world = null;
    entity.pos = null;
    
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