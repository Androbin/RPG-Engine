package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import de.androbin.rpg.gfx.sheet.*;
import de.androbin.rpg.tile.*;

public class TileRenderer {
  public final void render( final Graphics2D g, final Tile tile,
      final Point pos, final float scale ) {
    render( g, tile, new Point2D.Float( pos.x, pos.y ), scale );
  }
  
  public void render( final Graphics2D g, final Tile tile,
      final Point2D.Float pos, final float scale ) {
    final float px = pos.x * scale;
    final float py = pos.y * scale;
    
    drawImage( g, Sheets.getImage( tile ), px, py, scale, scale );
  }
}