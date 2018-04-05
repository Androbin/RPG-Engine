package de.androbin.rpg;

import java.awt.*;
import java.awt.geom.*;
import java.util.function.*;

public final class LoopUtil {
  private LoopUtil() {
  }
  
  public static boolean and( final Rectangle r, final Predicate<Point> p ) {
    for ( int y = r.y; y < r.y + r.height; y++ ) {
      for ( int x = r.x; x < r.x + r.width; x++ ) {
        if ( !p.test( new Point( x, y ) ) ) {
          return false;
        }
      }
    }
    
    return true;
  }
  
  public static boolean any( final Rectangle r, final Predicate<Point> p ) {
    for ( int y = r.y; y < r.y + r.height; y++ ) {
      for ( int x = r.x; x < r.x + r.width; x++ ) {
        if ( p.test( new Point( x, y ) ) ) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public static void forEach( final Dimension d, final Consumer<Point> c ) {
    for ( int y = 0; y < d.height; y++ ) {
      for ( int x = 0; x < d.width; x++ ) {
        c.accept( new Point( x, y ) );
      }
    }
  }
  
  public static void forEach( final Rectangle r, final Consumer<Point> c ) {
    for ( int y = r.y; y < r.y + r.height; y++ ) {
      for ( int x = r.x; x < r.x + r.width; x++ ) {
        c.accept( new Point( x, y ) );
      }
    }
  }
  
  public static void forEach( final Rectangle2D.Float r, final Consumer<Point> c ) {
    for ( int y = (int) r.y; y < r.y + r.height; y++ ) {
      for ( int x = (int) r.x; x < r.x + r.width; x++ ) {
        c.accept( new Point( x, y ) );
      }
    }
  }
}