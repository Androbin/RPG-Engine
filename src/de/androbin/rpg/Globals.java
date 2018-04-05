package de.androbin.rpg;

import org.json.simple.*;
import de.androbin.util.*;

public final class Globals {
  private static Globals INSTANCE;
  
  public final int res;
  
  private Globals( final JSONObject config ) {
    res = ( (Number) config.get( "res" ) ).intValue();
  }
  
  public static Globals get() {
    return INSTANCE;
  }
  
  public static void init( final String path ) {
    init( (JSONObject) JSONUtil.parseJSON( path ).get() );
  }
  
  public static void init( final JSONObject config ) {
    INSTANCE = new Globals( config );
  }
}