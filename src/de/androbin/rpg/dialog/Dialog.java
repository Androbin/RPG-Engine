package de.androbin.rpg.dialog;

import java.util.*;

public final class Dialog {
  public final List<Page> pages;
  public final List<Choice> choices;
  
  public Dialog( final List<Page> pages ) {
    this.pages = pages;
    choices = new ArrayList<>();
  }
}