package de.androbin.rpg.world;

import de.androbin.mixin.dim.*;
import de.androbin.rpg.tile.*;
import java.awt.*;

public final class TileLayer {
  public final Dimension size;
  private final Tile[] tiles;
  
  public TileLayer( final Dimension size ) {
    this.size = size;
    tiles = new Tile[ size.width * size.height ];
  }
  
  private int calcIndex( final Point pos ) {
    return pos.y * size.width + pos.x;
  }
  
  public boolean checkBounds( final Point pos ) {
    return StaticUtil.contains( size, pos );
  }
  
  public Tile get( final Point pos ) {
    if ( !checkBounds( pos ) ) {
      return null;
    }
    
    return tiles[ calcIndex( pos ) ];
  }
  
  public boolean set( final Point pos, final Tile tile ) {
    if ( !checkBounds( pos ) ) {
      return false;
    }
    
    tiles[ calcIndex( pos ) ] = tile;
    return true;
  }
}