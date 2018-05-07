package de.androbin.rpg.world;

import java.awt.*;

public class Layer {
  public final World world;
  
  public Layer( final World world ) {
    this.world = world;
  }
  
  public final boolean checkBounds( final Point pos ) {
    return world.checkBounds( pos );
  }
}