package de.androbin.rpg.overlay;

import de.androbin.shell.input.*;
import java.util.function.*;

public final class OverlayKeyInput implements KeyInput {
  private final Supplier<Overlay> overlay;
  
  public OverlayKeyInput( final Supplier<Overlay> overlay ) {
    this.overlay = overlay;
  }
  
  @ Override
  public boolean hasKeyMask() {
    return overlay.get() != null;
  }
  
  @ Override
  public void keyPressed( final int keycode ) {
    final Overlay overlay = this.overlay.get();
    
    if ( overlay == null ) {
      return;
    }
    
    overlay.getKeyInput().keyPressed( keycode );
  }
  
  @ Override
  public void keyReleased( final int keycode ) {
    final Overlay overlay = this.overlay.get();
    
    if ( overlay == null ) {
      return;
    }
    
    overlay.getKeyInput().keyReleased( keycode );
  }
  
  @ Override
  public void keyTyped( final char keychar ) {
    final Overlay overlay = this.overlay.get();
    
    if ( overlay == null ) {
      return;
    }
    
    overlay.getKeyInput().keyTyped( keychar );
  }
}