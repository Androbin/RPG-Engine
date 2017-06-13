package de.androbin.rpg.tile;

import static de.androbin.util.JSONUtil.*;
import de.androbin.gfx.util.*;
import java.awt.image.*;
import java.util.*;
import org.json.simple.*;

public final class Tiles {
  private static final Map<String, TileData> DATA = new HashMap<>();
  
  public static Tile.Builder builder = Tile::new;
  public static TileData.Builder dataBuilder = TileData::new;
  
  private Tiles() {
  }
  
  private static TileData createData( final String type ) {
    final JSONObject props = (JSONObject) parseJSON( "tile/" + type + ".json" )
        .orElseGet( JSONObject::new );
    final BufferedImage image = ImageUtil.loadImage( "tile/" + type + ".png" );
    
    return dataBuilder.build( type, image, props );
  }
  
  public static Tile create( final String name ) {
    return create( getData( name ) );
  }
  
  public static Tile create( final TileData data ) {
    return builder.build( data );
  }
  
  public static TileData getData( final String name ) {
    return DATA.computeIfAbsent( name, Tiles::createData );
  }
}