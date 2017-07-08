package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import de.androbin.gfx.util.*;
import de.androbin.rpg.*;
import de.androbin.util.txt.*;

public class EntityRenderer extends Renderer {
  protected final Entity entity;
  protected final BufferedImage[][] animation;
  
  public EntityRenderer( final Entity entity, final BufferedImage[][] animation ) {
    this.entity = entity;
    this.animation = animation;
  }
  
  public static BufferedImage[][] createSheet( final String path ) {
    final Direction[] directions = Direction.values();
    final BufferedImage[][] animation = new BufferedImage[ directions.length ][];
    
    for ( int i = 0; i < animation.length; i++ ) {
      final String dir = CaseUtil.toProperCase( directions[ i ].name() );
      final BufferedImage image = ImageUtil.loadImage( path + "/" + dir + ".png" );
      
      final int ratio = Math.round( (float) image.getWidth() / image.getHeight() );
      
      final int width = image.getWidth() / ratio;
      final int height = image.getHeight();
      
      animation[ i ] = new BufferedImage[ ratio ];
      
      for ( int j = 0; j < animation[ i ].length; j++ ) {
        animation[ i ][ j ] = image.getSubimage( j * width, 0, width, height );
      }
    }
    
    return animation;
  }
  
  @ Override
  public Rectangle2D.Float getBounds() {
    final Point2D.Float pos = entity.getFloatPos();
    final Dimension size = entity.size;
    
    final int i = entity.viewDir.ordinal();
    final int j = (int) ( entity.move.getProgress() * animation[ i ].length );
    
    final BufferedImage image = animation[ i ][ j ];
    
    final float res = image.getWidth() / size.width;
    final float y = pos.y + size.height - image.getHeight() / res;
    final float height = image.getHeight() / res;
    
    return new Rectangle2D.Float( pos.x, y, size.width, height );
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    final Rectangle2D.Float bounds = getBounds();
    
    bounds.x *= scale;
    bounds.y *= scale;
    bounds.width *= scale;
    bounds.height *= scale;
    
    final int i = entity.viewDir.ordinal();
    final int j = (int) ( entity.move.getProgress() * animation[ i ].length );
    drawImage( g, animation[ i ][ j ], bounds );
  }
}