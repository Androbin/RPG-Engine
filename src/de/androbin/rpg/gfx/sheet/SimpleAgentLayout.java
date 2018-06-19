package de.androbin.rpg.gfx.sheet;

import de.androbin.rpg.entity.*;
import java.awt.*;

public final class SimpleAgentLayout implements EntityLayout<Agent> {
  @ Override
  public Point locate( final Agent agent, final Dimension size ) {
    final int x = (int) ( agent.move.getProgress() * size.width );
    final int y = agent.orientation.first.ordinal();
    
    return new Point( x, y );
  }
}