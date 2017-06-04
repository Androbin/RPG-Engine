package de.androbin.rpg.obj;

import static de.androbin.util.JSONUtil.*;
import de.androbin.gfx.util.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import org.json.simple.*;

public final class GameObjects
{
	private static final Map<String, GameObjectData>	 DATA		 = new HashMap<>();
																	 
	public static GameObject.Builder					 builder	 = GameObject::new;
	public static GameObjectData.Builder				 dataBuilder = GameObjectData::new;
																	 
	private GameObjects()
	{
	}
	
	private static GameObjectData createData( final String type )
	{
		final JSONObject props = (JSONObject) parseJSON( "gameobject/" + type + ".json" ).orElseGet( JSONObject::new );
		final BufferedImage image = ImageUtil.loadImage( "gameobject/" + type + ".png" );
		
		return dataBuilder.build( type, image, props );
	}
	
	public static GameObject create( final String name, final Point pos )
	{
		return create( getData( name ), pos );
	}
	
	public static GameObject create( final GameObjectData data, final Point pos )
	{
		return builder.build( data, pos );
	}
	
	public static GameObjectData getData( final String name )
	{
		if ( DATA.containsKey( name ) )
		{
			return DATA.get( name );
		}
		else
		{
			final GameObjectData data = createData( name );
			DATA.put( name, data );
			return data;
		}
	}
}