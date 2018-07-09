package de.androbin.rpg.tile;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.pkg.*;
import java.util.*;

public final class Tiles {
  public static final Packages PACKAGES = new Packages( "tile" );
  private static final Map<Ident, TileData> DATA = new HashMap<>();
  
  private static final StaticPackager<Tile.Builder<?>> BUILDERS;
  private static final StaticPackager<TileData.Builder> DATA_BUILDERS;
  
  static {
    BUILDERS = new StaticPackager<>( SimpleTile::new );
    DATA_BUILDERS = new StaticPackager<>( TileData::new );
  }
  
  private Tiles() {
  }
  
  public static <T extends Tile> T create( final Ident type ) {
    return create( getData( type ) );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <T extends Tile> T create( final TileData data ) {
    return data == null ? null : (T) BUILDERS.select( data.type ).build( data );
  }
  
  private static TileData createData( final Ident type ) {
    final XObject props = XUtil.readJSON( "tile/" + type + ".json" ).get().asObject();
    return DATA_BUILDERS.select( type ).build( type, props );
  }
  
  public static TileData getData( final Ident type ) {
    return DATA.computeIfAbsent( type, Tiles::createData );
  }
  
  public static void invalidate() {
    PACKAGES.invalidate();
    DATA.clear();
  }
  
  public static <D extends TileData> void register( final String serial,
      final Tile.Builder<D> builder ) {
    BUILDERS.register( Ident.parse( serial ), builder );
  }
  
  public static void registerData( final String serial, final TileData.Builder builder ) {
    DATA_BUILDERS.register( Ident.parse( serial ), builder );
  }
}