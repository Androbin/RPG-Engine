package de.androbin.rpg.story;

import java.util.*;

public final class StoryGraph {
  private final Map<String, StoryNode> nodes;
  private final Map<String, List<String>> deps;
  
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
    
    for ( final String dep : node.deps ) {
      deps.computeIfAbsent( dep, foo -> new ArrayList<>() )
          .add( node.id );
    }
  }
  
  public List<String> getDeps( final String id ) {
    return deps.getOrDefault( id, Collections.emptyList() );
  }
  
  public StoryNode getNode( final String id ) {
    return nodes.get( id );
  }
}