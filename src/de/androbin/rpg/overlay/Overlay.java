package de.androbin.rpg.overlay;

import de.androbin.shell.input.*;
import java.awt.*;

public interface Overlay {
  KeyInput getKeyInput();
  
  boolean isDone();
  
  void onResized( int width, int height );
  
  void render( Graphics2D g );
  
  void update( float delta );
}