package de.androbin.rpg.entity;

import java.awt.*;
import java.awt.geom.*;
import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.event.Event;

public class Entity {
  public transient World world;
  public Point pos;
  
  public final EntityData data;
  public final int id;
  
  public Direction orientation;
  
  public final MoveHandle move;
  
  public Event enterEvent;
  
  public Entity( final EntityData data, final int id ) {
    this.data = data;
    this.id = id;
    
    this.pos = new Point();
    
    this.orientation = data.orientation;
    
    this.move = new MoveHandle( this );
  }
  
  public Rectangle getBounds() {
    return new Rectangle( pos, data.size );
  }
  
  public final Point2D.Float getFloatPos() {
    if ( move.hasCurrent() ) {
      final DirectionPair dir = move.getCurrent();
      final float progress = move.getProgress();
      final float scalar;
      
      if ( dir.second == null ) {
        scalar = ( progress + 0.5f ) % 1f - 0.5f;
      } else {
        scalar = progress;
      }
      
      return dir.from( pos, scalar );
    } else {
      return new Point2D.Float( pos.x, pos.y );
    }
  }
  
  public void update( final float delta ) {
    move.update( delta );
  }
  
  @ FunctionalInterface
  public interface Builder<D extends EntityData> {
    Entity build( D data, int id );
  }
}