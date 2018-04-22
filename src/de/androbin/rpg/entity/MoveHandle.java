package de.androbin.rpg.entity;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.tile.*;
import java.awt.*;

public final class MoveHandle extends Handle<DirectionPair, Void> {
  private final Entity entity;
  
  public MoveHandle( final Entity entity ) {
    this.entity = entity;
  }
  
  @ Override
  public boolean canHandle( final DirectionPair dir ) {
    return canHandle( dir.first );
  }
  
  public boolean canHandle( final Direction dir ) {
    return LoopUtil.and( dir.outer( entity.getBounds() ), pos -> {
      final Tile tile = entity.world.getTile( pos );
      return tile != null && tile.data.passable;
    } );
  }
  
  private void contract( final Direction dir ) {
    entity.pos = current.first.from( entity.pos );
    
    final Rectangle target = new Rectangle( entity.pos, entity.data.size );
    entity.world.getSpaceTime( entity ).set( entity, target );
    
    LoopUtil.forEach( dir.inner( entity.getBounds() ), pos -> {
      final Tile tile = entity.world.getTile( pos );
      Events.QUEUE.enqueue( new TileEnterEvent( tile, entity ) );
    } );
  }
  
  @ Override
  protected Void doHandle( final DirectionPair dir ) {
    return null;
  }
  
  private boolean expand( final Direction dir ) {
    if ( !canHandle( dir ) ) {
      return false;
    }
    
    final Rectangle target = dir.expand( entity.getBounds() );
    return entity.world.getSpaceTime( entity ).trySet( entity, target );
  }
  
  private boolean expand( final Direction dir, final boolean naive ) {
    final boolean success = expand( dir );
    
    if ( entity.orientation != null && ( success || naive ) ) {
      entity.orientation = dir;
    }
    
    return success;
  }
  
  @ Override
  protected boolean handle( final DirectionPair dir ) {
    return expand( dir.first, true );
  }
  
  @ Override
  public void update( final float delta ) {
    if ( current == null && next != null && next.second != null ) {
      if ( expand( next.first, true ) ) {
        current = new DirectionPair( next.first );
      }
    }
    
    final float before = getProgress();
    super.update( delta );
    final float after = getProgress();
    
    if ( current == null || before >= 0.5f || after < 0.5f ) {
      return;
    }
    
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
    
    contract( current.first );
    
    if ( current.second == null ) {
      return;
    }
    
    if ( expand( current.second, false ) ) {
      progress -= 0.5f;
      current = current.reverse();
    } else {
      current = new DirectionPair( current.first );
    }
  }
}