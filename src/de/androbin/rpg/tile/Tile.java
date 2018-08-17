package de.androbin.rpg.tile;

import de.androbin.json.*;

public abstract class Tile {
  public XArray enterEvent;
  
  public abstract TileData getData();
  
  @ FunctionalInterface
  public interface Builder<D extends TileData> {
    Tile build( TileData data );
  }
}