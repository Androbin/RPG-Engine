package de.androbin.rpg.gfx;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.world.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class SimpleEntityLayerRenderer implements EntityLayerRenderer {
  @ Override
  public void render( final Graphics2D g, final EntityLayer entities,
      final Rectangle2D.Float view, final float scale ) {
    final Comparator<Entity> comp = ( a, b ) -> {
      final Rectangle boundsA = a.getBounds().getBounds();
      final Rectangle boundsB = b.getBounds().getBounds();
      
      return Float.compare(
          boundsA.y + boundsA.height + ( a.getData().solid ? 0.5f : 0f ),
          boundsB.y + boundsB.height + ( b.getData().solid ? 0.5f : 0f ) );
    };
    
    entities.list()
        .stream()
        .sorted( comp )
        .forEachOrdered( entity -> {
          final EntityRenderer<Entity> renderer = Renderers.getRenderer( entity );
          renderer.render( g, entity, view, scale );
        } );
  }
}