package de.androbin.rpg;

public interface Interaction
{
	default Object onInteract( final Class< ? extends Entity> type, final Object ... args )
	{
		return null;
	}
}