package de.androbin.rpg.story;

import de.androbin.rpg.*;
import java.util.*;

public class DummyStoryNode extends StoryNode {
  public DummyStoryNode( final String id, final List<Ident> deps ) {
    this( Ident.fromSerial( id ), deps );
  }
  
  public DummyStoryNode( final Ident id, final List<Ident> deps ) {
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