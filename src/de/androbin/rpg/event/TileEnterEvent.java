package de.androbin.rpg.event;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;

public final class TileEnterEvent extends Event {
  public Tile tile;
  public Entity entity;
  
  public TileEnterEvent( final Tile tile, final Entity entity ) {
    this.tile = tile;
    this.entity = entity;
  }
  
  @ Override
  public String getMessage() {
    return null;
  }
}