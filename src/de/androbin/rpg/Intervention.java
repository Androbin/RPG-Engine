package de.androbin.rpg;

public enum Intervention {
  TRANSPARENT( false, false ), MASKING( false, true ), FREEZING( true, true );
  
  public final boolean freezing;
  public final boolean masking;
  
  private Intervention( final boolean freezing, final boolean masking ) {
    this.freezing = freezing;
    this.masking = masking;
  }
}