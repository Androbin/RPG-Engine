package de.androbin.rpg.tile;

import de.androbin.rpg.event.*;

public class Tile {
  public final TileData data;
  
  public Event.Raw enterEvent;
  
  public Tile( final TileData data ) {
    this.data = data;
  }
  
  @ FunctionalInterface
  public interface Builder<D extends TileData> {
    Tile build( TileData data );
  }
}