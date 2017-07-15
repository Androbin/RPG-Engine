package de.androbin.rpg;

import java.util.*;

public final class Identifier implements Iterable<String> {
  private final String[] path;
  private final String serial;
  private final int hash;
  
  public Identifier( final Builder builder ) {
    this.path = builder.path;
    this.serial = builder.serial;
    this.hash = builder.hash;
  }
  
  @ Override
  public boolean equals( final Object obj ) {
    if ( obj instanceof Identifier ) {
      final Identifier id = (Identifier) obj;
      return Arrays.equals( path, id.path );
    } else {
      return false;
    }
  }
  
  public static Identifier fromSerial( final String serial ) {
    if ( serial == null ) {
      return null;
    }
    
    final Builder builder = new Builder();
    final String[] elements = serial.split( " > " );
    
    for ( final String element : elements ) {
      builder.add( element );
    }
    
    return builder.build();
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
  
  @ Override
  public String toString() {
    return serial;
  }
  
  public static class Builder {
    private final List<String> pathList;
    
    private String[] path;
    private String serial;
    private int hash;
    
    public Builder() {
      pathList = new ArrayList<>();
    }
    
    public Builder add( final String element ) {
      pathList.add( element );
      return this;
    }
    
    public Identifier build() {
      final StringJoiner joiner = new StringJoiner( " > " );
      
      for ( final String element : pathList ) {
        joiner.add( element );
      }
      
      path = pathList.toArray( new String[ pathList.size() ] );
      serial = joiner.toString();
      hash = serial.hashCode();
      
      return new Identifier( this );
    }
  }
}