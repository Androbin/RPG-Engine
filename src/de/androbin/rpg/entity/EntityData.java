package de.androbin.rpg.entity;

import de.androbin.json.*;
import de.androbin.rpg.*;
import de.androbin.rpg.gfx.sheet.*;
import de.androbin.space.Shape;
import java.awt.*;

public class EntityData extends Data {
  public final boolean solid;
  public final Shape shape;
  
  public final Sheet sheet;
  public final int sheetDX;
  public final int sheetDY;
  
  public EntityData( final Ident type, final XObject props ) {
    super( type, props );
    
    this.solid = props.get( "solid" ).asBoolean( true );
    final Dimension size = new Dimension(
        props.get( "width" ).asInt( 1 ),
        props.get( "height" ).asInt( 1 ) );
    this.shape = Shape.rect( size );
    
    this.sheet = Sheets.createEntity( type );
    this.sheetDX = props.get( "dx" ).asInt( 0 );
    this.sheetDY = props.get( "dy" ).asInt( 0 );
  }
  
  @ FunctionalInterface
  public interface Builder {
    EntityData build( Ident type, XObject props );
  }
}