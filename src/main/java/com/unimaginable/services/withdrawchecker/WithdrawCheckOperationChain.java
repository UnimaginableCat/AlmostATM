package com.unimaginable.services.withdrawchecker;

public abstract class WithdrawCheckOperationChain {

  private WithdrawCheckOperationChain nextOperation;


  public static WithdrawCheckOperationChain link(WithdrawCheckOperationChain first,
      WithdrawCheckOperationChain... chain) {
    WithdrawCheckOperationChain head = first;
    for (WithdrawCheckOperationChain nextInChain : chain) {
      head.nextOperation = nextInChain;
      head = nextInChain;
    }
    return first;
  }

  public abstract boolean check(Integer amount);

  protected boolean checkNext(Integer amount) {
    if (nextOperation == null) {
      return true;
    }
    return nextOperation.check(amount);
  }

}
