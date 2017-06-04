package de.androbin.rpg;

import java.awt.event.*;
import java.util.*;

public enum Direction {
  UP( 0, -1 ), LEFT( -1, 0 ), DOWN( 0, 1 ), RIGHT( 1, 0 );
  
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
  
  public final int dx;
  public final int dy;
  
  private Direction( final int dx, final int dy ) {
    this.dx = dx;
    this.dy = dy;
  }
  
  public static Direction bestFit( final float dx, final float dy ) {
    final float ax = Math.abs( dx );
    final float ay = Math.abs( dy );
    
    if ( ax < 0.5f && ay < 0.5f ) {
      return null;
    }
    
    if ( ax >= ay ) {
      return dx >= 0f ? Direction.RIGHT : Direction.LEFT;
    } else {
      return dy >= 0f ? Direction.DOWN : Direction.UP;
    }
  }
  
  public static Direction byKeyCode( final int keycode ) {
    return KEY_MAPPINGS.getOrDefault( keycode, null );
  }
  
  public Direction opposite() {
    return values()[ ( ordinal() + 2 ) % 4 ];
  }
}