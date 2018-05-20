package de.androbin.rpg.gfx;

import static de.androbin.math.util.floats.FloatMathUtil.*;
import java.awt.geom.*;
import java.util.function.*;

public final class Camera {
  private Supplier<Point2D.Float> currentFocus;
  private Supplier<Point2D.Float> nextFocus;
  
  private float progress;
  private float speed;
  
  private float breakpoint() {
    final Point2D.Float current = getCurrentFocus();
    final Point2D.Float next = getNextFocus();
    
    final float dx = next.x - current.x;
    final float dy = next.y - current.y;
    
    return dx / ( dx + dy );
  }
  
  public float calcTranslationX( final float width, final float worldWidth, final float scale ) {
    if ( worldWidth <= width ) {
      return 0.5f * ( width - worldWidth );
    }
    
    final Point2D.Float current = getCurrentFocus();
    
    if ( current == null ) {
      return 0f;
    }
    
    final Point2D.Float next = getNextFocus();
    final float ix = next == null ? current.x
        : progress >= breakpoint() ? next.x
            : inter( current.x, progress / breakpoint(), next.x );
    return bound( width - worldWidth, 0.5f * width - scale * ix, 0f );
  }
  
  public float calcTranslationY( final float height, final float worldHeight, final float scale ) {
    if ( worldHeight <= height ) {
      return 0.5f * ( height - worldHeight );
    }
    
    final Point2D.Float current = getCurrentFocus();
    
    if ( current == null ) {
      return 0f;
    }
    
    final Point2D.Float next = getNextFocus();
    final float iy = next == null ? current.y
        : progress <= breakpoint() ? next.y
            : inter( current.y, ( progress - breakpoint() ) / ( 1f - breakpoint() ), next.y );
    return bound( height - worldHeight, 0.5f * height - scale * iy, 0f );
  }
  
  private Point2D.Float getCurrentFocus() {
    return currentFocus == null ? null : currentFocus.get();
  }
  
  private Point2D.Float getNextFocus() {
    return nextFocus == null ? null : nextFocus.get();
  }
  
  public void moveFocus( final Supplier<Point2D.Float> nextFocus, final float speed ) {
    this.speed = speed;
    this.progress = 0f;
    
    final Point2D.Float current = currentFocus.get();
    this.currentFocus = () -> current;
    
    this.nextFocus = nextFocus;
  }
  
  public void setFocus( final Supplier<Point2D.Float> focus ) {
    this.progress = 0f;
    this.currentFocus = focus;
  }
  
  public void update( final float delta ) {
    if ( nextFocus == null ) {
      return;
    }
    
    progress += delta * speed;
    
    if ( progress >= 1f ) {
      progress = 0f;
      
      currentFocus = nextFocus;
      nextFocus = null;
    }
  }
}