package de.androbin.rpg.dialog;

import de.androbin.io.util.*;
import de.androbin.rpg.*;

public final class Dialogs {
  private Dialogs() {
  }
  
  public static String loadText( final Ident id ) {
    return FileReaderUtil.read( "txt/dialog/" + id + ".txt" );
  }
}