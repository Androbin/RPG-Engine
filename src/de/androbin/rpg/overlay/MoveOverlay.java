package de.androbin.rpg.overlay;

import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.shell.*;
import java.util.*;

public final class MoveOverlay extends AbstractShell implements Overlay {
  private final MoveHandle move;
  private final Queue<Direction> dirs;
  private final boolean autoStop;
  
  public MoveOverlay( final MoveHandle move, final List<Direction> dirs, final boolean autoStop ) {
    this.move = move;
    this.dirs = new ArrayDeque<>( dirs );
    this.autoStop = autoStop;
  }
  
  @ Override
  public void attach() {
    if ( autoStop ) {
      move.onPrepare = ( dir, success ) -> {
        if ( !success ) {
          move.reset();
        }
      };
    }
  }
  
  @ Override
  public void detach() {
    if ( autoStop ) {
      move.onPrepare = null;
    }
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