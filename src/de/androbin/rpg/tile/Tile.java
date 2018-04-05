package de.androbin.rpg.tile;

import java.util.*;
import java.util.function.*;
import de.androbin.rpg.event.*;

public class Tile {
  public final TileData data;
  
  public Function<Map<String, Object>, Event> enterEvent;
  
  public Tile( final TileData data ) {
    this.data = data;
  }
  
  @ FunctionalInterface
  public interface Builder<D extends TileData> {
    Tile build( TileData data );
  }
}