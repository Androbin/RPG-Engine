package de.androbin.rpg.gfx;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;
import de.androbin.rpg.world.*;
import java.awt.*;
import java.awt.geom.*;

public interface WorldRenderer {
  EntityRenderer<Entity> getEntityRenderer();
  
  TileRenderer<Tile> getTileRenderer();
  
  void render( Graphics2D g, World world, Rectangle2D.Float view, float scale );
  
  void setEntityRenderer( EntityRenderer<Entity> renderer );
  
  void setTileRenderer( TileRenderer<Tile> renderer );
}