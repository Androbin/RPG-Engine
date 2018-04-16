package de.androbin.rpg;

import de.androbin.json.*;

public class Data {
  public final Ident type;
  public final String name;
  
  public Data( final Ident type, final XObject props ) {
    this.type = type;
    this.name = props.get( "name" ).asString();
  }
}