package de.androbin.rpg.world;

import de.androbin.rpg.tile.*;
import java.awt.*;

public final class TileLayer extends Layer {
  private final Tile[] tiles;
  
  public TileLayer( final World world ) {
    super( world );
    
    final Dimension size = world.size;
    tiles = new Tile[ size.width * size.height ];
  }
  
  private int calcIndex( final Point pos ) {
    return pos.y * world.size.width + pos.x;
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