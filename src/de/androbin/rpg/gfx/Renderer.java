package de.androbin.rpg.gfx;

import java.awt.*;
import java.awt.geom.*;

public interface Renderer {
  default Rectangle2D.Float getBounds() {
    return getBounds( getPos() );
  }
  
  Rectangle2D.Float getBounds( Point2D.Float pos );
  
  Point2D.Float getPos();
  
  default void render( final Graphics2D g, final float scale ) {
    render( g, getPos(), scale );
  }
  
  default void render( final Graphics2D g, final Point pos, final float scale ) {
    render( g, new Point2D.Float( pos.x, pos.y ), scale );
  }
  
  void render( Graphics2D g, Point2D.Float pos, float scale );
}