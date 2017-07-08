package de.androbin.rpg.obj;

import de.androbin.gfx.util.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import java.awt.*;
import java.awt.image.*;
import org.json.simple.*;

public class GameObjectData {
  public final String name;
  public final BufferedImage image;
  public final Dimension size;
  
  public final Event passEvent;
  
  @ SuppressWarnings( "unchecked" )
  public GameObjectData( final String name, final JSONObject props ) {
    this.name = name;
    this.image = ImageUtil.loadImage( "obj/" + name + ".png" );
    this.size = new Dimension(
        ( (Number) props.getOrDefault( "width", 1 ) ).intValue(),
        ( (Number) props.getOrDefault( "height", 1 ) ).intValue() );
    
    this.passEvent = Events.parse( (String) props.get( "pass_event" ) );
  }
  
  public interface Builder {
    GameObjectData build( String name, JSONObject props );
  }
}