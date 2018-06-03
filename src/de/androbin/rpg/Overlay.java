package de.androbin.rpg;

import de.androbin.shell.*;
import java.awt.*;
import java.awt.geom.*;

public interface Overlay extends Shell {
  default void renderScreen( Graphics2D g ) {
  }
  
  default void renderWorld( Graphics2D g, Rectangle2D.Float view, float scale ) {
  }
}