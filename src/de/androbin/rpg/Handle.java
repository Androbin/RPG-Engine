package de.androbin.rpg;

import java.util.function.*;

public abstract class Handle<I, O> {
  private I current;
  private I requested;
  private float progress;
  
  public BiConsumer<I, O> callback;
  public BiConsumer<I, Boolean> requestCallback;
  
  public boolean canHandle( final I arg ) {
    return true;
  }
  
  protected abstract O doHandle( final RPGScreen master, final I arg );
  
  public final I getCurrent() {
    return current;
  }
  
  public final float getModProgress() {
    return progress % 1f;
  }
  
  public final float getProgress() {
    return progress;
  }
  
  public final I getRequested() {
    return requested;
  }
  
  protected boolean handle( final I arg ) {
    return canHandle( arg );
  }
  
  public final boolean hasCurrent() {
    return current != null;
  }
  
  public final boolean hasRequested() {
    return requested != null;
  }
  
  public void request( final I arg ) {
    if ( arg == null ) {
      return;
    }
    
    requested = arg;
  }
  
  public void updateStrong( final RPGScreen master ) {
    if ( current == null ) {
      if ( requested == null ) {
        progress = 0f;
      } else {
        final I val = requested;
        final boolean success = handle( requested );
        
        if ( success ) {
          current = requested;
        }
        
        requested = null;
        
        if ( requestCallback != null ) {
          requestCallback.accept( val, success );
        }
      }
    } else if ( progress >= 1f ) {
      final I val1 = current;
      final O val2 = doHandle( master, current );
      current = null;
      progress--;
      
      if ( callback != null ) {
        callback.accept( val1, val2 );
      }
    }
  }
  
  public void updateWeak( final float delta ) {
    if ( current != null ) {
      progress += delta;
    }
  }
}