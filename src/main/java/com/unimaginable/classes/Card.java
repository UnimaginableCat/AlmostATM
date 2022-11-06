package com.unimaginable.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Card {

  @NonNull
  private final String cardNumber;
  @NonNull
  private String password;
  @Setter
  @Getter
  @NonNull
  private double balance; // Balance in rubles

}
