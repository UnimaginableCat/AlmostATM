package com.unimaginable.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

  Account account1;
  @BeforeEach
  void setUp() {
    account1 = new Account("1", "Иванов Иван");
    Card card = new Card("4433117863424090", "1111", 5169.52);
    account1.addCard(card);
  }

  @Test
  void checkCardPresence() {
    Assertions.assertTrue(account1.checkCardPresence("4433117863424090"));
    Assertions.assertFalse(account1.checkCardPresence("4822640571758185"));
  }
}