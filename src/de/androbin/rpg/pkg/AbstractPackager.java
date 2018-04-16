package de.androbin.rpg.pkg;

import de.androbin.rpg.*;

public abstract class AbstractPackager<T> implements Packager<T> {
  protected abstract T get( Ident id );
  
  @ Override
  public final T select( final Ident id ) {
    T best = getDefault();
    
    for ( final Ident partialId : id.partial() ) {
      final T value = get( partialId );
      
      if ( value != null ) {
        best = value;
      }
    }
    
    return best;
  }
}