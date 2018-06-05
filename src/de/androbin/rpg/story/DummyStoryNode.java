package de.androbin.rpg.story;

import java.util.*;

public class DummyStoryNode extends StoryNode {
  public DummyStoryNode( final String id, final List<String> deps ) {
    super( id, deps );
  }
  
  @ Override
  public void finish() {
  }
  
  @ Override
  public final boolean update() {
    return false;
  }
}