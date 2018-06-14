package de.androbin.rpg.overlay;

import de.androbin.shell.*;
import de.androbin.shell.input.*;

public final class WaitOverlay extends AbstractShell implements Overlay {
  private final float duration;
  private float progress;
  
  public WaitOverlay( final float duration ) {
    this.duration = duration;
    
    getInputs().keyboard = KeyInput.MASKING;
  }
  
  @ Override
  public void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void update( final float delta ) {
    progress += delta;
    
    if ( progress >= duration ) {
      setRunning( false );
    }
  }
}