package de.androbin.rpg.dialog;

import de.androbin.json.*;
import java.util.*;

public final class DialogBuilder {
  private final List<Page> pages;
  
  private final StringBuilder text;
  private Map<String, String> props;
  public boolean newline;
  
  public DialogBuilder() {
    pages = new ArrayList<>();
    
    text = new StringBuilder();
    props = new HashMap<>();
  }
  
  public Dialog build() {
    return new Dialog( pages );
  }
  
  public void addText( final String line ) {
    if ( text.length() > 0 ) {
      if ( newline ) {
        newPage( true );
      } else {
        text.append( '\n' );
      }
    }
    
    text.append( line );
  }
  
  public void newPage( final boolean keepProps ) {
    pages.add( new Page( text.toString(), new XObject( props ) ) );
    text.setLength( 0 );
    props = keepProps ? new HashMap<>( props ) : new HashMap<>();
  }
  
  public void setProp( final String key, final String value ) {
    if ( key.equals( "agent" ) && text.length() > 0 ) {
      newPage( false );
    }
    
    props.put( key, value );
  }
}