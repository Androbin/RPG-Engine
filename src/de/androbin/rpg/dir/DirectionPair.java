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
    
    return new Point2D.Float( p.x + dx() * scalar, p.y + dy() * scalar );
  }
  
  public Point2D.Float fromAskew( final Point p, final float scalar ) {
    if ( second == null ) {
      return from( p, scalar );
    }
    
    final Point2D.Float q = from( p, scalar );
    q.x -= second.dx * 0.5f;
    q.y -= second.dy * 0.5f;
    return q;
  }
  
  public DirectionPair opposite() {
    if ( second == null ) {
      return new DirectionPair( first.opposite() );
    }
    
    return new DirectionPair( first.opposite(), second.opposite() );
  }
  
  public static DirectionPair parse( final String text ) {
    final String[] parts = text.split( "-" );
    
    if ( parts.length == 1 ) {
      return new DirectionPair(
          Direction.parse( parts[ 0 ] ) );
    } else {
      return new DirectionPair(
          Direction.parse( parts[ 0 ] ),
          Direction.parse( parts[ 1 ] ) );
    }
  }
  
  public DirectionPair reverse() {
    if ( second == null ) {
      return this;
    }
    
    return new DirectionPair( second, first );
  }
  
  @ Override
  public String toString() {
    if ( second == null ) {
      return first.toString();
    } else {
      return first.toString() + "-" + second.toString();
    }
  }
}