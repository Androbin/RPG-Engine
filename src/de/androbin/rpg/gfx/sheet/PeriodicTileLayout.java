package de.androbin.rpg.gfx.sheet;

import de.androbin.rpg.tile.*;
import java.awt.*;

public final class PeriodicTileLayout implements TileLayout<Tile> {
  @ Override
  public Point locate( final Tile tile, final Point pos, final Dimension size ) {
    return new Point( pos.x % size.width, pos.y % size.height );
  }
}