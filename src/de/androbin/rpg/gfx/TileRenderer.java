package de.androbin.rpg.gfx;

import de.androbin.rpg.tile.*;
import java.awt.*;
import java.awt.geom.*;

public interface TileRenderer<T extends Tile> {
  default void render( final Graphics2D g, final T tile,
      final Point pos, final float scale ) {
    render( g, tile, new Point2D.Float( pos.x, pos.y ), scale );
  }
  
  void render( Graphics2D g, T tile, Point2D.Float pos, float scale );
}