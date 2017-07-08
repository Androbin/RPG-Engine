package de.androbin.rpg;

import de.androbin.rpg.obj.*;
import de.androbin.rpg.tile.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class World {
  public Identifier id;
  public final Dimension size;
  
  private final Tile[] tiles;
  private final List<Entity> entities;
  private final List<GameObject> objects;
  
  public final SpaceTime<Sprite> strong;
  public final SpaceTime<Sprite> weak;
  
  public World( final Identifier id, final Dimension size ) {
    this.id = id;
    this.size = size;
    
    this.tiles = new Tile[ size.width * size.height ];
    this.objects = new ArrayList<>();
    this.entities = new ArrayList<>();
    
    this.strong = new SpaceTime<>();
    this.weak = new SpaceTime<>();
  }
  
  public final boolean addEntity( final Entity entity, final Point pos ) {
    entity.attach( this, pos );
    
    if ( entities.contains( entity ) ) {
      return true;
    }
    
    final boolean success = strong.tryAdd( entity, entity.getBounds() );
    
    if ( !success ) {
      return false;
    }
    
    entities.add( entity );
    return true;
  }
  
  public final boolean addGameObject( final GameObject object ) {
    if ( objects.contains( object ) ) {
      return true;
    }
    
    if ( object.data.passEvent == null ) {
      final boolean success = strong.tryAdd( object, object.getBounds() );
      
      if ( !success ) {
        return false;
      }
    } else {
      weak.add( object, object.getBounds() );
    }
    
    objects.add( object );
    return true;
  }
  
  public final boolean checkBounds( final Point pos ) {
    return pos.x >= 0 && pos.x < size.width
        && pos.y >= 0 && pos.y < size.height;
  }
  
  public Entity getEntity( final Point pos ) {
    final Object o = strong.tryGet( pos );
    return o instanceof Entity ? (Entity) o : null;
  }
  
  public GameObject getGameObject( final Point pos ) {
    final Object o = strong.tryGet( pos );
    final Object p = weak.tryGet( pos );
    return o instanceof GameObject ? (GameObject) o
        : p instanceof GameObject ? (GameObject) p : null;
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
  
  public final void removeEntity( final Entity entity ) {
    entities.remove( entity );
    strong.remove( entity );
  }
  
  public final void removeGameObject( final GameObject object ) {
    objects.remove( object );
    
    if ( object.data.passEvent == null ) {
      strong.remove( object );
    } else {
      weak.remove( object );
    }
  }
  
  public final void render( final Graphics2D g, final Rectangle2D.Float view, final float scale ) {
    LoopUtil.forEach( view, pos -> {
      final Tile tile = getTile( pos );
      
      if ( tile != null ) {
        tile.render( g, pos, scale );
      }
    } );
    
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