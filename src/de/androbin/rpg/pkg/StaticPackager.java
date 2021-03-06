package de.androbin.rpg.pkg;

import de.androbin.rpg.*;
import java.util.*;

public final class StaticPackager<T> extends AbstractPackager<T> {
  private final Map<Ident, T> map;
  private final T defaultValue;
  
  public StaticPackager() {
    this( null );
  }
  
  public StaticPackager( final T defaultValue ) {
    map = new HashMap<>();
    this.defaultValue = defaultValue;
  }
  
  @ Override
  protected T get( final Ident id ) {
    return map.get( id );
  }
  
  @ Override
  public T getDefault() {
    return map.getOrDefault( null, defaultValue );
  }
  
  public void register( final Ident id, final T value ) {
    map.put( id, value );
  }
}