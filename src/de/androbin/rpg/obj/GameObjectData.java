package de.androbin.rpg.obj;

import java.awt.*;
import java.awt.image.*;
import org.json.simple.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;

public class GameObjectData {
  public final String name;
  public final BufferedImage image;
  public final Dimension size;
  
  public final Event passEvent;
  
  public GameObjectData( final String name, final BufferedImage image, final JSONObject props ) {
    this.name = name;
    this.image = image;
    this.size = new Dimension(
        ( (Number) props.get( "width" ) ).intValue(),
        ( (Number) props.get( "height" ) ).intValue() );
    this.passEvent = Events.parse( (String) props.get( "pass_event" ) );
  }
  
  public Dimension getSize() {
    return size;
  }
  
  public interface Builder {
    GameObjectData build( String name, BufferedImage image, JSONObject props );
  }
}