package de.androbin.rpg.entity;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.Entity.*;
import de.androbin.rpg.pkg.*;
import java.util.*;

public final class Entities {
  public static final Packages PACKAGES = new Packages( "entity" );
  private static final Map<Ident, EntityData> DATA = new HashMap<>();
  
  private static final StaticPackager<Entity.Builder<?>> BUILDERS;
  private static final StaticPackager<EntityData.Builder> DATA_BUILDERS;
  
  static {
    BUILDERS = new StaticPackager<>( SimpleEntity::new );
    DATA_BUILDERS = new StaticPackager<>( EntityData::new );
  }
  
  private Entities() {
  }
  
  public static <E extends Entity> E create( final Ident type, final int id ) {
    return create( getData( type ), id );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <E extends Entity, D extends EntityData> E create(
      final D data, final int id ) {
    if ( data == null ) {
      return null;
    }
    
    final Entity.Builder<D> builder = (Builder<D>) BUILDERS.select( data.type );
    return (E) builder.build( data, id );
  }
  
  private static EntityData createData( final Ident type ) {
    final XObject props = XUtil.readJSON( "entity/" + type + ".json" ).get().asObject();
    return DATA_BUILDERS.select( type ).build( type, props );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <D extends EntityData> D getData( final Ident type ) {
    return (D) DATA.computeIfAbsent( type, Entities::createData );
  }
  
  public static void invalidate() {
    PACKAGES.invalidate();
    DATA.clear();
  }
  
  public static <D extends EntityData> void register( final String serial,
      final Entity.Builder<D> builder ) {
    BUILDERS.register( Ident.parse( serial ), builder );
  }
  
  public static void registerData( final String serial, final EntityData.Builder builder ) {
    DATA_BUILDERS.register( Ident.parse( serial ), builder );
  }
}