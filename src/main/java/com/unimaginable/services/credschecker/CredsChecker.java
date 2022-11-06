package com.unimaginable.services.credschecker;

import com.unimaginable.classes.ATM;
import com.unimaginable.services.credschecker.CredsCheckerOperationChain;

/**
 * Class that checks card number and password in many  ways
 */
public class CredsChecker {

  private CredsCheckerOperationChain operationChain;
  private ATM atm;

  public void setOperationChain(CredsCheckerOperationChain operationChain) {
    this.operationChain = operationChain;

  }

  public boolean checkOperationAvailability(String cardNumber, String password) {
    return operationChain.check(cardNumber, password);

  }


}
