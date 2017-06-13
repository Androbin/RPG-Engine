package de.androbin.rpg.gfx;

import de.androbin.rpg.gfx.Renderer.*;
import java.awt.*;
import java.awt.geom.*;

public final class OpacityDecorator extends Decorator {
  private float opacity;
  
  public OpacityDecorator() {
    setOpacity( 1f );
  }
  
  public OpacityDecorator( final float opacity ) {
    setOpacity( opacity );
  }
  
  @ Override
  public Rectangle2D.Float getDecoratorBounds() {
    return null;
  }
  
  @ Override
  public Graphics2D preDecorate( final Graphics2D g ) {
    g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, opacity ) );
    return g;
  }
  
  @ Override
  public void postDecorate( final Graphics2D g ) {
  }
  
  public void setOpacity( final float opacity ) {
    this.opacity = opacity;
  }
}