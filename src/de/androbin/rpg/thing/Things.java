package de.androbin.rpg.thing;

import static de.androbin.util.JSONUtil.*;
import java.awt.*;
import java.util.*;
import org.json.simple.*;

public final class Things {
  private static final Map<String, ThingData> DATA = new HashMap<>();
  
  public static Thing.Builder builder = Thing::new;
  public static ThingData.Builder dataBuilder = ThingData::new;
  
  private Things() {
  }
  
  public static Thing create( final String name, final Point pos ) {
    return create( getData( name ), pos );
  }
  
  public static Thing create( final ThingData data, final Point pos ) {
    return builder.build( data, pos );
  }
  
  private static ThingData createData( final String type ) {
    final JSONObject props = (JSONObject) parseJSON( "thing/" + type + ".json" )
        .orElseGet( JSONObject::new );
    return dataBuilder.build( type, props );
  }
  
  public static ThingData getData( final String name ) {
    return DATA.computeIfAbsent( name, Things::createData );
  }
}