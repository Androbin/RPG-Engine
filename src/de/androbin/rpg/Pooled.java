package de.androbin.rpg;

import de.androbin.json.*;
import java.util.*;

public interface Pooled {
  default void load( XArray pool ) {
  }
  
  default List<Object> save() {
    final List<Object> pool = new ArrayList<>();
    save( pool );
    return pool;
  }
  
  default String save( final boolean pretty ) {
    return XArray.toString( save(), pretty );
  }
  
  default void save( List<Object> pool ) {
  }
}