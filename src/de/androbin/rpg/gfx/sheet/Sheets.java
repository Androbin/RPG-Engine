package de.androbin.rpg.gfx.sheet;

import java.awt.*;
import java.awt.image.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.pkg.*;
import de.androbin.rpg.tile.*;

public final class Sheets {
  private static final Packager<Dimension> ENTITY_SIZES;
  private static final Packager<Dimension> TILE_SIZES;
  
  private static final StaticPackager<Sheet.Layout<Entity>> ENTITY_LAYOUTS;
  private static final StaticPackager<Sheet.Layout<Tile>> TILE_LAYOUTS;
  
  static {
    ENTITY_SIZES = new DynamicPackager<>( new Dimension( 1, 1 ),
        id -> Entities.PACKAGES.getData( id ).sheetSize );
    TILE_SIZES = new DynamicPackager<>( new Dimension( 1, 1 ),
        id -> Tiles.PACKAGES.getData( id ).sheetSize );
    
    ENTITY_LAYOUTS = new StaticPackager<>( entity -> new Point() );
    TILE_LAYOUTS = new StaticPackager<>( tile -> new Point() );
  }
  
  private Sheets() {
  }
  
  public static Sheet create( final EntityData data ) {
    return Sheet.create( "entity/" + data.type, ENTITY_SIZES.select( data.type.parent() ) );
  }
  
  public static Sheet create( final TileData data ) {
    return Sheet.create( "tile/" + data.type, TILE_SIZES.select( data.type.parent() ) );
  }
  
  public static BufferedImage getImage( final Entity entity ) {
    final Sheet.Layout<Entity> layout = ENTITY_LAYOUTS.select( entity.data.type.parent() );
    return entity.data.sheet.getImage( layout.locate( entity ) );
  }
  
  public static BufferedImage getImage( final Tile tile ) {
    final Sheet.Layout<Tile> layout = TILE_LAYOUTS.select( tile.data.type.parent() );
    return tile.data.sheet.getImage( layout.locate( tile ) );
  }
  
  public static void registerEntity( final String serial,
      final Sheet.Layout<Entity> layout ) {
    ENTITY_LAYOUTS.register( Ident.fromSerial( serial ), layout );
  }
  
  public static void registerTile( final String serial,
      final Sheet.Layout<Tile> layout ) {
    TILE_LAYOUTS.register( Ident.fromSerial( serial ), layout );
  }
}