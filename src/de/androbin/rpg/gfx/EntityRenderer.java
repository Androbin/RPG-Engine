package de.androbin.rpg.gfx;

import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.sheet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public interface EntityRenderer<E extends Entity> {
  default Rectangle2D.Float getBounds( final E entity ) {
    return getBounds( entity, entity.getFloatPos() );
  }
  
  default Rectangle2D.Float getBounds( final E entity, final Point2D.Float pos ) {
    return getBounds( entity.data, Sheets.getImage( entity ), pos );
  }
  
  Rectangle2D.Float getBounds( EntityData data, BufferedImage image, Point2D.Float pos );
  
  default void render( final Graphics2D g, final E entity, final float scale ) {
    render( g, entity, entity.getFloatPos(), scale );
  }
  
  void render( Graphics2D g, E entity, Point2D.Float pos, float scale );
}