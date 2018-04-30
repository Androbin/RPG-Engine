package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import java.awt.*;
import java.awt.geom.*;

public class Entity {
  public final EntityData data;
  public final int id;
  
  private Spot spot;
  
  public Entity( final EntityData data, final int id ) {
    this.data = data;
    this.id = id;
  }
  
  public Rectangle getBounds() {
    return new Rectangle( spot.getPos(), data.size );
  }
  
  public Point2D.Float getFloatPos() {
    final Point pos = spot.getPos();
    return new Point2D.Float( pos.x, pos.y );
  }
  
  public Spot getSpot() {
    return spot;
  }
  
  public void setSpot( final Spot spot ) {
    this.spot = spot;
  }
  
  public void update( final float delta ) {
  }
  
  @ FunctionalInterface
  public interface Builder<D extends EntityData> {
    Entity build( D data, int id );
  }
}