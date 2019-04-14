package de.androbin.rpg;

import java.awt.*;

public interface Shape {
  boolean contains( Point p );
  
  Dimension getSize();
}