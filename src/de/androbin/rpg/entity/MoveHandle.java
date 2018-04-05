package de.androbin.rpg.entity;

import java.awt.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.tile.*;

public final class MoveHandle extends Handle<Direction, Void> {
  private final Entity entity;
  
  public MoveHandle( final Entity entity ) {
    this.entity = entity;
  }
  
  @ Override
  public boolean canHandle( final Direction dir ) {
    return LoopUtil.and( dir.outer( entity.getBounds() ), pos -> {
      final Tile tile = entity.world.getTile( pos );
      return tile != null && tile.data.passable;
    } );
  }
  
  @ Override
  protected Void doHandle( final Direction dir ) {
    entity.pos = dir.from( entity.pos );
    
    final Rectangle target = new Rectangle( entity.pos, entity.data.size );
    entity.world.getSpaceTime( entity ).set( entity, target );
    
    LoopUtil.forEach( dir.inner( entity.getBounds() ), pos -> {
      final Tile tile = entity.world.getTile( pos );
      Events.QUEUE.enqueue( new TileEnterEvent( tile, entity ) );
    } );
    
    return null;
  }
  
  @ Override
  protected boolean handle( final Direction dir ) {
    if ( entity.orientation != null ) {
      entity.orientation = dir;
    }
    
    if ( !canHandle( dir ) ) {
      return false;
    }
    
    final Rectangle target = dir.expand( entity.getBounds() );
    return entity.world.getSpaceTime( entity ).trySet( entity, target );
  }
}