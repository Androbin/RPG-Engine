package de.androbin.rpg;

import java.awt.*;
import java.util.*;
import java.util.List;
import javafx.util.*;

public final class SpaceTime {
  private final List<Pair<Object, Rectangle>> map = new ArrayList<>();
  
  private void add( final Pair<Object, Rectangle> pair ) {
    map.add( pair );
  }
  
  public void add( final Object o, final Rectangle bounds ) {
    add( new Pair<>( o, bounds ) );
  }
  
  private Pair<Object, Rectangle> find( final Object o ) {
    for ( final Pair<Object, Rectangle> pair : map ) {
      if ( pair.getKey() == o ) {
        return pair;
      }
    }
    
    return null;
  }
  
  private Pair<Object, Rectangle> find( final Point pos ) {
    for ( final Pair<Object, Rectangle> pair : map ) {
      if ( pair.getValue().contains( pos ) ) {
        return pair;
      }
    }
    
    return null;
  }
  
  public Rectangle get( final Object o ) {
    return find( o ).getValue();
  }
  
  public Object get( final Point pos ) {
    return find( pos ).getKey();
  }
  
  private void remove( final Pair<Object, Rectangle> pair ) {
    map.remove( pair );
  }
  
  public void remove( final Object o ) {
    remove( find( o ) );
  }
  
  public void remove( final Point pos ) {
    remove( find( pos ) );
  }
  
  public void set( final Object o, final Rectangle target ) {
    final Pair<Object, Rectangle> pair = find( o );
    pair.getValue().setBounds( target );
  }
  
  public boolean tryAdd( final Object o, final Rectangle bounds ) {
    for ( final Pair<Object, Rectangle> pair : map ) {
      if ( pair.getValue().intersects( bounds ) ) {
        return false;
      }
    }
    
    add( o, bounds );
    return true;
  }
  
  public Rectangle tryGet( final Object o ) {
    final Pair<Object, Rectangle> pair = find( o );
    return pair == null ? null : pair.getValue();
  }
  
  public Object tryGet( final Point pos ) {
    final Pair<Object, Rectangle> pair = find( pos );
    return pair == null ? null : pair.getKey();
  }
  
  private boolean tryRemove( final Pair<Object, Rectangle> pair ) {
    if ( pair == null ) {
      return false;
    }
    
    remove( pair );
    return true;
  }
  
  public boolean tryRemove( final Object o ) {
    return tryRemove( find( o ) );
  }
  
  public boolean tryRemove( final Point pos ) {
    return tryRemove( find( pos ) );
  }
  
  public boolean trySet( final Object o, final Rectangle target ) {
    Rectangle current = null;
    
    for ( final Pair<Object, Rectangle> pair : map ) {
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