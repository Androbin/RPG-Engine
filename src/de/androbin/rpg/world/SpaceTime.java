package de.androbin.rpg.world;

import de.androbin.rpg.*;
import de.androbin.util.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public final class SpaceTime<T> {
  private final List<Pair<T, Bounds>> map = new ArrayList<>();
  
  private void add( final Pair<T, Bounds> pair ) {
    map.add( pair );
  }
  
  public void add( final T o, final Bounds bounds ) {
    add( new Pair<>( o, bounds ) );
  }
  
  private Pair<T, Bounds> find( final T o ) {
    for ( final Pair<T, Bounds> pair : map ) {
      if ( pair.first == o ) {
        return pair;
      }
    }
    
    return null;
  }
  
  private Pair<T, Bounds> find( final Point pos ) {
    for ( final Pair<T, Bounds> pair : map ) {
      if ( pair.second.contains( pos ) ) {
        return pair;
      }
    }
    
    return null;
  }
  
  public Bounds get( final T o ) {
    final Pair<T, Bounds> pair = find( o );
    return pair == null ? null : pair.second;
  }
  
  public T get( final Point pos ) {
    final Pair<T, Bounds> pair = find( pos );
    return pair == null ? null : pair.first;
  }
  
  private void remove( final Pair<T, Bounds> pair ) {
    if ( pair == null ) {
      return;
    }
    
    map.remove( pair );
  }
  
  public void remove( final T o ) {
    remove( find( o ) );
  }
  
  public void remove( final Point pos ) {
    remove( find( pos ) );
  }
  
  public void set( final T o, final Bounds target ) {
    find( o ).second = target;
  }
  
  public boolean tryAdd( final T o, final Bounds bounds ) {
    for ( final Pair<T, Bounds> pair : map ) {
      if ( pair.second.intersects( bounds ) ) {
        return false;
      }
    }
    
    add( o, bounds );
    return true;
  }
  
  public boolean trySet( final T o, final Bounds target ) {
    Pair<T, Bounds> current = null;
    
    for ( final Pair<T, Bounds> pair : map ) {
      if ( pair.first == o ) {
        current = pair;
      } else if ( pair.second.intersects( target ) ) {
        return false;
      }
    }
    
    if ( current == null ) {
      return false;
    }
    
    current.second = target;
    return true;
  }
}