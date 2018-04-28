package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.tile.*;
import java.awt.*;

public final class MoveHandle extends Handle<DirectionPair, Void> {
  private final Agent agent;
  private State state;
  
  public float speed;
  
  public MoveHandle( final Agent agent ) {
    this.agent = agent;
    
    speed = 1f;
  }
  
  @ Override
  protected float calcSpeed( final DirectionPair current ) {
    float speed = this.speed;
    
    if ( current != null && current.second != null ) {
      // assert state == State.CONST
      speed *= Math.sqrt( 0.5f );
    } else if ( state != State.CONST ) {
      speed *= 2f / 3f;
    }
    
    return speed;
  }
  
  private boolean canMove( final Direction dir ) {
    return LoopUtil.and( dir.outer( agent.getBounds() ), pos -> {
      final Tile tile = agent.world.getTile( pos );
      return tile != null && tile.data.passable;
    } );
  }
  
  private void contract( final Direction dir ) {
    agent.pos = dir.from( agent.pos );
    
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
  
  private boolean expand( final DirectionPair dir, final boolean naive ) {
    final boolean success = expand( dir.first );
    
    if ( success || naive ) {
      agent.orientation = new DirectionPair( dir.first );
    }
    
    return success;
  }
  
  public State getState() {
    return state;
  }
  
  @ Override
  protected void handle( final DirectionPair current, final boolean next ) {
    contract( current.first );
    
    if ( current.second == null ) {
      state = next ? State.CONST : State.DEC;
      return;
    }
    
    state = State.CONST;
    
    if ( expand( current.reverse(), false ) ) {
      rewind( 0.5f );
      setCurrent( current.reverse() );
    } else {
      setCurrent( new DirectionPair( current.first ) );
    }
  }
  
  public float interpolate() {
    if ( state == State.ZERO ) {
      return 0f;
    }
    
    final DirectionPair current = getCurrent();
    final float progress = getProgress();
    
    if ( current.second != null ) {
      // assert state == State.CONST
      return progress;
    }
    
    float scalar = progress;
    
    switch ( state ) {
      case ACC:
      case DEC: {
        scalar = -2f * progress * progress * progress + 3f * progress * progress;
        break;
      }
    }
    
    if ( progress >= 0.5f ) {
      scalar -= 1f;
    }
    
    return scalar;
  }
  
  @ Override
  protected boolean prepare( final DirectionPair next ) {
    state = state == State.ZERO ? State.ACC : State.CONST;
    return expand( next, true );
  }
  
  @ Override
  protected DirectionPair merge( final DirectionPair current, final DirectionPair next ) {
    if ( next == null ) {
      return new DirectionPair( current.first );
    } else if ( next.second == null ) {
      if ( next.first == current.first ) {
        setCurrent( next );
      } else if ( next.first != current.first.opposite() ) {
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
      // request( (DirectionPair) null );
      return;
    }
    
    request( new DirectionPair( dir ) );
  }
  
  @ Override
  public void request( final DirectionPair next ) {
    if ( next == null ) {
      return;
    }
    
    super.request( next );
  }
  
  @ Override
  protected void reset() {
    state = State.ZERO;
  }
  
  @ Override
  protected DirectionPair sanitize( final DirectionPair next ) {
    if ( next.second == null ) {
      return next;
    }
    
    if ( expand( next, true ) ) {
      return new DirectionPair( next.first );
    } else if ( expand( next.reverse(), false ) ) {
      return new DirectionPair( next.second );
    }
    
    return null;
  }
  
  @ Override
  protected void setCurrent( final DirectionPair current ) {
    super.setCurrent( current );
    
    if ( current == null ) {
      return;
    }
    
    agent.orientation = current;
  }
  
  public enum State {
    ZERO, ACC, CONST, DEC;
  }
}