package de.androbin.rpg.dialog;

import de.androbin.json.*;

public final class Page {
  public final String text;
  public final XObject props;
  
  public Page( final String text, final XObject props ) {
    this.text = text;
    this.props = props;
  }
}