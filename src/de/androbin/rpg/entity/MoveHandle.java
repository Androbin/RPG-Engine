package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.tile.*;
import de.androbin.rpg.world.*;
import java.awt.*;
import java.util.List;
import java.util.function.*;

public final class MoveHandle extends Handle<DirectionPair, Void> {
  private final Agent agent;
  private State state;
  
  public float speed;
  public Predicate<Point> bounds;
  
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
    final World world = agent.getSpot().world;
    return agent.getBounds().outer( dir ).stream().allMatch( pos -> {
      final Tile tile = world.tiles.get( pos );
      return tile != null && tile.getData().passable
          && ( bounds == null || bounds.test( pos ) );
    } );
  }
  
  private void contract( final Direction dir ) {
    final Spot spot = agent.getSpot();
    final World world = spot.world;
    
    spot.move( dir );
    world.entities.move( agent, agent.getBounds() );
    
    final List<Point> inner = agent.getBounds().inner( dir );
    
    inner.forEach( pos -> {
      final Tile tile = world.tiles.get( pos );
      Events.QUEUE.enqueue( new TileEnterEvent( tile, agent ) );
    } );
  }
  
  private boolean expand( final Direction dir ) {
    if ( !canMove( dir ) ) {
      return false;
    }
    
    final World world = agent.getSpot().world;
    final Bounds target = agent.getBounds().expand( dir );
    return world.entities.tryMove( agent, target );
  }
  
  private boolean expand( final DirectionPair dir, final boolean naive ) {
    final boolean success = expand( dir.first );
    
    if ( success || naive ) {
      agent.orientation = new DirectionPair( dir.first );
    }
    
    return success;
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
      request( current.second );
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
      request( (DirectionPair) null );
      return;
    }
    
    request( new DirectionPair( dir ) );
  }
  
  @ Override
  public void reset() {
    super.reset();
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