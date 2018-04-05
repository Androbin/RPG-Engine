package de.androbin.rpg.tile;

import java.util.*;
import java.util.function.*;
import org.json.simple.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.sheet.*;

public class TileData {
  public final Ident type;
  public final String name;
  
  public final boolean passable;
  
  public final Function<Map<String, Object>, Event> enterEvent;
  
  public final Sheet sheet;
  
  @ SuppressWarnings( "unchecked" )
  public TileData( final Ident type, final JSONObject props ) {
    this.type = type;
    this.name = (String) props.get( "name" );
    
    this.passable = (boolean) props.getOrDefault( "passable", true );
    
    this.enterEvent = Events.parse( (String) props.get( "enter_event" ) );
    
    this.sheet = Sheets.create( this );
  }
  
  @ FunctionalInterface
  public interface Builder {
    TileData build( Ident type, JSONObject props );
  }
}