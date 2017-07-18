package de.androbin.rpg;

import de.androbin.rpg.event.EventQueue;
import de.androbin.rpg.phantom.*;
import de.androbin.rpg.thing.*;
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
  private final List<Thing> things;
  private final List<Phantom> phantoms;
  private final List<Entity> entities;
  
  public final SpaceTime<Sprite> strong;
  public final SpaceTime<Sprite> weak;
  
  public World( final Identifier id, final Dimension size ) {
    this.id = id;
    this.size = size;
    
    this.tiles = new Tile[ size.width * size.height ];
    this.things = new ArrayList<>();
    this.phantoms = new ArrayList<>();
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
  
  public final boolean addPhantom( final Phantom phantom, final Point pos ) {
    phantom.attach( pos );
    
    if ( phantoms.contains( phantom ) ) {
      return true;
    }
    
    final boolean success = weak.tryAdd( phantom, phantom.getBounds() );
    
    if ( !success ) {
      return false;
    }
    
    phantoms.add( phantom );
    return true;
  }
  
  public final boolean addThing( final Thing thing, final Point pos ) {
    thing.attach( pos );
    
    if ( things.contains( thing ) ) {
      return true;
    }
    
    final boolean success = strong.tryAdd( thing, thing.getBounds() );
    
    if ( !success ) {
      return false;
    }
    
    things.add( thing );
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
  
  public Phantom getPhantom( final Point pos ) {
    final Sprite o = weak.tryGet( pos );
    return o instanceof Phantom ? (Phantom) o : null;
  }
  
  public Thing getThing( final Point pos ) {
    final Sprite o = strong.tryGet( pos );
    return o instanceof Thing ? (Thing) o : null;
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
  
  public final List<Phantom> listPhantoms() {
    return Collections.unmodifiableList( phantoms );
  }
  
  public final List<Thing> listThings() {
    return Collections.unmodifiableList( things );
  }
  
  public final void removeEntity( final Entity entity ) {
    entities.remove( entity );
    strong.remove( entity );
  }
  
  public final void removePhantom( final Phantom phantom ) {
    phantoms.remove( phantom );
    weak.remove( phantom );
  }
  
  public final void removeThing( final Thing thing ) {
    things.remove( thing );
    strong.remove( thing );
  }
  
  public final void render( final Graphics2D g, final Rectangle2D.Float view, final float scale ) {
    LoopUtil.forEach( view, pos -> {
      final Tile tile = getTile( pos );
      
      if ( tile != null ) {
        tile.render( g, pos, scale );
      }
    } );
    
    final Comparator<Sprite> comp = ( a, b ) -> Float.compare( a.getBounds().y, b.getBounds().y );
    Stream.concat( Stream.concat( things.stream(), phantoms.stream() ), entities.stream() )
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
  
  public void triggerStrong( final Point pos, final EventQueue events,
      final Map<String, Object> args ) {
    final Thing thing = getThing( pos );
    
    if ( thing != null ) {
      thing.trigger( events, args );
    }
  }
  
  public void triggerWeak( final Point pos, final EventQueue events,
      final Map<String, Object> args ) {
    final Tile tile = getTile( pos );
    tile.trigger( events, args );
    
    final Phantom phantom = getPhantom( pos );
    
    if ( phantom != null ) {
      phantom.trigger( events, args );
    }
  }
}