package de.androbin.rpg.thing;

import static de.androbin.util.JSONUtil.*;
import java.io.*;
import java.util.*;
import java.util.function.*;
import org.json.simple.*;

public final class Things {
  private static final Map<String, ThingData> DATA = new HashMap<>();
  
  public static final Map<String, BiConsumer<File, Thing>> DETAILS_WRITER = new HashMap<>();
  
  public static Thing.Builder builder = Thing::new;
  public static ThingData.Builder dataBuilder = ThingData::new;
  
  public static IntFunction<File> detailsLocator;
  
  private Things() {
  }
  
  public static Thing create( final String name ) {
    return create( name, 0 );
  }
  
  public static Thing create( final String name, final int id ) {
    return create( getData( name ), id );
  }
  
  public static Thing create( final ThingData data ) {
    return create( data, 0 );
  }
  
  public static Thing create( final ThingData data, final int id ) {
    return builder.build( data, id );
  }
  
  private static ThingData createData( final String type ) {
    final JSONObject props = (JSONObject) parseJSON( "thing/" + type + ".json" )
        .orElseGet( JSONObject::new );
    return dataBuilder.build( type, props );
  }
  
  public static ThingData getData( final String name ) {
    return DATA.computeIfAbsent( name, Things::createData );
  }
  
  public static File locateDetails( final int id ) {
    return id == 0 || detailsLocator == null ? null : detailsLocator.apply( id );
  }
  
  public static void writeDetails( final Thing thing ) {
    if ( thing.id != 0 ) {
      DETAILS_WRITER.get( thing.data.name ).accept( locateDetails( thing.id ), thing );
    }
  }
}