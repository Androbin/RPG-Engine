package de.androbin.rpg.gfx;

import java.awt.*;
import java.awt.geom.*;

public interface Renderer {
  default Rectangle2D.Float getBounds( final Point pos ) {
    return getBounds( new Point2D.Float( pos.x, pos.y ) );
  }
  
  Rectangle2D.Float getBounds( Point2D.Float pos );
  
  default void render( final Graphics2D g, final Point pos, final float scale ) {
    render( g, new Point2D.Float( pos.x, pos.y ), scale );
  }
  
  void render( Graphics2D g, Point2D.Float pos, float scale );
}