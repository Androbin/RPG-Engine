package de.androbin.rpg;

import de.androbin.json.*;
import java.util.*;

public interface Detailed {
  default void load( XObject details ) {
  }
  
  default Map<String, Object> save() {
    final Map<String, Object> details = new HashMap<>();
    save( details );
    return details;
  }
  
  default String save( final boolean pretty ) {
    return XObject.toString( save(), pretty );
  }
  
  default void save( Map<String, Object> details ) {
  }
}