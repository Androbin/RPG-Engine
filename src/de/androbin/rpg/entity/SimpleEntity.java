package de.androbin.rpg.entity;

public final class SimpleEntity extends Entity {
  private final EntityData data;
  
  public SimpleEntity( final EntityData data, final int id ) {
    super( id );
    this.data = data;
  }
  
  @ Override
  public EntityData getData() {
    return data;
  }
}