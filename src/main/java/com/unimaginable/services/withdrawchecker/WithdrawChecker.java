package com.unimaginable.services.withdrawchecker;

import com.unimaginable.classes.ATM;
import com.unimaginable.services.credschecker.CredsCheckerOperationChain;


public class WithdrawChecker {

  private WithdrawCheckOperationChain operationChain;
  private ATM atm;

  public void setOperationChain(WithdrawCheckOperationChain operationChain) {
    this.operationChain = operationChain;

  }

  public boolean checkOperationAvailability(Integer amount) {
    return operationChain.check(amount);

  }


}
