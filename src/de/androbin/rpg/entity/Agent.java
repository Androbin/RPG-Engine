package de.androbin.rpg.entity;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import java.awt.geom.*;
import java.util.*;

public abstract class Agent extends Entity {
  public DirectionPair orientation;
  
  public final MoveHandle move;
  
  public Agent( final int id ) {
    super( id );
    
    this.move = new MoveHandle( this );
  }
  
  @ Override
  public Point2D.Float getFloatPos() {
    final DirectionPair dir = move.getCurrent();
    
    if ( dir == null ) {
      return super.getFloatPos();
    }
    
    final float scalar = move.interpolate();
    return dir.fromAskew( getSpot().getPos(), scalar );
  }
  
  @ Override
  public void load( final XObject details ) {
    super.load( details );
    details.get( "orientation" ).apply(
        value -> orientation = DirectionPair.parse( value.asString() ) );
  }
  
  @ Override
  public void save( final Map<String, Object> details ) {
    super.save( details );
    
    if ( orientation != null ) {
      details.put( "orientation", orientation.toString() );
    }
  }
  
  @ Override
  public void setSpot( final Spot spot ) {
    super.setSpot( spot );
    orientation = new DirectionPair( Direction.DOWN );
    move.reset();
  }
  
  public void update( final float delta, final boolean passive ) {
    move.update( delta );
  }
}