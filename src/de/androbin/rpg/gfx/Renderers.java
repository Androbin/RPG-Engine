package de.androbin.rpg.gfx;

import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.pkg.*;
import de.androbin.rpg.tile.*;

public final class Renderers {
  private static final StaticPackager<EntityRenderer<?>> ENTITY_RENDERERS;
  private static final StaticPackager<TileRenderer<?>> TILE_RENDERERS;
  
  static {
    ENTITY_RENDERERS = new StaticPackager<>( new SimpleEntityRenderer<>() );
    TILE_RENDERERS = new StaticPackager<>( new SimpleTileRenderer<>() );
  }
  
  private Renderers() {
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <E extends Entity> EntityRenderer<E> getRenderer( final E entity ) {
    return (EntityRenderer<E>) ENTITY_RENDERERS.select( entity.getData().type );
  }
  
  @ SuppressWarnings( "unchecked" )
  public static <T extends Tile> TileRenderer<T> getRenderer( final T tile ) {
    return (TileRenderer<T>) TILE_RENDERERS.select( tile.getData().type );
  }
  
  public static void registerEntity( final String serial,
      final EntityRenderer<? extends Entity> renderer ) {
    ENTITY_RENDERERS.register( Ident.parse( serial ), renderer );
  }
  
  public static void registerTile( final String serial,
      final TileRenderer<? extends Tile> renderer ) {
    TILE_RENDERERS.register( Ident.parse( serial ), renderer );
  }
}