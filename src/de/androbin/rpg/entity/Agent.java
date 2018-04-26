package de.androbin.rpg.entity;

import de.androbin.rpg.dir.*;
import java.awt.geom.*;

public class Agent extends Entity {
  public DirectionPair orientation;
  
  public final MoveHandle move;
  
  public Agent( final EntityData data, final int id ) {
    super( data, id );
    
    this.orientation = new DirectionPair( Direction.DOWN );
    
    this.move = new MoveHandle( this );
  }
  
  @ Override
  public Point2D.Float getFloatPos() {
    if ( move.getCurrent() == null ) {
      return super.getFloatPos();
    }
    
    final DirectionPair dir = move.getCurrent();
    final float scalar = move.interpolate();
    return dir.from( pos, scalar );
  }
  
  @ Override
  public void update( final float delta ) {
    move.update( delta );
  }
}