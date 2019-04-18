package de.androbin.rpg.space;

import de.androbin.space.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public final class SpaceTime<T> {
  private final Space<T> space;
  
  private final Map<T, Bounds> boundsCache;
  
  public SpaceTime() {
    space = new SpatialList<T>();
    boundsCache = new HashMap<>();
  }
  
  public void add( final T o, final Bounds bounds ) {
    space.add( o, bounds.getBounds() );
    boundsCache.put( o, bounds );
  }
  
  public T get( final Point pos ) {
    return filter( pos ).findAny().orElse( null );
  }
  
  public List<T> getAll( final Point pos ) {
    return filter( pos ).collect( Collectors.toList() );
  }
  
  public List<T> getAll( final Rectangle window ) {
    return filter( Bounds.rect( window ) ).collect( Collectors.toList() );
  }
  
  private Stream<T> filter( final Bounds bounds ) {
    return space.filter( bounds.getBounds() )
        .filter( o -> boundsCache.get( o ).intersects( bounds ) );
  }
  
  private Stream<T> filter( final Point pos ) {
    return space.filter( pos )
        .filter( o -> boundsCache.get( o ).contains( pos ) );
  }
  
  public void remove( final T o ) {
    space.remove( o, boundsCache.remove( o ).getBounds() );
  }
  
  public void set( final T o, final Bounds bounds ) {
    space.set( o, boundsCache.get( o ).getBounds(), bounds.getBounds() );
    boundsCache.put( o, bounds );
  }
  
  public boolean tryAdd( final T o, final Bounds bounds ) {
    if ( filter( bounds ).findAny().isPresent() ) {
      return false;
    }
    
    add( o, bounds );
    return true;
  }
  
  public boolean trySet( final T o, final Bounds bounds ) {
    if ( filter( bounds ).findAny().isPresent() ) {
      return false;
    }
    
    set( o, bounds );
    return true;
  }
}