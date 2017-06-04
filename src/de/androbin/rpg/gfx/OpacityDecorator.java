package de.androbin.rpg.gfx;

import java.awt.*;
import de.androbin.rpg.gfx.Renderer.*;

public final class OpacityDecorator extends Decorator {
  private float opacity;
  
  public OpacityDecorator() {
    setOpacity( 1f );
  }
  
  public OpacityDecorator( final float opacity ) {
    setOpacity( opacity );
  }
  
  @ Override
  public Graphics2D preDecorate( final Graphics2D g ) {
    g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, opacity ) );
    return g;
  }
  
  public final void setOpacity( float opacity ) {
    this.opacity = opacity;
  }
}