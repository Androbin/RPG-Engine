package de.androbin.rpg;

import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.shell.input.*;
import java.util.function.*;

public final class MoveKeyInput implements KeyInput {
  private final Supplier<DirectionPair> getter;
  private final Consumer<DirectionPair> setter;
  
  public MoveKeyInput( final Supplier<DirectionPair> getter,
      final Consumer<DirectionPair> setter ) {
    this.getter = getter;
    this.setter = setter;
  }
  
  public static boolean applyRequest( final MoveHandle move, final DirectionPair dir ) {
    if ( checkRequest( move, dir ) ) {
      move.request( dir );
      return true;
    }
    
    return false;
  }
  
  public static boolean checkRequest( final MoveHandle move, final DirectionPair dir ) {
    final DirectionPair current = move.getCurrent();
    
    if ( current == null || dir == null ) {
      return true;
    }
    
    if ( current.second != null || dir.second != null ) {
      return true;
    }
    
    if ( current.first != dir.first ) {
      return true;
    }
    
    return move.getProgress() >= 0.4f;
  }
  
  @ Override
  public void keyPressed( final int keycode ) {
    final Direction dir = Directions.byKeyCode( keycode );
    
    if ( dir == null ) {
      return;
    }
    
    final DirectionPair current = getter.get();
    
    if ( current == null ) {
      setter.accept( new DirectionPair( dir ) );
    } else if ( current.second == null ) {
      if ( dir != current.first.opposite() ) {
        setter.accept( new DirectionPair( current.first, dir ) );
      }
    } else if ( dir == current.first.opposite() ) {
      setter.accept( new DirectionPair( dir, current.second ) );
    } else if ( dir == current.second.opposite() ) {
      setter.accept( new DirectionPair( current.first, dir ) );
    }
  }
  
  @ Override
  public void keyReleased( final int keycode ) {
    final DirectionPair current = getter.get();
    
    if ( current == null ) {
      return;
    }
    
    final Direction dir = Directions.byKeyCode( keycode );
    
    if ( current.second == null ) {
      if ( dir == current.first ) {
        setter.accept( null );
      }
    } else {
      if ( dir == current.first ) {
        setter.accept( new DirectionPair( current.second ) );
      } else if ( dir == current.second ) {
        setter.accept( new DirectionPair( current.first ) );
      }
    }
  }
}