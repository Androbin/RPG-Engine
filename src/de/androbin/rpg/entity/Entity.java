package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import java.awt.*;
import java.awt.geom.*;

public abstract class Entity implements Detailed {
  public int id;
  private Spot spot;
  
  public Entity( final int id ) {
    this.id = id;
  }
  
  public final Rectangle getBounds() {
    return new Rectangle( spot.getPos(), getData().size );
  }
  
  public abstract EntityData getData();
  
  public Rectangle2D.Float getFloatBounds() {
    final Point2D.Float pos = getFloatPos();
    final Dimension size = getData().size;
    return new Rectangle2D.Float( pos.x, pos.y, size.width, size.height );
  }
  
  public Point2D.Float getFloatPos() {
    final Point pos = spot.getPos();
    return new Point2D.Float( pos.x, pos.y );
  }
  
  public final Spot getSpot() {
    return spot;
  }
  
  public void setSpot( final Spot spot ) {
    this.spot = spot;
  }
  
  @ Override
  public String toString() {
    return getData().type.toString();
  }
  
  @ FunctionalInterface
  public interface Builder<D extends EntityData> {
    Entity build( D data, int id );
  }
}