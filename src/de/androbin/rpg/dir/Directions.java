package de.androbin.rpg.dir;

import java.awt.event.*;
import java.util.*;
import java.util.function.*;

public final class Directions {
  public static final Map<Integer, Direction> KEY_MAPPINGS = new HashMap<>();
  
  static {
    KEY_MAPPINGS.put( KeyEvent.VK_W, Direction.UP );
    KEY_MAPPINGS.put( KeyEvent.VK_A, Direction.LEFT );
    KEY_MAPPINGS.put( KeyEvent.VK_S, Direction.DOWN );
    KEY_MAPPINGS.put( KeyEvent.VK_D, Direction.RIGHT );
    
    KEY_MAPPINGS.put( KeyEvent.VK_UP, Direction.UP );
    KEY_MAPPINGS.put( KeyEvent.VK_DOWN, Direction.DOWN );
    KEY_MAPPINGS.put( KeyEvent.VK_RIGHT, Direction.RIGHT );
    KEY_MAPPINGS.put( KeyEvent.VK_LEFT, Direction.LEFT );
  }
  
  private Directions() {
  }
  
  public static Direction aim( final float dx, final float dy, final Predicate<Direction> pred ) {
    final float ax = Math.abs( dx );
    final float ay = Math.abs( dy );
    
    final Direction dirX = dx < 0f ? Direction.LEFT : Direction.RIGHT;
    final Direction dirY = dy < 0f ? Direction.UP : Direction.DOWN;
    
    if ( ax >= ay ) {
      return choose( dirX, dirY, pred );
    } else {
      return choose( dirY, dirX, pred );
    }
  }
  
  public static Direction byKeyCode( final int keycode ) {
    return KEY_MAPPINGS.getOrDefault( keycode, null );
  }
  
  private static Direction choose( final Direction d0, final Direction d1,
      final Predicate<Direction> pred ) {
    return pred.test( d0 ) ? d0 : d1;
  }
  
  public static Direction valueOf( final String name ) {
    return name == null ? null : Direction.valueOf( name.toUpperCase() );
  }
}