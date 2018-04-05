package de.androbin.rpg.gfx.sheet;

import java.awt.*;
import java.awt.image.*;
import de.androbin.gfx.util.*;

public final class Sheet {
  private final BufferedImage[][] images;
  
  private Sheet( final BufferedImage[][] images ) {
    this.images = images;
  }
  
  public static Sheet create( final String path, final Dimension size ) {
    final BufferedImage[][] images = new BufferedImage[ size.height ][ size.width ];
    
    final BufferedImage image = ImageUtil.loadImage( path + ".png" );
    
    final int width = image.getWidth() / size.width;
    final int height = image.getHeight() / size.height;
    
    for ( int y = 0; y < size.height; y++ ) {
      for ( int x = 0; x < size.width; x++ ) {
        images[ y ][ x ] = image.getSubimage( x * width, y * height, width, height );
      }
    }
    
    return new Sheet( images );
  }
  
  public BufferedImage getImage( final Point pos ) {
    return images[ pos.y ][ pos.x ];
  }
  
  @ FunctionalInterface
  public interface Layout<O> {
    Point locate( O object );
  }
}