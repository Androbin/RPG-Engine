package de.androbin.rpg.story;

import de.androbin.rpg.*;
import java.util.*;

public final class StoryGraph {
  private final Map<Ident, StoryNode> nodes;
  private final Map<Ident, List<Ident>> deps;
  
  public StoryGraph() {
    nodes = new HashMap<>();
    deps = new HashMap<>();
  }
  
  public void add( final StoryNode node ) {
    nodes.put( node.id, node );
    
    if ( node.deps.isEmpty() ) {
      deps.computeIfAbsent( null, foo -> new ArrayList<>() )
          .add( node.id );
      return;
    }
    
    for ( final Ident dep : node.deps ) {
      deps.computeIfAbsent( dep, foo -> new ArrayList<>() )
          .add( node.id );
    }
  }
  
  public List<Ident> getDeps( final Ident id ) {
    return deps.getOrDefault( id, Collections.emptyList() );
  }
  
  public StoryNode getNode( final Ident id ) {
    return nodes.get( id );
  }
}