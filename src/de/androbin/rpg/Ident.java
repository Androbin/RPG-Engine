package de.androbin.rpg;

import java.util.*;

public final class Ident implements Iterable<String> {
  private final String[] path;
  private String serial;
  private int hash;
  
  private Ident( final String[] path ) {
    this.path = path;
  }
  
  @ Override
  public boolean equals( final Object obj ) {
    if ( obj instanceof Ident ) {
      final Ident id = (Ident) obj;
      return Arrays.equals( path, id.path );
    } else {
      return false;
    }
  }
  
  public String firstElement() {
    return path[ 0 ];
  }
  
  @ Override
  public int hashCode() {
    if ( hash == 0 ) {
      hash = Arrays.hashCode( path );
    }
    
    return hash;
  }
  
  @ Override
  public Iterator<String> iterator() {
    return Arrays.asList( path ).iterator();
  }
  
  public String lastElement() {
    return path[ path.length - 1 ];
  }
  
  public static Ident parse( final String serial ) {
    if ( serial == null ) {
      return null;
    }
    
    return new Ident( serial.split( "/" ) );
  }
  
  public Iterable<Ident> partial() {
    return PartialIterator::new;
  }
  
  private Ident range( final int start, final int end ) {
    return new Ident( Arrays.copyOfRange( path, start, end ) );
  }
  
  @ Override
  public String toString() {
    if ( serial == null ) {
      final StringJoiner joiner = new StringJoiner( "/" );
      
      for ( final String element : path ) {
        joiner.add( element );
      }
      
      serial = joiner.toString();
    }
    
    return serial;
  }
  
  private final class PartialIterator implements Iterator<Ident> {
    private int end = 1;
    
    @ Override
    public boolean hasNext() {
      return end <= path.length;
    }
    
    @ Override
    public Ident next() {
      return range( 0, end++ );
    }
  }
}