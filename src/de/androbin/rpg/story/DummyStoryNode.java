package de.androbin.rpg.story;

import de.androbin.rpg.*;
import java.util.*;

public final class DummyStoryNode extends StoryNode {
  public DummyStoryNode( final String id, final List<Ident> deps ) {
    this( Ident.fromSerial( id ), deps );
  }
  
  public DummyStoryNode( final Ident id, final List<Ident> deps ) {
    super( id, null );
  }
  
  @ Override
  public void finish() {
  }
  
  @ Override
  public boolean update() {
    return false;
  }
}