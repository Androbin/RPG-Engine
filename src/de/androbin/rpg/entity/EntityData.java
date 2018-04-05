package de.androbin.rpg.entity;

import java.awt.*;
import java.util.*;
import java.util.function.*;
import org.json.simple.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.gfx.sheet.*;
import de.androbin.util.*;

public class EntityData {
  public final Ident type;
  public final String name;
  
  public final boolean solid;
  public final Dimension size;
  public final Direction orientation;
  
  public final Function<Map<String, Object>, Event> enterEvent;
  
  public final Sheet sheet;
  public final int sheetDX;
  public final int sheetDY;
  
  @ SuppressWarnings( "unchecked" )
  public EntityData( final Ident type, final JSONObject props ) {
    this.type = type;
    this.name = (String) props.get( "name" );
    
    this.solid = (boolean) props.getOrDefault( "solid", true );
    this.size = new Dimension(
        JSONUtil.toInt( props.getOrDefault( "width", 1 ) ),
        JSONUtil.toInt( props.getOrDefault( "height", 1 ) ) );
    this.orientation = Directions.valueOf( (String) props.get( "orientation" ) );
    
    this.enterEvent = Events.parse( (String) props.get( "enter_event" ) );
    
    this.sheet = Sheets.create( this );
    this.sheetDX = ( (Number) props.getOrDefault( "dx", 0 ) ).intValue();
    this.sheetDY = ( (Number) props.getOrDefault( "dy", 0 ) ).intValue();
  }
  
  @ FunctionalInterface
  public interface Builder {
    EntityData build( Ident type, JSONObject props );
  }
}