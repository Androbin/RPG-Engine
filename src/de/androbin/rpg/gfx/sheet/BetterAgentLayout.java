package de.androbin.rpg.gfx.sheet;

import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import java.awt.*;

public final class BetterAgentLayout implements EntityLayout<Agent> {
  @ Override
  public Point locate( final Agent agent, final Dimension size ) {
    final DirectionPair dir = agent.orientation;
    final int steps = size.width / 2;
    
    final int x;
    final int y;
    
    if ( dir.second == null ) {
      x = (int) ( agent.move.getProgress() * steps );
      y = dir.first.ordinal();
    } else {
      final int a = dir.first.ordinal();
      final int b = dir.second.ordinal();
      x = (int) ( agent.move.getProgress() * steps ) + steps;
      y = a == ( b + 1 ) % 4 ? a : b;
    }
    
    return new Point( x, y );
  }
}