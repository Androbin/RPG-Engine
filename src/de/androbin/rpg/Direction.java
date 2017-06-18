package de.androbin.rpg;

import java.awt.*;

public enum Direction {
  UP( 0, -1 ), LEFT( -1, 0 ), DOWN( 0, 1 ), RIGHT( 1, 0 );
  
  public final int dx;
  public final int dy;
  
  private Direction( final int dx, final int dy ) {
    this.dx = dx;
    this.dy = dy;
  }
  
  public Rectangle expand( final Point p ) {
    switch ( this ) {
      case UP:
        return new Rectangle( p.x, p.y - 1, 1, 2 );
      case LEFT:
        return new Rectangle( p.x - 1, p.y, 2, 1 );
      case DOWN:
        return new Rectangle( p.x, p.y, 1, 2 );
      case RIGHT:
        return new Rectangle( p.x, p.y, 2, 1 );
    }
    
    return null;
  }
  
  public Rectangle expand( final Rectangle p ) {
    switch ( this ) {
      case UP:
        return new Rectangle( p.x, p.y - 1, p.width, p.height + 1 );
      case LEFT:
        return new Rectangle( p.x - 1, p.y, p.width + 1, p.height );
      case DOWN:
        return new Rectangle( p.x, p.y + 1, p.width, p.height + 1 );
      case RIGHT:
        return new Rectangle( p.x + 1, p.y, p.width + 1, p.height );
    }
    
    return null;
  }
  
  public Point from( final Point p ) {
    return new Point( p.x + dx, p.y + dy );
  }
  
  public Direction opposite() {
    return values()[ ( ordinal() + 2 ) % 4 ];
  }
  
  public static Direction byDistance( final int dx, final int dy ) {
    switch ( dx ) {
      case 1:
        return RIGHT;
      case -1:
        return LEFT;
    }
    
    switch ( dy ) {
      case 1:
        return DOWN;
      case -1:
        return UP;
    }
    
    return null;
  }
}