package com.unimaginable.classes;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyTest {

  CurrencyImpl currencyImpl;
  @BeforeEach
  void setup(){
    currencyImpl = new CurrencyImpl("RUB", 1);
  }
  @Test
  void addBanknote() {
    currencyImpl.addNewBanknote(5000, 5);
    Assertions.assertEquals(5, currencyImpl.getBanknoteQuantity(5000));
  }
  @Test
  void getTotalValue() {
    currencyImpl.addNewBanknote(5000, 28);
    currencyImpl.addNewBanknote(2000, 41);
    currencyImpl.addNewBanknote(1000, 87);
    currencyImpl.addNewBanknote(500, 62);
    currencyImpl.addNewBanknote(200, 49);
    currencyImpl.addNewBanknote(100, 57);
    currencyImpl.addNewBanknote(50, 34);

    Assertions.assertEquals(357200, currencyImpl.getTotal());
  }
}