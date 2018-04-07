package de.androbin.rpg.entity;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.pkg.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

public final class Entities {
  public static final Packages PACKAGES = new Packages( "entity" );
  private static final Map<Ident, EntityData> DATA = new HashMap<>();
  
  public static final Map<Ident, BiConsumer<File, Entity>> DETAILS_WRITER = new HashMap<>();
  
  private static final StaticPackager<Entity.Builder<EntityData>> BUILDERS;
  private static final StaticPackager<EntityData.Builder> DATA_BUILDERS;
  
  public static IntFunction<File> detailsLocator;
  
  static {
    BUILDERS = new StaticPackager<>( Entity::new );
    DATA_BUILDERS = new StaticPackager<>( EntityData::new );
  }
  
  private Entities() {
  }
  
  public static Entity create( final Ident type, final int id ) {
    return create( getData( type ), id );
  }
  
  public static Entity create( final EntityData data, final int id ) {
    return data == null ? null : BUILDERS.select( data.type ).build( data, id );
  }
  
  private static EntityData createData( final Ident type ) {
    final XObject props = JSONUtil.readJSON( "entity/" + type + ".json" ).get().asObject();
    return DATA_BUILDERS.select( type ).build( type, props );
  }
  
  public static EntityData getData( final Ident type ) {
    return DATA.computeIfAbsent( type, Entities::createData );
  }
  
  public static File locateDetails( final int id ) {
    return id == 0 || detailsLocator == null ? null : detailsLocator.apply( id );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <D extends EntityData> void register( final String serial,
      final Entity.Builder<D> builder ) {
    BUILDERS.register( Ident.fromSerial( serial ), (Entity.Builder<EntityData>) builder );
  }
  
  public static void registerData( final String serial, final EntityData.Builder builder ) {
    DATA_BUILDERS.register( Ident.fromSerial( serial ), builder );
  }
  
  public static void writeDetails( final Entity entity ) {
    if ( entity.id != 0 ) {
      DETAILS_WRITER.get( entity.data.type ).accept( locateDetails( entity.id ), entity );
    }
  }
}