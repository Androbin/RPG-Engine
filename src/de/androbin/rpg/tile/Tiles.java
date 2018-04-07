package de.androbin.rpg.tile;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.pkg.*;
import java.util.*;

public final class Tiles {
  public static final Packages PACKAGES = new Packages( "tile" );
  private static final Map<Ident, TileData> DATA = new HashMap<>();
  
  private static final StaticPackager<Tile.Builder<TileData>> BUILDERS;
  private static final StaticPackager<TileData.Builder> DATA_BUILDERS;
  
  static {
    BUILDERS = new StaticPackager<>( Tile::new );
    DATA_BUILDERS = new StaticPackager<>( TileData::new );
  }
  
  private Tiles() {
  }
  
  public static Tile create( final Ident type ) {
    return create( getData( type ) );
  }
  
  public static Tile create( final TileData data ) {
    return data == null ? null : BUILDERS.select( data.type ).build( data );
  }
  
  private static TileData createData( final Ident type ) {
    final XObject props = JSONUtil.readJSON( "tile/" + type + ".json" ).get().asObject();
    return DATA_BUILDERS.select( type ).build( type, props );
  }
  
  public static TileData getData( final Ident type ) {
    return DATA.computeIfAbsent( type, Tiles::createData );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <D extends TileData> void register( final String serial,
      final Tile.Builder<D> builder ) {
    BUILDERS.register( Ident.fromSerial( serial ), (Tile.Builder<TileData>) builder );
  }
  
  public static void registerData( final String serial, final TileData.Builder builder ) {
    DATA_BUILDERS.register( Ident.fromSerial( serial ), builder );
  }
}