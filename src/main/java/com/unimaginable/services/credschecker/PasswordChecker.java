package com.unimaginable.services.credschecker;

import com.unimaginable.classes.ATM;
import com.unimaginable.classes.Card;
import com.unimaginable.services.credschecker.CredsCheckerOperationChain;

public class PasswordChecker extends CredsCheckerOperationChain {

  private ATM atm;

  public PasswordChecker(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean check(String cardNumber, String password) {
    Card tempCard = atm.getCard(cardNumber);
    if (tempCard == null) {
      return false;
    }

    if (!(tempCard.getCardNumber().equals(cardNumber) && tempCard.getPassword().equals(password))) {
      System.out.println("Invalid password");
      return false;
    }
    return checkNext(cardNumber, password);
  }
}
