package de.androbin.rpg;

public enum Direction {
  UP( 0, -1 ), LEFT( -1, 0 ), DOWN( 0, 1 ), RIGHT( 1, 0 );
  
  public final int dx;
  public final int dy;
  
  private Direction( final int dx, final int dy ) {
    this.dx = dx;
    this.dy = dy;
  }
  
  public Direction opposite() {
    return values()[ ( ordinal() + 2 ) % 4 ];
  }
}