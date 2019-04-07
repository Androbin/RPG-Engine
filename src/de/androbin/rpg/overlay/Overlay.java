package de.androbin.rpg.overlay;

import de.androbin.rpg.*;
import de.androbin.shell.*;
import java.awt.*;
import java.awt.geom.*;

public interface Overlay extends Shell {
  default void attach() {
  }
  
  default void detach() {
  }
  
  default Intervention getIntervention() {
    return Intervention.TRANSPARENT;
  }
  
  default void renderScreen( Graphics2D g ) {
  }
  
  default void renderWorld( Graphics2D g, Rectangle2D.Float view, float scale ) {
  }
}