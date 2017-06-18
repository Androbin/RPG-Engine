package de.androbin.rpg;

import java.awt.*;
import java.util.*;
import java.util.List;
import javafx.util.*;

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
      if ( pair.getKey() == o ) {
        return pair;
      }
    }
    
    return null;
  }
  
  private Pair<T, Rectangle> find( final Point pos ) {
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.getValue().contains( pos ) ) {
        return pair;
      }
    }
    
    return null;
  }
  
  public Rectangle get( final T o ) {
    return find( o ).getValue();
  }
  
  public T get( final Point pos ) {
    return find( pos ).getKey();
  }
  
  private void remove( final Pair<T, Rectangle> pair ) {
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
    pair.getValue().setBounds( target );
  }
  
  public boolean tryAdd( final T o, final Rectangle bounds ) {
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.getValue().intersects( bounds ) ) {
        return false;
      }
    }
    
    add( o, bounds );
    return true;
  }
  
  public Rectangle tryGet( final T o ) {
    final Pair<T, Rectangle> pair = find( o );
    return pair == null ? null : pair.getValue();
  }
  
  public T tryGet( final Point pos ) {
    final Pair<T, Rectangle> pair = find( pos );
    return pair == null ? null : pair.getKey();
  }
  
  private boolean tryRemove( final Pair<T, Rectangle> pair ) {
    if ( pair == null ) {
      return false;
    }
    
    remove( pair );
    return true;
  }
  
  public boolean tryRemove( final T o ) {
    return tryRemove( find( o ) );
  }
  
  public boolean tryRemove( final Point pos ) {
    return tryRemove( find( pos ) );
  }
  
  public boolean trySet( final T o, final Rectangle target ) {
    Rectangle current = null;
    
    for ( final Pair<T, Rectangle> pair : map ) {
      if ( pair.getKey() == o ) {
        current = pair.getValue();
      } else if ( pair.getValue().intersects( target ) ) {
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