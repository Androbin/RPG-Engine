package de.androbin.rpg.gfx.sheet;

import java.awt.*;
import java.awt.image.*;
import de.androbin.gfx.util.*;
import de.androbin.rpg.*;

public final class Sheet {
  public final Dimension size;
  
  private final BufferedImage[][] raw;
  private final BufferedImage[][] scaled;
  
  public final Dimension rawSize;
  private float scale;
  
  private Sheet( final Dimension size, final BufferedImage[][] images, final Dimension rawSize ) {
    this.size = size;
    
    raw = images;
    scaled = new BufferedImage[ images.length ][ images[ 0 ].length ];
    
    this.rawSize = rawSize;
    setScale( Globals.get().res );
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
    
    return new Sheet( size, images, new Dimension( width, height ) );
  }
  
  public BufferedImage getImage( final Point pos ) {
    return scaled[ pos.y ][ pos.x ];
  }
  
  public void setScale( final float scale ) {
    if ( this.scale == scale ) {
      return;
    }
    
    final float scalar = scale / Globals.get().res;
    final Dimension size = new Dimension(
        Math.round( rawSize.width * scalar ),
        Math.round( rawSize.height * scalar ) );
    
    this.scale = scale;
    
    for ( int y = 0; y < raw.length; y++ ) {
      for ( int x = 0; x < raw[ y ].length; x++ ) {
        scaled[ y ][ x ] = ImageUtil.scaleImage( raw[ y ][ x ], size );
      }
    }
  }
}