package de.androbin.rpg.dialog;

import de.androbin.io.*;
import de.androbin.io.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class Dialogs {
  private Dialogs() {
  }
  
  public static Dialog create( final String id ) {
    final String path = "txt/dialog/" + id + ".txt";
    final DialogBuilder builder = new DialogBuilder();
    
    try {
      parse( builder, DynamicClassLoader.getPath( path ) );
    } catch ( final IOException e ) {
      return null;
    }
    
    return builder.build();
  }
  
  private static void parse( final DialogBuilder builder, final Path path ) throws IOException {
    try ( final Scanner scanner = FileReaderUtil.scanFile( path ) ) {
      while ( scanner.hasNextLine() ) {
        final String line = scanner.nextLine();
        
        if ( line.isEmpty() ) {
          builder.newline = true;
          continue;
        }
        
        if ( line.charAt( 0 ) == '@' ) {
          final String[] prop = line.substring( 1 ).split( ":\\s" );
          builder.setProp( prop[ 0 ], prop[ 1 ] );
        } else {
          builder.addText( line );
        }
        
        builder.newline = false;
      }
    }
    
    builder.newPage( false );
  }
}