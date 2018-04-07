package de.androbin.rpg.tile;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.gfx.sheet.*;
import java.util.*;
import java.util.function.*;

public class TileData {
  public final Ident type;
  public final String name;
  
  public final boolean passable;
  
  public final Function<Map<String, Object>, Event> enterEvent;
  
  public final Sheet sheet;
  
  public TileData( final Ident type, final XObject props ) {
    this.type = type;
    this.name = props.get( "name" ).asString();
    
    this.passable = props.get( "passable" ).asBoolean( true );
    
    this.enterEvent = Events.parse( props.get( "enter_event" ).asString() );
    
    this.sheet = Sheets.create( this );
  }
  
  @ FunctionalInterface
  public interface Builder {
    TileData build( Ident type, XObject props );
  }
}