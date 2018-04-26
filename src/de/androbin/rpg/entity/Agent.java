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
    }
    
    return super.getFloatPos();
  }
  
  @ Override
  public void update( final float delta ) {
    move.update( delta );
  }
}