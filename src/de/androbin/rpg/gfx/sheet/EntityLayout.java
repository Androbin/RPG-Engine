package de.androbin.rpg.gfx.sheet;

import de.androbin.rpg.entity.*;
import java.awt.*;

@ FunctionalInterface
public interface EntityLayout<E extends Entity> {
  Point locate( E entity, Dimension size );
}