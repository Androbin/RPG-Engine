package de.androbin.rpg.event;

import java.awt.*;
import java.util.*;
import de.androbin.rpg.*;

public final class TeleportEvent implements Event {
  public static final Event.Builder BUILDER = args -> {
    switch ( args.length ) {
      case 3:
        return new TeleportEvent( args[ 0 ],
            new Point( Integer.parseInt( args[ 1 ] ), Integer.parseInt( args[ 2 ] ) ) );
      
      case 2:
        return new TeleportEvent(
            new Point( Integer.parseInt( args[ 0 ] ), Integer.parseInt( args[ 1 ] ) ) );
    }
    
    return null;
  };
  
  private final Identifier world;
  private final Point pos;
  
  public TeleportEvent( final Point pos ) {
    this( null, pos );
  }
  
  public TeleportEvent( final String serial, final Point pos ) {
    this.world = Identifier.fromSerial( serial );
    this.pos = pos;
  }
  
  @ Override
  public String getLogMessage() {
    return "teleport { world: '" + world + "', pos: (" + pos.x + ", " + pos.y + ") }";
  }
  
  @ Override
  public void run( final RPGScreen master, final Map<String, Object> args ) {
    final Entity entity = (Entity) args.get( "entity" );
    
    if ( world == null ) {
      entity.moveTo( pos );
    } else {
      if ( entity.equals( master.player ) ) {
        master.switchWorld( world, pos );
      }
    }
  }
}