package de.androbin.rpg;

import java.util.function.*;

public abstract class Handle<I, O> {
  private I current;
  private I next;
  
  private float progress;
  public float speed = 1f;
  
  public BiConsumer<I, O> callback;
  public BiConsumer<I, Boolean> requestCallback;
  
  public boolean canHandle( final I arg ) {
    return true;
  }
  
  protected abstract O doHandle( final I arg );
  
  public final I getCurrent() {
    return current;
  }
  
  public final float getModProgress() {
    return progress % 1f;
  }
  
  public final float getProgress() {
    return progress;
  }
  
  public final I getNext() {
    return next;
  }
  
  protected boolean handle( final I arg ) {
    return canHandle( arg );
  }
  
  public final boolean hasCurrent() {
    return current != null;
  }
  
  public final boolean hasNext() {
    return next != null;
  }
  
  public void makeNext( final I arg ) {
    if ( arg == null ) {
      return;
    }
    
    next = arg;
  }
  
  public void update( final float delta ) {
    if ( current == null ) {
      if ( next == null ) {
        progress = 0f;
      } else {
        final I val = next;
        final boolean success = handle( next );
        
        if ( success ) {
          current = next;
        }
        
        next = null;
        
        if ( requestCallback != null ) {
          requestCallback.accept( val, success );
        }
      }
    } else {
      progress += delta * speed;
      
      if ( progress >= 1f ) {
        final I val1 = current;
        final O val2 = doHandle( current );
        current = null;
        progress--;
        
        if ( callback != null ) {
          callback.accept( val1, val2 );
        }
      }
    }
  }
}