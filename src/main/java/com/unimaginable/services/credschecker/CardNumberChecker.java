package com.unimaginable.services.credschecker;

import com.unimaginable.classes.ATM;
import com.unimaginable.services.credschecker.CredsCheckerOperationChain;

public class CardNumberChecker extends CredsCheckerOperationChain {

  private ATM atm;

  public CardNumberChecker(ATM atm) {
    this.atm = atm;
  }

  /**
   * Method that checks presence of card in atm database
   *
   * @param cardNumber card Number
   * @param password   password
   * @return check result
   */
  @Override
  public boolean check(String cardNumber, String password) {

    if (!atm.checkCardNumber(cardNumber)) {
      System.out.println("There is no such card in ATM database");
      return false;
    }
    return checkNext(cardNumber, password);


  }
}
