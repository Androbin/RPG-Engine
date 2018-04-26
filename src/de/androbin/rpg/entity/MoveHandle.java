package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.tile.*;
import java.awt.*;

public final class MoveHandle extends Handle<DirectionPair, Void> {
  private final Agent agent;
  
  public MoveHandle( final Agent agent ) {
    this.agent = agent;
  }
  
  private boolean canMove( final Direction dir ) {
    return LoopUtil.and( dir.outer( agent.getBounds() ), pos -> {
      final Tile tile = agent.world.getTile( pos );
      return tile != null && tile.data.passable;
    } );
  }
  
  private void contract( final Direction dir ) {
    agent.pos = current.first.from( agent.pos );
    
    final Rectangle target = new Rectangle( agent.pos, agent.data.size );
    agent.world.getSpaceTime( agent ).set( agent, target );
    
    LoopUtil.forEach( dir.inner( agent.getBounds() ), pos -> {
      final Tile tile = agent.world.getTile( pos );
      Events.QUEUE.enqueue( new TileEnterEvent( tile, agent ) );
    } );
  }
  
  private boolean expand( final Direction dir ) {
    if ( !canMove( dir ) ) {
      return false;
    }
    
    final Rectangle target = dir.expand( agent.getBounds() );
    return agent.world.getSpaceTime( agent ).trySet( agent, target );
  }
  
  private boolean expand( final DirectionPair dir, final boolean dim, final boolean naive ) {
    final boolean success = expand( dir.first );
    
    if ( success || naive ) {
      agent.orientation = dim ? dir : new DirectionPair( dir.first );
    }
    
    return success;
  }
  
  @ Override
  protected void handle( final DirectionPair dir ) {
    super.handle( dir );
    contract( current.first );
    
    if ( current.second == null ) {
      return;
    }
    
    if ( expand( current.reverse(), true, false ) ) {
      rewind( 0.5f );
      setCurrent( current.reverse() );
    } else {
      setCurrent( new DirectionPair( current.first ) );
    }
  }
  
  @ Override
  protected boolean prepare( final DirectionPair dir ) {
    return expand( dir, false, true );
  }
  
  @ Override
  protected DirectionPair merge( final DirectionPair next ) {
    if ( next == null ) {
      return new DirectionPair( current.first );
    } else if ( next.second == null ) {
      if ( next.first != current.first.opposite() ) {
        return new DirectionPair( current.first, next.first );
      }
    } else if ( next.first == current.first ) {
      return next;
    } else if ( next.second == current.first ) {
      return next.reverse();
    }
    
    return null;
  }
  
  public void request( final Direction dir ) {
    if ( dir == null ) {
      request( (DirectionPair) null );
      return;
    }
    
    request( new DirectionPair( dir ) );
  }
  
  @ Override
  protected DirectionPair sanitize( final DirectionPair dir ) {
    if ( dir.second == null ) {
      return dir;
    }
    
    if ( expand( dir, false, true ) ) {
      return new DirectionPair( dir.first );
    } else if ( expand( dir.reverse(), false, false ) ) {
      return new DirectionPair( dir.second );
    }
    
    return null;
  }
  
  @ Override
  protected void setCurrent( final DirectionPair dir ) {
    super.setCurrent( dir );
    
    if ( dir == null ) {
      return;
    }
    
    agent.orientation = dir;
  }
  
  @ Override
  public void update( final float delta ) {
    float speed = 1f;
    
    if ( current != null && current.second != null ) {
      speed *= Math.sqrt( 0.5f );
    }
    
    super.update( delta * speed );
  }
}