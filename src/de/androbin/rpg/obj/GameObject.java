package de.androbin.rpg.obj;

import de.androbin.rpg.*;
import de.androbin.rpg.gfx.*;
import java.awt.*;
import java.awt.geom.*;

public class GameObject implements Sprite {
  protected transient Renderer renderer;
  
  public final GameObjectData data;
  public final Point pos;
  
  public GameObject( final GameObjectData data, final Point pos ) {
    this.data = data;
    this.pos = pos;
    
    renderer = new GameObjectRenderer( this );
  }
  
  @ Override
  public Rectangle getBounds() {
    return new Rectangle( pos, data.size );
  }
  
  @ Override
  public Rectangle2D.Float getViewBounds() {
    return renderer == null ? null : renderer.getBounds();
  }
  
  @ Override
  public void render( final Graphics2D g, final float scale ) {
    if ( renderer == null ) {
      return;
    }
    
    renderer.setScale( scale );
    renderer.render( g );
  }
  
  public interface Builder {
    GameObject build( GameObjectData data, final Point pos );
  }
}