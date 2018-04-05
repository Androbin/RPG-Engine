package de.androbin.rpg.pkg;

import java.awt.*;
import org.json.simple.*;
import de.androbin.rpg.*;
import de.androbin.util.*;

public class PackageData {
  public final Ident type;
  
  public final Dimension sheetSize;
  
  public PackageData( final Ident type, final JSONObject props ) {
    this.type = type;
    
    this.sheetSize = JSONUtil.toDimension( props.get( "sheet.size" ) );
  }
  
  @ FunctionalInterface
  public interface Builder {
    PackageData build( Ident type, JSONObject props );
  }
}