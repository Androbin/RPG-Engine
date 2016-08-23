package de.androbin.rpg;

import java.awt.image.*;
import org.json.simple.*;

public class TileData
{
	public final String			name;
	public final BufferedImage	image;
	
	public final boolean		passable;
	
	public TileData( final String name, final BufferedImage image, final JSONObject extra )
	{
		this.name = name;
		this.image = image;
		
		this.passable = extra == null || (boolean) extra.get( "passable" );
	}
	
	public interface Factory
	{
		TileData build( final String name, final BufferedImage image, final JSONObject extra );
	}
}