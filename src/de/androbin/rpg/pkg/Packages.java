package de.androbin.rpg.pkg;

import de.androbin.json.*;
import de.androbin.rpg.*;
import java.util.*;

public final class Packages {
  private final Map<Ident, PackageData> data = new HashMap<>();
  
  public PackageData.Builder dataBuilder = PackageData::new;
  
  private final String prefix;
  
  public Packages( final String prefix ) {
    this.prefix = prefix;
  }
  
  private PackageData createData( final Ident type ) {
    final XObject props = XUtil.readJSONObject( prefix + "/" + type + "/package.json" );
    return dataBuilder.build( type, props );
  }
  
  public PackageData getData( final Ident type ) {
    return data.computeIfAbsent( type, this::createData );
  }
  
  public void invalidate() {
    data.clear();
  }
}