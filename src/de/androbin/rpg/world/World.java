package de.androbin.rpg.world;

import de.androbin.rpg.*;
import java.awt.*;

public class World {
  public final Ident id;
  public final Dimension size;
  
  public final TileLayer tiles;
  public final EntityLayer entities;
  
  public World( final Ident id, final Dimension size ) {
    this.id = id;
    this.size = size;
    
    tiles = new TileLayer( this );
    entities = new EntityLayer( this );
  }
  
  public final boolean checkBounds( final Point pos ) {
    return pos.x >= 0 && pos.x < size.width
        && pos.y >= 0 && pos.y < size.height;
  }
}