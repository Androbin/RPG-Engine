package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import de.androbin.rpg.gfx.sheet.*;
import de.androbin.rpg.tile.*;

public class SimpleTileRenderer<E extends Tile> implements TileRenderer<E> {
  @ Override
  public void render( final Graphics2D g, final E tile,
      final Point2D.Float pos, final float scale ) {
    final BufferedImage image = Sheets.getImage( tile, scale );
    
    final float px = pos.x * scale;
    final float py = pos.y * scale;
    
    drawImage( g, image, px, py, scale, scale );
  }
}