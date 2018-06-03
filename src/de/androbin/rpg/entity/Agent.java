package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import java.awt.geom.*;

public abstract class Agent extends Entity {
  public DirectionPair orientation;
  
  public final MoveHandle move;
  
  public Agent( final int id ) {
    super( id );
    
    this.move = new MoveHandle( this );
  }
  
  @ Override
  public Point2D.Float getFloatPos() {
    if ( move.getCurrent() == null ) {
      return super.getFloatPos();
    }
    
    final DirectionPair dir = move.getCurrent();
    final float scalar = move.interpolate();
    return dir.from( getSpot().getPos(), scalar );
  }
  
  @ Override
  public void setSpot( final Spot spot ) {
    super.setSpot( spot );
    orientation = new DirectionPair( Direction.DOWN );
    move.reset();
  }
  
  public void update( final float delta ) {
    move.update( delta );
  }
}