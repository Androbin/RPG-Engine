package de.androbin.rpg;

import static de.androbin.util.JSONUtil.*;
import de.androbin.gfx.util.*;
import java.awt.image.*;
import java.util.*;
import org.json.simple.*;

public final class Tiles
{
	private static final Map<String, TileData>	TILE_DATA		= new HashMap<>();
	
	public static Tile.Factory					tileFactory		= ( x, y, data ) -> new Tile( x, y, data );
	public static TileData.Factory				tileDataFactory	= ( name, image, extra ) -> new TileData( name, image, extra );
	
	private Tiles()
	{
	}
	
	private static TileData createData( final String type )
	{
		final BufferedImage image = ImageUtil.loadImage( "tile/" + type + ".png" );
		final JSONObject extra = (JSONObject) parseJSON( "tile/" + type + ".json" );
		
		return tileDataFactory.build( type, image, extra );
	}
	
	public static Tile getTile( final String name, final int x, final int y )
	{
		final TileData data;
		
		if ( TILE_DATA.containsKey( name ) )
		{
			data = TILE_DATA.get( name );
		}
		else
		{
			data = createData( name );
			TILE_DATA.put( name, data );
		}
		
		return tileFactory.build( x, y, data );
	}
}