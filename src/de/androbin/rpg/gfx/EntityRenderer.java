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
  
  public static BufferedImage[][] createSheet( final String path, final int res ) {
    final Direction[] directions = Direction.values();
    final BufferedImage[][] animation = new BufferedImage[ directions.length ][];
    
    for ( int i = 0; i < animation.length; i++ ) {
      final String dir = CaseUtil.toProperCase( directions[ i ].name() );
      final BufferedImage image = ImageUtil.loadImage( path + "/" + dir + ".png" );
      
      animation[ i ] = new BufferedImage[ image.getWidth() / res ];
      
      for ( int j = 0; j < animation[ i ].length; j++ ) {
        animation[ i ][ j ] = image.getSubimage( j * res, 0, res, res );
      }
    }
    
    return animation;
  }
  
  @ Override
  public Rectangle2D.Float getBounds() {
    return new Rectangle2D.Float( 0f, 0f, scale, scale );
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    final int i = entity.viewDir.ordinal();
    final int j = (int) ( entity.getMoveProgress() * animation[ i ].length );
    drawImage( g, animation[ i ][ j ], 0f, 0f, scale, scale );
  }
}