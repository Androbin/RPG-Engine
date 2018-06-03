package de.androbin.rpg.gfx;

import de.androbin.rpg.entity.*;
import java.awt.*;
import java.awt.geom.*;

public interface EntityRenderer<E extends Entity> {
  default Rectangle2D.Float getBounds( final E entity ) {
    return getBounds( entity.getData(), entity.getFloatPos() );
  }
  
  default Rectangle2D.Float getBounds( final EntityData data, final Point2D.Float pos ) {
    return getBounds( data, pos, data.sheet.rawSize );
  }
  
  Rectangle2D.Float getBounds( EntityData data, Point2D.Float pos, Dimension rawSize );
  
  default void render( final Graphics2D g, final E entity, final Rectangle2D.Float view,
      final float scale ) {
    render( g, entity, entity.getFloatPos(), view, scale );
  }
  
  void render( Graphics2D g, E entity, Point2D.Float pos, Rectangle2D.Float view, float scale );
}