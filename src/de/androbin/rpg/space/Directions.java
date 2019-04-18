package de.androbin.rpg.space;

import de.androbin.space.*;
import java.awt.event.*;
import java.util.*;

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
  
  public static DirectionPair aim( final float dx, final float dy ) {
    final float d = (float) Math.sqrt( dx * dx + dy * dy );
    final float dx0 = dx / d;
    final float dy0 = dy / d;
    
    Direction dirX = null;
    
    if ( dx0 > 0.5f ) {
      dirX = Direction.RIGHT;
    } else if ( dx0 < -0.5f ) {
      dirX = Direction.LEFT;
    }
    
    Direction dirY = null;
    
    if ( dy0 > 0.5f ) {
      dirY = Direction.DOWN;
    } else if ( dy0 < -0.5f ) {
      dirY = Direction.UP;
    }
    
    if ( dirX != null ) {
      return new DirectionPair( dirX, dirY );
    } else if ( dirY != null ) {
      return new DirectionPair( dirY );
    }
    
    return null;
  }
  
  public static Direction byKeyCode( final int keycode ) {
    return KEY_MAPPINGS.getOrDefault( keycode, null );
  }
  
  public static DirectionPair follow( final float dx, final float dy ) {
    Direction dirX = null;
    
    if ( dx >= 1f ) {
      dirX = Direction.RIGHT;
    } else if ( dx <= -1f ) {
      dirX = Direction.LEFT;
    }
    
    Direction dirY = null;
    
    if ( dy >= 1f ) {
      dirY = Direction.DOWN;
    } else if ( dy <= -1f ) {
      dirY = Direction.UP;
    }
    
    if ( dirX != null ) {
      return new DirectionPair( dirX, dirY );
    } else if ( dirY != null ) {
      return new DirectionPair( dirY );
    }
    
    return null;
  }
  
  public static Direction valueOf( final String name ) {
    return name == null ? null : Direction.valueOf( name.toUpperCase() );
  }
}