package de.androbin.rpg.tile;

public final class SimpleTile extends Tile {
  private final TileData data;
  
  public SimpleTile( final TileData data ) {
    this.data = data;
  }
  
  @ Override
  public TileData getData() {
    return data;
  }
}