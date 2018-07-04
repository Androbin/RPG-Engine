package de.androbin.rpg.overlay;

import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.shell.*;
import java.util.*;

public final class MoveOverlay extends AbstractShell implements Overlay {
  private final MoveHandle move;
  private final Queue<Direction> dirs;
  
  public MoveOverlay( final MoveHandle move, final Direction[] dirs ) {
    this.move = move;
    this.dirs = new ArrayDeque<>( Arrays.asList( dirs ) );
  }
  
  @ Override
  public void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void update( final float delta ) {
    if ( move.getNext() != null ) {
      return;
    }
    
    if ( dirs.isEmpty() ) {
      if ( move.getCurrent() == null ) {
        setRunning( false );
      }
    } else {
      move.request( dirs.remove() );
    }
  }
}