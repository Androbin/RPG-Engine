package de.androbin.rpg.event;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;
import java.util.logging.*;

public final class TileEnterEvent implements Event {
  public Tile tile;
  public Entity entity;
  
  public TileEnterEvent( final Tile tile, final Entity entity ) {
    this.tile = tile;
    this.entity = entity;
  }
  
  @ Override
  public void log( final Logger logger ) {
    logger.finest( "tileEnter" );
  }
}