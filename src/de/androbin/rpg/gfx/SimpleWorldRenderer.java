package de.androbin.rpg.gfx;

import de.androbin.rpg.world.*;
import java.awt.*;
import java.awt.geom.*;

public class SimpleWorldRenderer implements WorldRenderer<World> {
  private TileLayerRenderer tileRenderer;
  private EntityLayerRenderer entityRenderer;
  
  public SimpleWorldRenderer() {
    tileRenderer = new SimpleTileLayerRenderer();
    entityRenderer = new SimpleEntityLayerRenderer();
  }
  
  @ Override
  public EntityLayerRenderer getEntityRenderer() {
    return entityRenderer;
  }
  
  @ Override
  public TileLayerRenderer getTileRenderer() {
    return tileRenderer;
  }
  
  @ Override
  public void render( final Graphics2D g, final World world,
      final Rectangle2D.Float view, final float scale ) {
    tileRenderer.render( g, world.tiles, view, scale );
    entityRenderer.render( g, world.entities, view, scale );
  }
  
  @ Override
  public void setEntityRenderer( final EntityLayerRenderer renderer ) {
    entityRenderer = renderer;
  }
  
  @ Override
  public void setTileRenderer( final TileLayerRenderer renderer ) {
    tileRenderer = renderer;
  }
}