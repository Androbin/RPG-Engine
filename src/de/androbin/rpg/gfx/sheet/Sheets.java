package de.androbin.rpg.gfx.sheet;

import java.awt.*;
import java.awt.image.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.sheet.Sheet.*;
import de.androbin.rpg.pkg.*;
import de.androbin.rpg.tile.*;

public final class Sheets {
  private static final Packager<Dimension> ENTITY_SIZES;
  private static final Packager<Dimension> TILE_SIZES;
  
  private static final StaticPackager<Sheet.Layout<? extends Entity>> ENTITY_LAYOUTS;
  private static final StaticPackager<Sheet.Layout<? extends Tile>> TILE_LAYOUTS;
  
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
  
  public static Sheet createEntity( final Ident type ) {
    return Sheet.create( "entity/" + type, ENTITY_SIZES.select( type.parent() ) );
  }
  
  public static Sheet createTile( final Ident type ) {
    return Sheet.create( "tile/" + type, TILE_SIZES.select( type.parent() ) );
  }
  
  public static <E extends Entity> BufferedImage getImage( final E entity, final float scale ) {
    final EntityData data = entity.data;
    final Sheet sheet = data.sheet;
    sheet.setScale( scale );
    
    @ SuppressWarnings( "unchecked" )
    final Sheet.Layout<? super E> layout = (Layout<? super E>) ENTITY_LAYOUTS
        .select( data.type.parent() );
    return sheet.getImage( layout.locate( entity ) );
  }
  
  public static <T extends Tile> BufferedImage getImage( final T tile, final float scale ) {
    final TileData data = tile.data;
    final Sheet sheet = data.sheet;
    sheet.setScale( scale );
    
    @ SuppressWarnings( "unchecked" )
    final Sheet.Layout<? super T> layout = (Layout<? super T>) TILE_LAYOUTS
        .select( data.type.parent() );
    return sheet.getImage( layout.locate( tile ) );
  }
  
  public static void registerEntity( final String serial,
      final Sheet.Layout<? extends Entity> layout ) {
    ENTITY_LAYOUTS.register( Ident.fromSerial( serial ), layout );
  }
  
  public static void registerTile( final String serial,
      final Sheet.Layout<Tile> layout ) {
    TILE_LAYOUTS.register( Ident.fromSerial( serial ), layout );
  }
}