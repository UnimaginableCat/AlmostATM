package com.unimaginable.services.credschecker;

import com.unimaginable.services.credschecker.CredsCheckerOperationChain;

public class CardNumberLengthChecker extends CredsCheckerOperationChain {

  @Override
  public boolean check(String cardNumber, String password) {
    if (cardNumber.length() != 16) {
      System.out.println("Invalid card number, must be 16 digit number!");
      return false;
    }
    return checkNext(cardNumber, password);
  }
}
