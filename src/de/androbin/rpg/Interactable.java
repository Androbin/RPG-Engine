package de.androbin.rpg;

public interface Interactable {
  default Object onInteract( Class< ? extends Entity> type, Object ... args ) {
    return null;
  }
}