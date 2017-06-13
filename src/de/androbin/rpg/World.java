package de.androbin.rpg;

import de.androbin.rpg.gfx.*;
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
  private final List<GameObject> objects;
  private final List<Entity> entities;
  
  public World( final Dimension size, final String name ) {
    this.name = name;
    this.size = size;
    
    this.tiles = new Tile[ size.width * size.height ];
    this.objects = new ArrayList<>();
    this.entities = new ArrayList<>();
  }
  
  public final void addEntity( final Entity entity ) {
    if ( entities.contains( entity ) ) {
      Logger.getGlobal().log( Level.WARNING,
          "World " + name + " had Entity attached twice: " + entity );
    }
    
    getTile( entity.getPos() ).request( entity );
    entities.add( entity );
  }
  
  public final boolean checkBounds( final Point pos ) {
    return new Rectangle( size ).contains( pos );
  }
  
  public final boolean checkBounds( final Rectangle bounds ) {
    return new Rectangle( size ).contains( bounds );
  }
  
  public Entity getEntity( final Point pos ) {
    for ( final Entity entity : listEntities() ) {
      if ( entity.getPos().equals( pos ) ) {
        return entity;
      }
    }
    
    return null;
  }
  
  public GameObject getGameObject( final Point pos ) {
    for ( final GameObject object : objects ) {
      if ( object.getBounds().contains( pos ) ) {
        return object;
      }
    }
    
    return null;
  }
  
  public Tile getTile( final Point pos ) {
    return checkBounds( pos ) ? tiles[ pos.y * size.width + pos.x ] : null;
  }
  
  public final List<Entity> listEntities() {
    return Collections.unmodifiableList( entities );
  }
  
  public final void removeEntity( final Entity entity ) {
    entities.remove( entity );
    getTile( entity.getPos() ).release();
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
  
  public final boolean addGameObject( final GameObject object ) {
    final Rectangle bounds = object.getBounds();
    
    if ( !checkBounds( bounds ) ) {
      return false;
    }
    
    for ( int y = bounds.y; y < bounds.y + bounds.height; y++ ) {
      for ( int x = bounds.x; x < bounds.x + bounds.width; x++ ) {
        final Point pos = new Point( x, y );
        getTile( pos ).request( object );
      }
    }
    
    objects.add( object );
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