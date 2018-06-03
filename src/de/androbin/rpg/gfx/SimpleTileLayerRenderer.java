package de.androbin.rpg.gfx;

import de.androbin.mixin.dim.*;
import de.androbin.rpg.tile.*;
import de.androbin.rpg.world.*;
import java.awt.*;
import java.awt.geom.*;

public class SimpleTileLayerRenderer implements TileLayerRenderer {
  @ Override
  public void render( final Graphics2D g, final TileLayer tiles,
      final Rectangle2D.Float view, final float scale ) {
    LoopUtil.forEach( view, pos -> {
      final Tile tile = tiles.get( pos );
      
      if ( tile == null ) {
        return;
      }
      
      final TileRenderer<Tile> renderer = Renderers.getRenderer( tile );
      renderer.render( g, tile, pos, scale );
    } );
  }
}