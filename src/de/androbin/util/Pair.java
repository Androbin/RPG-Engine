package de.androbin.util;

public final class Pair<T, S> {
  public T first;
  public S second;
  
  public Pair( final T first, final S second ) {
    this.first = first;
    this.second = second;
  }
}