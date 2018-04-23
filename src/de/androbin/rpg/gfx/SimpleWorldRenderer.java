package de.androbin.rpg.gfx;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;

public class SimpleWorldRenderer implements WorldRenderer {
  private TileRenderer<Tile> tileRenderer;
  private EntityRenderer<Entity> entityRenderer;
  
  public SimpleWorldRenderer() {
    tileRenderer = new SimpleTileRenderer<>();
    entityRenderer = new SimpleEntityRenderer<>();
  }
  
  @ Override
  public EntityRenderer<Entity> getEntityRenderer() {
    return entityRenderer;
  }
  
  @ Override
  public TileRenderer<Tile> getTileRenderer() {
    return tileRenderer;
  }
  
  @ Override
  public void render( final Graphics2D g, final World world,
      final Rectangle2D.Float view, final float scale ) {
    LoopUtil.forEach( view, pos -> {
      final Tile tile = world.getTile( pos );
      
      if ( tile != null ) {
        tileRenderer.render( g, tile, pos, scale );
      }
    } );
    
    final Comparator<Entity> comp = ( a, b ) -> {
      final Rectangle boundsA = a.getBounds();
      final Rectangle boundsB = b.getBounds();
      
      return Float.compare(
          boundsA.y + boundsA.height + ( a.data.solid ? 0.5f : 0f ),
          boundsB.y + boundsB.height + ( b.data.solid ? 0.5f : 0f ) );
    };
    world.listEntities().stream()
        .filter( entity -> entityRenderer.getBounds( entity ).intersects( view ) )
        .sorted( comp )
        .forEachOrdered( entity -> entityRenderer.render( g, entity, scale ) );
  }
  
  @ Override
  public void setEntityRenderer( final EntityRenderer<Entity> renderer ) {
    entityRenderer = renderer;
  }
  
  @ Override
  public void setTileRenderer( final TileRenderer<Tile> renderer ) {
    tileRenderer = renderer;
  }
}