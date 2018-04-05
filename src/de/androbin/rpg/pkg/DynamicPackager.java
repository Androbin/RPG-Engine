package de.androbin.rpg.pkg;

import java.util.function.*;
import de.androbin.rpg.*;

public final class DynamicPackager<T> extends AbstractPackager<T> {
  private final Function<Ident, T> backend;
  private final T defaultValue;
  
  public DynamicPackager( final Function<Ident, T> backend ) {
    this( null, backend );
  }
  
  public DynamicPackager( final T defaultValue, final Function<Ident, T> backend ) {
    this.backend = backend;
    this.defaultValue = defaultValue;
  }
  
  @ Override
  protected T get( final Ident id ) {
    return backend.apply( id );
  }
  
  @ Override
  public T getDefault() {
    return defaultValue;
  }
}