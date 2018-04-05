package de.androbin.rpg.pkg;

import static de.androbin.util.JSONUtil.*;
import java.util.*;
import org.json.simple.*;
import de.androbin.rpg.*;

public final class Packages {
  private final Map<Ident, PackageData> data = new HashMap<>();
  
  public PackageData.Builder dataBuilder = PackageData::new;
  
  private final String prefix;
  
  public Packages( final String prefix ) {
    this.prefix = prefix;
  }
  
  private PackageData createData( final Ident type ) {
    final JSONObject props = (JSONObject) parseJSON( prefix + "/" + type + "/package.json" )
        .orElseGet( JSONObject::new );
    return dataBuilder.build( type, props );
  }
  
  public PackageData getData( final Ident type ) {
    return data.computeIfAbsent( type, this::createData );
  }
}