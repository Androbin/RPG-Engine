package de.androbin.rpg;

import de.androbin.mixin.*;
import java.util.*;

public final class Ident implements Iterable<String> {
  private final String[] path;
  private final String serial;
  private final int hash;
  
  private Ident( final String[] path, final String serial ) {
    this.path = path;
    this.serial = serial;
    this.hash = serial.hashCode();
  }
  
  public static Ident build( final String[] path ) {
    if ( path == null ) {
      return null;
    }
    
    final StringJoiner joiner = new StringJoiner( "/" );
    
    for ( final String element : path ) {
      joiner.add( element );
    }
    
    final String serial = joiner.toString();
    return new Ident( path, serial );
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
  
  public static Ident fromSerial( final String serial ) {
    return build( serial.split( "/" ) );
  }
  
  @ Override
  public int hashCode() {
    return hash;
  }
  
  @ Override
  public Iterator<String> iterator() {
    return Arrays.asList( path ).iterator();
  }
  
  public String lastElement() {
    return path[ path.length - 1 ];
  }
  
  public Ident parent() {
    return range( 0, path.length - 1 );
  }
  
  public Iterable<Ident> partial() {
    return new MixIterable<>( PartialIterator::new );
  }
  
  private Ident range( final int start, final int end ) {
    return build( Arrays.copyOfRange( path, start, end ) );
  }
  
  @ Override
  public String toString() {
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