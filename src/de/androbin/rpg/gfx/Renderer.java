package de.androbin.rpg.gfx;

import java.awt.*;
import java.awt.geom.*;
import de.androbin.gfx.*;

public abstract class Renderer implements Renderable {
  protected float scale = 1f;
  
  public abstract Rectangle2D.Float getBounds();
  
  public void setScale( final float scale ) {
    this.scale = scale;
  }
  
  public static abstract class Decorator extends Renderer {
    private Renderer renderer;
    
    public Decorator() {
    }
    
    public Decorator( final Renderer renderer ) {
      setRenderer( renderer );
    }
    
    public Rectangle2D.Float getDecoratorBounds() {
      return null;
    }
    
    @ Override
    public final Rectangle2D.Float getBounds() {
      final Rectangle2D.Float bounds0 = renderer.getBounds();
      final Rectangle2D.Float bounds1 = getDecoratorBounds();
      return bounds1 == null ? bounds0 : (Rectangle2D.Float) bounds0.createUnion( bounds1 );
    }
    
    public Graphics2D preDecorate( final Graphics2D g ) {
      return g;
    }
    
    @ Override
    public final void render( final Graphics2D g ) {
      final Graphics2D h = preDecorate( g );
      renderer.render( h );
      postDecorate( g );
    }
    
    public final void setRenderer( final Renderer renderer ) {
      this.renderer = renderer;
    }
    
    @ Override
    public void setScale( final float scale ) {
      super.setScale( scale );
      renderer.setScale( scale );
    }
    
    public void postDecorate( final Graphics2D g ) {
    }
  }
}