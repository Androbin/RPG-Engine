package de.androbin.rpg.world;

import de.androbin.util.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public final class SpaceTime<T> {
  private final List<Pair<T, Rectangle>> map = new ArrayList<>();
  
  private void add( final Pair<T, Rectangle> pair ) {
    map.add( pair );
  }
  
  public void add( final T o, final Rectangle bounds ) {
    add( new Pair<>( o, bounds ) );
  }
  
  private Pair<T, Rectangle> find( final T o ) {
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.first == o ) {
        return pair;
      }
    }
    
    return null;
  }
  
  private Pair<T, Rectangle> find( final Point pos ) {
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.second.contains( pos ) ) {
        return pair;
      }
    }
    
    return null;
  }
  
  public Rectangle get( final T o ) {
    final Pair<T, Rectangle> pair = find( o );
    return pair == null ? null : pair.second;
  }
  
  public T get( final Point pos ) {
    final Pair<T, Rectangle> pair = find( pos );
    return pair == null ? null : pair.first;
  }
  
  private void remove( final Pair<T, Rectangle> pair ) {
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
  
  public void set( final T o, final Rectangle target ) {
    final Pair<T, Rectangle> pair = find( o );
    pair.second.setBounds( target );
  }
  
  public boolean tryAdd( final T o, final Rectangle bounds ) {
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.second.intersects( bounds ) ) {
        return false;
      }
    }
    
    add( o, bounds );
    return true;
  }
  
  public boolean trySet( final T o, final Rectangle target ) {
    Rectangle current = null;
    
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.first == o ) {
        current = pair.second;
      } else if ( pair.second.intersects( target ) ) {
        return false;
      }
    }
    
    if ( current == null ) {
      return false;
    }
    
    current.setBounds( target );
    return true;
  }
}