package com.unimaginable.classes;

import com.unimaginable.interfaces.Currency;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ATMTest {

  ATM atm;

  @BeforeEach
  void setUp() {
    atm = new ATM(generateCurrencyList(), generateAccountList());
  }

  List<Account> generateAccountList() {
    List<Account> accountList = new ArrayList<>();
    Account account1 = new Account("1", "Иванов Иван");
    Card card = new Card("4433117863424090", "1111", 5169.52);
    account1.addCard(card);
    accountList.add(account1);
    return accountList;
  }

  List<Currency> generateCurrencyList() {
    List<Currency> currencyList = new ArrayList<>();

    Currency currencyRUB = new CurrencyImpl("RUB", 1);
    currencyRUB.addNewBanknote(5000, 5);

    Currency currencyEUR = new CurrencyImpl("EUR", 60.38);
    currencyEUR.addNewBanknote(20, 12);

    Currency currencyUSD = new CurrencyImpl("USD", 61.93);
    currencyUSD.addNewBanknote(5, 25);

    currencyList.add(currencyEUR);
    currencyList.add(currencyRUB);
    currencyList.add(currencyUSD);

    return currencyList;

  }

  @Test
  void checkCardNumber() {
    Assertions.assertTrue(atm.checkCardNumber("4433117863424090"));
    Assertions.assertFalse(atm.checkCardNumber("4679283663022618"));
  }
}