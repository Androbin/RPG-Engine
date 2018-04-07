package de.androbin.rpg.entity;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import de.androbin.rpg.gfx.sheet.*;
import java.awt.*;
import java.util.*;
import java.util.function.*;

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
  
  public EntityData( final Ident type, final XObject props ) {
    this.type = type;
    this.name = props.get( "name" ).asString();
    
    this.solid = props.get( "solid" ).asBoolean( true );
    this.size = new Dimension(
        props.get( "width" ).asInt( 1 ),
        props.get( "height" ).asInt( 1 ) );
    this.orientation = Directions.valueOf( props.get( "orientation" ).asString() );
    
    this.enterEvent = Events.parse( props.get( "enter_event" ).asString() );
    
    this.sheet = Sheets.create( this );
    this.sheetDX = props.get( "dx" ).asInt( 0 );
    this.sheetDY = props.get( "dy" ).asInt( 0 );
  }
  
  @ FunctionalInterface
  public interface Builder {
    EntityData build( Ident type, XObject props );
  }
}