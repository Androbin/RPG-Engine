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
  
  protected boolean prepare( final I arg ) {
    return true;
  }
  
  protected void rewind( final float delta ) {
    progress -= delta;
  }
  
  public void update( final float delta ) {
    if ( current == null ) {
      if ( next == null ) {
        progress = 0f;
      } else {
        final I val = next;
        final boolean success = prepare( next );
        
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
        final O val2 = finish( current );
        current = null;
        rewind( 1f );
        
        if ( callback != null ) {
          callback.accept( val1, val2 );
        }
      }
    }
  }
}