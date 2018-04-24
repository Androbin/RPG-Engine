package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import java.awt.*;
import java.awt.geom.*;

public class Entity {
  public final EntityData data;
  public final int id;
  
  public transient World world;
  public Point pos;
  
  public Entity( final EntityData data, final int id ) {
    this.data = data;
    this.id = id;
    
    this.pos = new Point();
  }
  
  public Rectangle getBounds() {
    return new Rectangle( pos, data.size );
  }
  
  public Point2D.Float getFloatPos() {
    return new Point2D.Float( pos.x, pos.y );
  }
  
  public void update( final float delta ) {
  }
  
  @ FunctionalInterface
  public interface Builder<D extends EntityData> {
    Entity build( D data, int id );
  }
}