package de.androbin.rpg;

import java.awt.*;
import java.awt.geom.*;

public interface Sprite {
  Rectangle getBounds();
  
  Rectangle2D.Float getViewBounds();
  
  void render( Graphics2D g, float scale );
}