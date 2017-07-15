package de.androbin.rpg.phantom;

import de.androbin.gfx.util.*;
import de.androbin.rpg.event.*;
import de.androbin.rpg.event.Event;
import java.awt.*;
import java.awt.image.*;
import org.json.simple.*;

public class PhantomData {
  public final String name;
  public final BufferedImage image;
  public final Dimension size;
  
  public final Event event;
  
  @ SuppressWarnings( "unchecked" )
  public PhantomData( final String name, final JSONObject props ) {
    this.name = name;
    this.image = ImageUtil.loadImage( "phantom/" + name + ".png" );
    this.size = new Dimension(
        ( (Number) props.getOrDefault( "width", 1 ) ).intValue(),
        ( (Number) props.getOrDefault( "height", 1 ) ).intValue() );
    
    this.event = Events.parse( (String) props.get( "event" ) );
  }
  
  public interface Builder {
    PhantomData build( String name, JSONObject props );
  }
}