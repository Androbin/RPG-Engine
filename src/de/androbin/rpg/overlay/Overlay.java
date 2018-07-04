package de.androbin.rpg.overlay;

import de.androbin.shell.*;
import java.awt.*;
import java.awt.geom.*;

public interface Overlay extends Shell {
  default void attach() {
  }
  
  default void detach() {
  }
  
  default boolean isFreezing() {
    return false;
  }
  
  default boolean isMasking() {
    return false;
  }
  
  default void renderScreen( Graphics2D g ) {
  }
  
  default void renderWorld( Graphics2D g, Rectangle2D.Float view, float scale ) {
  }
}