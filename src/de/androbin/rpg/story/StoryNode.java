package de.androbin.rpg.story;

import java.util.*;

public abstract class StoryNode {
  public final String id;
  public final List<String> deps;
  
  public StoryNode( final String id, final List<String> deps ) {
    this.id = id;
    this.deps = deps == null ? Collections.emptyList() : deps;
  }
  
  public abstract void finish();
  
  public abstract boolean update();
}