package de.androbin.rpg.pkg;

import de.androbin.rpg.*;

public interface Packager<T> {
  T getDefault();
  
  T select( Ident id );
}