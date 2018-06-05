package de.androbin.rpg;

import de.androbin.json.*;

public final class Globals {
  private static Globals instance;
  
  public final int res;
  
  private Globals( final XObject config ) {
    res = config.get( "res" ).asInt();
  }
  
  public static Globals get() {
    return instance;
  }
  
  public static void init( final String path ) {
    init( XUtil.readJSON( path ).get().asObject() );
  }
  
  public static void init( final XObject config ) {
    instance = new Globals( config );
  }
}