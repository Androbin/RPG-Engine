package de.androbin.rpg;

import java.awt.*;
import java.awt.geom.*;

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
  
  public Rectangle expand( final Rectangle r ) {
    switch ( this ) {
      case UP:
        return new Rectangle( r.x, r.y - 1, r.width, r.height + 1 );
      case LEFT:
        return new Rectangle( r.x - 1, r.y, r.width + 1, r.height );
      case DOWN:
        return new Rectangle( r.x, r.y, r.width, r.height + 1 );
      case RIGHT:
        return new Rectangle( r.x, r.y, r.width + 1, r.height );
    }
    
    return null;
  }
  
  public Point from( final Point p ) {
    return new Point( p.x + dx, p.y + dy );
  }
  
  public Point2D.Float from( final Point p, final float progress ) {
    return new Point2D.Float( p.x + dx * progress, p.y + dy * progress );
  }
  
  public Rectangle inner( final Rectangle r ) {
    switch ( this ) {
      case UP:
        return new Rectangle( r.x, r.y, r.width, 1 );
      case LEFT:
        return new Rectangle( r.x, r.y, 1, r.height );
      case DOWN:
        return new Rectangle( r.x, r.y + r.height - 1, r.width, 1 );
      case RIGHT:
        return new Rectangle( r.x + r.width - 1, r.y, 1, r.height );
    }
    
    return null;
  }
  
  public Direction opposite() {
    return values()[ ( ordinal() + 2 ) % 4 ];
  }
  
  public Rectangle outer( final Rectangle r ) {
    switch ( this ) {
      case UP:
        return new Rectangle( r.x, r.y - 1, r.width, 1 );
      case LEFT:
        return new Rectangle( r.x - 1, r.y, 1, r.height );
      case DOWN:
        return new Rectangle( r.x, r.y + r.height, r.width, 1 );
      case RIGHT:
        return new Rectangle( r.x + r.width, r.y, 1, r.height );
    }
    
    return null;
  }
}