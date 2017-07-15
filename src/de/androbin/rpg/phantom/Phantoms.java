package de.androbin.rpg.phantom;

import static de.androbin.util.JSONUtil.*;
import java.awt.*;
import java.util.*;
import org.json.simple.*;

public final class Phantoms {
  private static final Map<String, PhantomData> DATA = new HashMap<>();
  
  public static Phantom.Builder builder = Phantom::new;
  public static PhantomData.Builder dataBuilder = PhantomData::new;
  
  private Phantoms() {
  }
  
  public static Phantom create( final String name, final Point pos ) {
    return create( getData( name ), pos );
  }
  
  public static Phantom create( final PhantomData data, final Point pos ) {
    return builder.build( data, pos );
  }
  
  private static PhantomData createData( final String type ) {
    final JSONObject props = (JSONObject) parseJSON( "phantom/" + type + ".json" )
        .orElseGet( JSONObject::new );
    return dataBuilder.build( type, props );
  }
  
  public static PhantomData getData( final String name ) {
    return DATA.computeIfAbsent( name, Phantoms::createData );
  }
}