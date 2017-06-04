package de.androbin.rpg;

public interface Interactable
{
	default Object onInteract( final Class< ? extends Entity> type, final Object ... args )
	{
		return null;
	}
}