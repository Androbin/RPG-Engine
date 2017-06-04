package de.androbin.rpg.gfx;

import java.awt.*;
import java.awt.geom.*;

public interface Sprite {
  Rectangle getBounds();
  
  Rectangle2D.Float getViewBounds();
  
  void render( Graphics2D g, float scale );
}