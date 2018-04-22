package de.androbin.rpg.story;

import de.androbin.rpg.*;
import java.util.*;

public final class StoryState {
  private final StoryGraph graph;
  private final Set<Ident> done;
  private final List<Ident> active;
  
  public StoryState( final StoryGraph graph ) {
    this.graph = graph;
    done = new HashSet<>();
    active = graph.getDeps( null );
  }
  
  public boolean check( final String id ) {
    return check( Ident.fromSerial( id ) );
  }
  
  public boolean check( final Ident id ) {
    return done.contains( id );
  }
  
  private boolean checkDeps( final StoryNode node ) {
    for ( final Ident dep : node.deps ) {
      if ( !check( dep ) ) {
        return false;
      }
    }
    
    return true;
  }
  
  public void setDone( final String id ) {
    setDone( Ident.fromSerial( id ) );
  }
  
  public void setDone( final Ident id ) {
    final StoryNode node = graph.getNode( id );
    node.finish();
    
    active.remove( id );
    
    for ( final Ident dep : graph.getDeps( id ) ) {
      final StoryNode depNode = graph.getNode( dep );
      
      if ( !active.contains( dep ) && checkDeps( depNode ) ) {
        active.add( dep );
      }
    }
  }
  
  public void update() {
    final List<Ident> done = new ArrayList<>();
    
    for ( final Ident id : active ) {
      final StoryNode node = graph.getNode( id );
      
      if ( node.update() ) {
        done.add( id );
      }
    }
    
    this.done.addAll( done );
    
    for ( final Ident node : done ) {
      setDone( node );
    }
  }
}