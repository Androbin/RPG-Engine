package de.androbin.rpg;

import de.androbin.json.*;

public final class Globals {
  private static Globals INSTANCE;
  
  public final int res;
  
  private Globals( final XObject config ) {
    res = config.get( "res" ).asInt();
  }
  
  public static Globals get() {
    return INSTANCE;
  }
  
  public static void init( final String path ) {
    init( JSONUtil.readJSON( path ).get().asObject() );
  }
  
  public static void init( final XObject config ) {
    INSTANCE = new Globals( config );
  }
}