package com.unimaginable.services.credschecker;

import com.unimaginable.services.credschecker.CredsCheckerOperationChain;

public class PasswordLengthChecker extends CredsCheckerOperationChain {

  @Override
  public boolean check(String cardNumber, String password) {
    if (password.length() != 4) {
      System.out.println("Invalid password, must be 4 digit number!");
      return false;
    }
    return checkNext(cardNumber, password);
  }
}
