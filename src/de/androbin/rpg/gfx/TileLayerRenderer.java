package de.androbin.rpg.gfx;

import de.androbin.rpg.world.*;
import java.awt.*;
import java.awt.geom.*;

public interface TileLayerRenderer {
  void render( Graphics2D g, TileLayer tiles, Rectangle2D.Float view, float scale );
}