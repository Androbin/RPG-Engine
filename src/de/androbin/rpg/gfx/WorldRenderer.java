package de.androbin.rpg.gfx;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.tile.*;

public class WorldRenderer {
  protected TileRenderer tileRenderer;
  
  protected EntityRenderer<Entity> entityRenderer;
  
  public WorldRenderer() {
    this.tileRenderer = new TileRenderer();
    this.entityRenderer = new EntityRenderer<>();
  }
  
  public EntityRenderer<Entity> getEntityRenderer() {
    return entityRenderer;
  }
  
  public TileRenderer getTileRenderer() {
    return tileRenderer;
  }
  
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
}