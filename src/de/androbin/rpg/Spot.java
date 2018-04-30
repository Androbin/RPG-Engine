package de.androbin.rpg;

import de.androbin.rpg.dir.*;
import java.awt.*;

public final class Spot {
  public final World world;
  private Point pos;
  
  public Spot( final World world, final Point pos ) {
    this.world = world;
    this.pos = pos;
  }
  
  public Point getPos() {
    return pos;
  }
  
  public boolean hasPos( final Point pos ) {
    return this.pos.equals( pos );
  }
  
  public void move( final Direction dir ) {
    pos = dir.from( pos );
  }
}