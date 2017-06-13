package de.androbin.rpg.obj;

import static de.androbin.util.JSONUtil.*;
import java.util.*;
import org.json.simple.*;

public final class GameObjects {
  private static final Map<String, GameObjectData> DATA = new HashMap<>();
  
  public static GameObject.Builder builder = GameObject::new;
  public static GameObjectData.Builder dataBuilder = GameObjectData::new;
  
  private GameObjects() {
  }
  
  private static GameObjectData createData( final String type ) {
    final JSONObject props = (JSONObject) parseJSON( "obj/" + type + ".json" )
        .orElseGet( JSONObject::new );
    return dataBuilder.build( type, props );
  }
  
  public static GameObject create( final String name ) {
    return create( getData( name ) );
  }
  
  public static GameObject create( final GameObjectData data ) {
    return builder.build( data );
  }
  
  public static GameObjectData getData( final String name ) {
    return DATA.computeIfAbsent( name, GameObjects::createData );
  }
}