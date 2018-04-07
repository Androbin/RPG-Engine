package de.androbin.rpg.pkg;

import de.androbin.json.*;
import de.androbin.rpg.*;
import java.awt.*;

public class PackageData {
  public final Ident type;
  
  public final Dimension sheetSize;
  
  public PackageData( final Ident type, final XObject props ) {
    this.type = type;
    
    this.sheetSize = props.get( "sheet.size" ).asDimension();
  }
  
  @ FunctionalInterface
  public interface Builder {
    PackageData build( Ident type, XObject props );
  }
}