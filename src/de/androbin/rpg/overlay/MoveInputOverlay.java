package de.androbin.rpg.overlay;

import de.androbin.rpg.*;
import de.androbin.rpg.dir.*;
import de.androbin.rpg.entity.*;
import de.androbin.shell.*;
import java.util.function.*;

public final class MoveInputOverlay extends AbstractShell implements Overlay {
  private final Supplier<Agent> player;
  private DirectionPair requestDir;
  
  public MoveInputOverlay( final Supplier<Agent> player ) {
    this.player = player;
    
    final Supplier<DirectionPair> getter = () -> requestDir;
    final Consumer<DirectionPair> setter = dir -> requestDir = dir;
    
    getInputs().keyboard = new MoveKeyInput( getter, setter );
  }
  
  @ Override
  protected void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void update( final float delta ) {
    final Agent player = this.player.get();
    
    if ( player != null ) {
      MoveKeyInput.applyRequest( player.move, requestDir );
    }
  }
}