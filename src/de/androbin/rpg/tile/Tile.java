package de.androbin.rpg.tile;

public abstract class Tile {
  public String enterEvent;
  
  public abstract TileData getData();
  
  @ FunctionalInterface
  public interface Builder<D extends TileData> {
    Tile build( TileData data );
  }
}