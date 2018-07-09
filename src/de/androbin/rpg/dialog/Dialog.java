package de.androbin.rpg.dialog;

import de.androbin.rpg.*;
import de.androbin.rpg.event.*;
import java.util.*;
import java.util.function.*;

public final class Dialog {
  public final List<String> pages;
  public final List<Choice> choices;
  
  public Dialog( final String id ) {
    this( Ident.parse( id ) );
  }
  
  public Dialog( final Ident id ) {
    final String text = Dialogs.loadText( id );
    
    this.pages = Arrays.asList( text.split( "\n\n\n" ) );
    this.choices = new ArrayList<>();
  }
  
  public void addChoice( final String label, final Supplier<Event> event ) {
    choices.add( new Choice( label, event ) );
  }
}