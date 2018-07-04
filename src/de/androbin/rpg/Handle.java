package de.androbin.rpg;

import java.util.function.*;

public class Handle<I, O> {
  private I current;
  private I next;
  
  private float progress;
  
  public Consumer<I> onPrepare;
  public Consumer<I> onHandle;
  public BiConsumer<I, O> onFinish;
  
  protected O finish( final I arg ) {
    return null;
  }
  
  protected float calcSpeed( final I current ) {
    return 1f;
  }
  
  public final I getCurrent() {
    return current;
  }
  
  public final float getProgress() {
    return progress;
  }
  
  public final I getNext() {
    return next;
  }
  
  protected void handle( final I current, final boolean next ) {
  }
  
  protected I merge( final I current, final I next ) {
    return null;
  }
  
  protected boolean prepare( final I next ) {
    return true;
  }
  
  public void request( final I next ) {
    this.next = next;
  }
  
  public void reset() {
    current = null;
    next = null;
    progress = 0f;
  }
  
  protected void rewind( final float delta ) {
    progress -= delta;
  }
  
  protected I sanitize( final I next ) {
    return next;
  }
  
  protected void setCurrent( final I current ) {
    this.current = current;
  }
  
  public void update( final float delta ) {
    float left = delta;
    
    while ( left > 0f ) {
      final float diff = 0.5f - ( progress % 0.5f );
      final float speed = calcSpeed( current );
      
      if ( left * speed < diff ) {
        updateAtomic( left * speed );
        left = 0f;
      } else {
        updateAtomic( diff );
        left -= diff / speed;
      }
    }
  }
  
  protected void updateAtomic( final float delta ) {
    if ( current == null ) {
      if ( next != null ) {
        next = sanitize( next );
      }
      
      if ( next == null ) {
        reset();
      } else {
        final I val = next;
        final boolean success = prepare( next );
        
        if ( success ) {
          setCurrent( next );
          next = null;
          
          if ( onPrepare != null ) {
            onPrepare.accept( val );
          }
        } else {
          reset();
          request( val );
        }
      }
    } else {
      final float before = progress;
      progress += delta;
      final float after = progress;
      
      if ( before < 0.5f && after >= 0.5f ) {
        final I merged = merge( current, next );
        
        if ( merged != null ) {
          setCurrent( merged );
          next = null;
        }
        
        final I val = current;
        handle( current, next != null );
        
        if ( onHandle != null ) {
          onHandle.accept( val );
        }
      }
      
      if ( progress >= 1f ) {
        rewind( 1f );
        
        final I val1 = current;
        final O val2 = finish( current );
        setCurrent( null );
        
        if ( onFinish != null ) {
          onFinish.accept( val1, val2 );
        }
      }
    }
  }
}