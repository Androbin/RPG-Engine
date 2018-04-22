package de.androbin.rpg.story;

import de.androbin.rpg.*;
import java.util.*;

public abstract class StoryNode {
  public final Ident id;
  public final List<Ident> deps;
  
  public StoryNode( final String id, final List<Ident> deps ) {
    this( Ident.fromSerial( id ), deps );
  }
  
  public StoryNode( final Ident id, final List<Ident> deps ) {
    this.id = id;
    this.deps = deps == null ? Collections.emptyList() : deps;
  }
  
  public abstract void finish();
  
  public abstract boolean update();
}