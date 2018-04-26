package de.androbin.rpg.dir;

import java.awt.*;
import java.awt.geom.*;

public final class DirectionPair {
  public final Direction first;
  public final Direction second;
  
  public DirectionPair( final Direction dir ) {
    first = dir;
    second = null;
  }
  
  public DirectionPair( final Direction first, final Direction second ) {
    this.first = first;
    this.second = second == first ? null : second;
  }
  
  public int dx() {
    if ( second == null ) {
      return first.dx;
    }
    
    return first.dx + second.dx;
  }
  
  public int dy() {
    if ( second == null ) {
      return first.dy;
    }
    
    return first.dy + second.dy;
  }
  
  @ Override
  public boolean equals( final Object obj ) {
    if ( obj instanceof DirectionPair ) {
      final DirectionPair dir = (DirectionPair) obj;
      return first == dir.first && second == dir.second;
    }
    
    return false;
  }
  
  public Point from( final Point p ) {
    return from( p, 1 );
  }
  
  public Point from( final Point p, final int x ) {
    if ( second == null ) {
      return first.from( p, x );
    }
    
    return new Point( p.x + dx() * x, p.y + dy() * x );
  }
  
  public Point2D.Float from( final Point p, final float scalar ) {
    if ( second == null ) {
      return first.from( p, scalar );
    }
    
    return new Point2D.Float(
        p.x + first.dx * scalar + second.dx * ( scalar - 0.5f ),
        p.y + first.dy * scalar + second.dy * ( scalar - 0.5f ) );
  }
  
  public DirectionPair opposite() {
    if ( second == null ) {
      return new DirectionPair( first.opposite() );
    }
    
    return new DirectionPair( first.opposite(), second.opposite() );
  }
  
  public DirectionPair reverse() {
    if ( second == null ) {
      return this;
    }
    
    return new DirectionPair( second, first );
  }
}