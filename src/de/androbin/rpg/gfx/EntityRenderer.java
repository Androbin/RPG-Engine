package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import de.androbin.gfx.util.*;
import de.androbin.rpg.*;

public class EntityRenderer extends Renderer {
  public final BufferedImage[][] images;
  public final Entity entity;
  
  public EntityRenderer( final BufferedImage[][] images, final Entity entity ) {
    this.images = images;
    this.entity = entity;
  }
  
  public static BufferedImage[][] createSheet( final String path, final int res ) {
    final int nDir = Direction.values().length;
    final BufferedImage[][] images = new BufferedImage[ nDir ][];
    
    for ( int iDir = 0; iDir < images.length; iDir++ ) {
      final String dir = Direction.values()[ iDir ].name();
      final BufferedImage image = ImageUtil.loadImage( path + "/" + dir + ".png" );
      
      final int steps = image.getWidth() / res;
      images[ iDir ] = new BufferedImage[ steps ];
      
      for ( int step = 0; step < steps; step++ ) {
        images[ iDir ][ step ] = image.getSubimage( step * res, 0, res, res );
      }
    }
    
    return images;
  }
  
  @ Override
  public Rectangle2D.Float getBounds() {
    return new Rectangle2D.Float( 0f, 0f, scale, scale );
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    final int ordinal = entity.getViewDir().ordinal();
    final float moveProgress = entity.getMoveProgress();
    
    final BufferedImage[] direction = images[ ordinal ];
    
    final Rectangle2D.Float bounds = new Rectangle2D.Float( 0f, 0f, scale, scale );
    
    drawImage( g, direction[ (int) ( moveProgress * direction.length ) ], bounds );
  }
}