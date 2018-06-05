package de.androbin.rpg.story;

import de.androbin.json.*;
import de.androbin.rpg.*;
import java.util.*;

public final class StoryState implements Pooled {
  private final StoryGraph graph;
  private final Set<String> done;
  private final List<String> active;
  
  public StoryState( final StoryGraph graph ) {
    this.graph = graph;
    done = new HashSet<>();
    active = graph.getDeps( null );
  }
  
  public boolean check( final String id ) {
    return done.contains( id );
  }
  
  private boolean checkDeps( final StoryNode node ) {
    for ( final String dep : node.deps ) {
      if ( !check( dep ) ) {
        return false;
      }
    }
    
    return true;
  }
  
  @ Override
  public void load( final XArray pool ) {
    Collection<String> target = done;
    
    for ( final XValue value : pool ) {
      if ( value.isNull() ) {
        target = active;
        continue;
      }
      
      target.add( value.asString() );
    }
  }
  
  @ Override
  public void save( final List<Object> pool ) {
    pool.addAll( done );
    pool.add( null );
    pool.addAll( active );
  }
  
  public void setDone( final String id ) {
    if ( done.contains( id ) ) {
      return;
    }
    
    final StoryNode node = graph.getNode( id );
    node.finish();
    
    active.remove( id );
    done.add( id );
    
    for ( final String dep : graph.getDeps( id ) ) {
      final StoryNode depNode = graph.getNode( dep );
      
      if ( !active.contains( dep ) && checkDeps( depNode ) ) {
        active.add( dep );
      }
    }
  }
  
  public void update() {
    final List<String> done = new ArrayList<>();
    
    for ( final String id : active ) {
      final StoryNode node = graph.getNode( id );
      
      if ( node.update() ) {
        done.add( id );
      }
    }
    
    for ( final String node : done ) {
      setDone( node );
    }
  }
}