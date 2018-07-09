package de.androbin.rpg.world;

import de.androbin.mixin.dim.*;
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
    
    tiles = new TileLayer( size );
    entities = new EntityLayer( this );
  }
  
  public boolean checkBounds( final Point pos ) {
    return StaticUtil.contains( size, pos );
  }
  
  @ Override
  public String toString() {
    return id.toString();
  }
}