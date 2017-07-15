package de.androbin.rpg.tile;

import java.awt.image.*;
import org.json.simple.*;
import de.androbin.gfx.util.*;
import de.androbin.rpg.event.*;

public class TileData {
  public final String name;
  public final BufferedImage image;
  
  public final Event event;
  
  public TileData( final String name, final JSONObject data ) {
    this.name = name;
    this.image = ImageUtil.loadImage( "tile/" + name + ".png" );
    
    this.event = Events.parse( (String) data.get( "event" ) );
  }
  
  public interface Builder {
    TileData build( String name, JSONObject data );
  }
}