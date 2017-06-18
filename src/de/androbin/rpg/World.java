package de.androbin.rpg;

import de.androbin.rpg.obj.*;
import de.androbin.rpg.tile.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import java.util.logging.*;
import java.util.stream.*;

public class World {
  public final String name;
  public final Dimension size;
  
  private final Tile[] tiles;
  private final List<Entity> entities;
  private final List<GameObject> objects;
  
  public final SpaceTime<Sprite> spaceTime;
  
  public World( final Dimension size, final String name ) {
    this.name = name;
    this.size = size;
    
    this.tiles = new Tile[ size.width * size.height ];
    this.objects = new ArrayList<>();
    this.entities = new ArrayList<>();
    
    this.spaceTime = new SpaceTime<>();
  }
  
  public final boolean addEntity( final Entity entity ) {
    if ( entities.contains( entity ) ) {
      Logger.getGlobal().log( Level.WARNING,
          "World " + name + " tried to add Entity twice: " + entity );
      return true;
    }
    
    final boolean success = spaceTime.tryAdd( entity, entity.getBounds() );
    
    if ( !success ) {
      Logger.getGlobal().log( Level.WARNING,
          "World " + name + " failed to attach Entity: " + entity );
      return false;
    }
    
    entities.add( entity );
    return true;
  }
  
  public final boolean addGameObject( final GameObject object ) {
    if ( objects.contains( object ) ) {
      Logger.getGlobal().log( Level.WARNING,
          "World " + name + " tried to add GameObject twice: " + object );
      return true;
    }
    
    if ( object.data.passEvent == null ) {
      final boolean success = spaceTime.tryAdd( object, object.getBounds() );
      
      if ( !success ) {
        Logger.getGlobal().log( Level.WARNING,
            "World " + name + " failed to attach GameObject: " + object );
        return false;
      }
    }
    
    objects.add( object );
    return true;
  }
  
  public final boolean checkBounds( final Point pos ) {
    return pos.x >= 0 && pos.x < size.width
        && pos.y >= 0 && pos.y < size.height;
  }
  
  public Entity getEntity( final Point pos ) {
    final Object o = spaceTime.tryGet( pos );
    return o instanceof Entity ? (Entity) o : null;
  }
  
  public GameObject getGameObject( final Point pos ) {
    final Object o = spaceTime.tryGet( pos );
    return o instanceof GameObject ? (GameObject) o : null;
  }
  
  public Tile getTile( final Point pos ) {
    return checkBounds( pos ) ? tiles[ pos.y * size.width + pos.x ] : null;
  }
  
  public final List<Entity> listEntities() {
    return Collections.unmodifiableList( entities );
  }
  
  public final void removeEntity( final Entity entity ) {
    entities.remove( entity );
    spaceTime.remove( entity );
  }
  
  public final void removeGameObject( final GameObject object ) {
    objects.remove( object );
    spaceTime.remove( object );
  }
  
  public final void render( final Graphics2D g, final Rectangle2D.Float view, final float scale ) {
    for ( int y = (int) view.y; y <= view.y + view.height; y++ ) {
      for ( int x = (int) view.x; x <= view.x + view.width; x++ ) {
        final Point pos = new Point( x, y );
        final Tile tile = getTile( pos );
        
        if ( tile != null ) {
          tile.render( g, pos, scale );
        }
      }
    }
    
    final Comparator<Sprite> comp = ( a, b ) -> Float.compare( a.getBounds().y, b.getBounds().y );
    Stream.concat( objects.stream(), entities.stream() )
        .filter( o -> o.getViewBounds().intersects( view ) )
        .sorted( comp )
        .forEachOrdered( o -> o.render( g, scale ) );
  }
  
  public final boolean setTile( final Point pos, final Tile tile ) {
    if ( !checkBounds( pos ) ) {
      return false;
    }
    
    tiles[ pos.y * size.width + pos.x ] = tile;
    return true;
  }
}