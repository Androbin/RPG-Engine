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
  
  private String world;
  
  private Point pos;
  
  public TeleportEvent( final Point pos ) {
    this( null, pos );
  }
  
  public TeleportEvent( final String world, final Point pos ) {
    this.world = world;
    this.pos = pos;
  }
  
  @ Override
  public void run( final Map<String, Object> args ) {
    final Entity entity = (Entity) args.get( "entity" );
    
    if ( world == null ) {
      entity.moveTo( pos );
    } else {
      final RPGScreen screen = (RPGScreen) args.get( "screen" );
      
      if ( entity.equals( screen.player ) ) {
        screen.switchWorld( world, pos );
      }
    }
  }
  
  @ Override
  public String toString() {
    return "teleport { x: " + pos.x + ", y: " + pos.y + " }";
  }
}