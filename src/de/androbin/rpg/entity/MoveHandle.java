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
  
  public boolean canHandle( final Direction dir ) {
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
    if ( !canHandle( dir ) ) {
      return false;
    }
    
    final Rectangle target = dir.expand( agent.getBounds() );
    return agent.world.getSpaceTime( agent ).trySet( agent, target );
  }
  
  private boolean expand( final Direction dir, final boolean naive ) {
    final boolean success = expand( dir );
    
    if ( agent.orientation != null && ( success || naive ) ) {
      agent.orientation = dir;
    }
    
    return success;
  }
  
  @ Override
  protected boolean prepare( final DirectionPair dir ) {
    return expand( dir.first, true );
  }
  
  private void merge() {
    if ( next == null ) {
      current = new DirectionPair( current.first );
    } else if ( next.second == null ) {
      if ( next.first != current.first.opposite() ) {
        current = new DirectionPair( current.first, next.first );
        next = null;
      }
    } else if ( next.first == current.first ) {
      current = next;
      next = null;
    } else if ( next.second == current.first ) {
      current = next.reverse();
      next = null;
    }
  }
  
  public void request( final Direction dir ) {
    request( new DirectionPair( dir ) );
  }
  
  public void request( final DirectionPair dir ) {
    next = dir;
  }
  
  private void sanitize() {
    if ( next.second == null ) {
      return;
    }
    
    if ( expand( next.first, true ) ) {
      current = new DirectionPair( next.first );
    } else if ( expand( next.second, false ) ) {
      current = new DirectionPair( next.second );
    }
  }
  
  @ Override
  public void update( final float delta ) {
    if ( current == null && next != null ) {
      sanitize();
    }
    
    final float before = getProgress();
    super.update( delta );
    final float after = getProgress();
    
    if ( current == null || before >= 0.5f || after < 0.5f ) {
      return;
    }
    
    merge();
    contract( current.first );
    
    if ( current.second == null ) {
      return;
    }
    
    if ( expand( current.second, false ) ) {
      rewind( 0.5f );
      current = current.reverse();
    } else {
      current = new DirectionPair( current.first );
    }
  }
}