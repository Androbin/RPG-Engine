package de.androbin.rpg.gfx;

import de.androbin.rpg.world.*;
import java.awt.*;
import java.awt.geom.*;

public interface WorldRenderer<W extends World> {
  EntityLayerRenderer getEntityRenderer();
  
  TileLayerRenderer getTileRenderer();
  
  void render( Graphics2D g, W world, Rectangle2D.Float view, float scale );
  
  void setEntityRenderer( EntityLayerRenderer renderer );
  
  void setTileRenderer( TileLayerRenderer renderer );
}