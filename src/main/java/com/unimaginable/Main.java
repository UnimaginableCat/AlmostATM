package com.unimaginable;

import com.unimaginable.classes.ATM;
import com.unimaginable.classes.Account;
import com.unimaginable.classes.Card;
import com.unimaginable.classes.CurrencyImpl;
import com.unimaginable.classes.UserRequestHandler;
import com.unimaginable.interfaces.Currency;
import java.util.ArrayList;
import java.util.List;

public class Main {

  static List<Account> generateAccountList() {
    List<Account> accountList = new ArrayList<>();
    Account account1 = new Account("1", "Иванов Иван");
    Card card = new Card("4433117863424090", "1111", 5000.52);
    account1.addCard(card);
    accountList.add(account1);
    return accountList;
  }

  static List<Currency> generateCurrencyList() {
    List<Currency> currencyList = new ArrayList<>();

    Currency currencyRUB = new CurrencyImpl("RUB", 1);
    currencyRUB.addNewBanknote(100, 1);
    currencyRUB.addNewBanknote(5000, 5);
    currencyRUB.addNewBanknote(2000, 10);
    currencyRUB.addNewBanknote(500, 25);
//    currencyRUB.addBanknote(1000, 15);
    currencyRUB.addNewBanknote(500, 25);

    Currency currencyEUR = new CurrencyImpl("EUR", 60.38);
    currencyEUR.addNewBanknote(1, 159);
    currencyEUR.addNewBanknote(20, 12);

    Currency currencyUSD = new CurrencyImpl("USD", 61.93);
    currencyUSD.addNewBanknote(1, 1);
    currencyUSD.addNewBanknote(5, 25);

    currencyList.add(currencyEUR);
    currencyList.add(currencyRUB);
    currencyList.add(currencyUSD);

    return currencyList;

  }

  public static void main(String[] args) {
    ATM atm = new ATM(generateCurrencyList(), generateAccountList());

    UserRequestHandler userService = new UserRequestHandler(atm);
  }
}