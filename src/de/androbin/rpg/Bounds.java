package de.androbin.rpg;

import de.androbin.mixin.dim.*;
import de.androbin.rpg.dir.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public final class Bounds {
  public final Point pos;
  public final Shape shape;
  
  public Bounds( final Point pos, final Shape shape ) {
    this.pos = pos;
    this.shape = shape;
  }
  
  public Point2D.Float center() {
    final Dimension size = shape.getSize();
    return new Point2D.Float( pos.x + size.width * 0.5f, pos.y + size.height * 0.5f );
  }
  
  public boolean contains( final Point p ) {
    return shape.contains( new Point( p.x - pos.x, p.y - pos.y ) );
  }
  
  public Bounds expand( final Direction dir ) {
    final Rectangle expanded = dir.expand( getBounds() );
    return new Bounds( expanded.getLocation(), new Shape() {
      @ Override
      public boolean contains( final Point p ) {
        final int x2 = p.x - Math.abs( dir.dx );
        final int y2 = p.y - Math.abs( dir.dy );
        return shape.contains( p ) || shape.contains( new Point( x2, y2 ) );
      }
      
      @ Override
      public Dimension getSize() {
        return expanded.getSize();
      }
    } );
  }
  
  public Rectangle getBounds() {
    return new Rectangle( pos, shape.getSize() );
  }
  
  public List<Point> inner( final Direction dir ) {
    final List<Point> inner = new ArrayList<>();
    final Dimension size = shape.getSize();
    
    final boolean axis = dir.ordinal() % 2 == 0;
    final int is = axis ? size.width : size.height;
    final int js = axis ? size.height : size.width;
    final int ds = axis ? dir.dy : dir.dx;
    
    for ( int i = 0; i < is; i++ ) {
      for ( int j = 0; j < js; j++ ) {
        final int x = i;
        final int y = ds > 0 ? js - j - 1 : j;
        
        if ( shape.contains( new Point( x, y ) ) ) {
          inner.add( new Point( x + pos.x, y + pos.y ) );
          break;
        }
      }
    }
    
    return inner;
  }
  
  public boolean intersects( final Bounds bounds ) {
    return LoopUtil.any( getBounds().intersection( bounds.getBounds() ), p -> {
      return contains( p ) && bounds.contains( p );
    } );
  }
  
  public List<Point> outer( final Direction dir ) {
    return inner( dir ).stream().map( dir::from ).collect( Collectors.toList() );
  }
  
  public static final class Float {
    public final Point2D.Float pos;
    public final Shape shape;
    
    public Float( final Point2D.Float pos, final Shape shape ) {
      this.pos = pos;
      this.shape = shape;
    }
    
    public Point2D.Float center() {
      final Dimension size = shape.getSize();
      return new Point2D.Float( pos.x + size.width * 0.5f, pos.y + size.height * 0.5f );
    }
    
    public boolean contains( final Point2D.Float p ) {
      return shape.contains( new Point( (int) ( p.x - pos.x ), (int) ( p.y - pos.y ) ) );
    }
    
    public Rectangle2D.Float getBounds() {
      final Dimension size = shape.getSize();
      return new Rectangle2D.Float( pos.x, pos.y, size.width, size.height );
    }
  }
}