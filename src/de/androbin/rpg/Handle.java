package de.androbin.rpg;

import java.util.function.*;

public class Handle<I, O> {
  protected I current;
  protected I next;
  
  private float progress;
  public float speed = 1f;
  
  public BiConsumer<I, O> callback;
  public BiConsumer<I, Boolean> requestCallback;
  
  protected O finish( final I arg ) {
    return null;
  }
  
  public final I getCurrent() {
    return current;
  }
  
  public final float getModProgress() {
    return progress % 1f;
  }
  
  public final float getProgress() {
    return progress;
  }
  
  public final boolean hasCurrent() {
    return current != null;
  }
  
  protected void handle( final I arg ) {
    final I merged = merge( next );
    
    if ( merged != null ) {
      setCurrent( merged );
      next = null;
    }
  }
  
  protected I merge( final I arg ) {
    return null;
  }
  
  protected boolean prepare( final I arg ) {
    return true;
  }
  
  public void request( final I arg ) {
    next = arg;
  }
  
  protected void rewind( final float delta ) {
    progress -= delta;
  }
  
  protected I sanitize( final I arg ) {
    return arg;
  }
  
  protected void setCurrent( final I arg ) {
    current = arg;
  }
  
  public void update( final float delta ) {
    final float total = delta * speed;
    final int steps = (int) ( total / 0.5f ) + 1;
    final float step = total / steps;
    
    for ( int i = 0; i < steps; i++ ) {
      updateAtomic( step );
    }
  }
  
  public void updateAtomic( final float delta ) {
    if ( current == null ) {
      if ( next != null ) {
        next = sanitize( next );
      }
      
      if ( next == null ) {
        progress = 0f;
      } else {
        final I val = next;
        final boolean success = prepare( next );
        
        if ( success ) {
          setCurrent( next );
        }
        
        next = null;
        
        if ( requestCallback != null ) {
          requestCallback.accept( val, success );
        }
      }
    } else {
      final float before = progress;
      progress += delta;
      final float after = progress;
      
      if ( before < 0.5f && after >= 0.5f ) {
        handle( current );
      }
      
      if ( progress >= 1f ) {
        final I val1 = current;
        final O val2 = finish( current );
        setCurrent( null );
        rewind( 1f );
        
        if ( callback != null ) {
          callback.accept( val1, val2 );
        }
      }
    }
  }
}