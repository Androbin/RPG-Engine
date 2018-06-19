package de.androbin.rpg.gfx.sheet;

import de.androbin.rpg.tile.*;
import java.awt.*;

@ FunctionalInterface
public interface TileLayout<T extends Tile> {
  Point locate( T tile, Point pos, Dimension size );
}