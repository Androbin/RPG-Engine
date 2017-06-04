package de.androbin.rpg.tile;

import java.awt.image.*;
import org.json.simple.*;
import de.androbin.rpg.event.*;

public class TileData
{
	public final String		   name;
	public final BufferedImage image;
							   
	public final Event		   passEvent;
							   
	public TileData( final String name, final BufferedImage image, final JSONObject props )
	{
		this.name = name;
		this.image = image;
		
		this.passEvent = props == null ? null : Events.parse( (String) props.get( "pass_event" ) );
	}
	
	public interface Builder
	{
		TileData build( final String name, final BufferedImage image, final JSONObject props );
	}
}