package com.unimaginable.services.credschecker;

public abstract class CredsCheckerOperationChain {

  private CredsCheckerOperationChain nextOperation;


  public static CredsCheckerOperationChain link(CredsCheckerOperationChain first,
      CredsCheckerOperationChain... chain) {
    CredsCheckerOperationChain head = first;
    for (CredsCheckerOperationChain nextInChain : chain) {
      head.nextOperation = nextInChain;
      head = nextInChain;
    }
    return first;
  }

  public abstract boolean check(String cardNumber, String password);

  protected boolean checkNext(String cardNumber, String password) {
    if (nextOperation == null) {
      return true;
    }
    return nextOperation.check(cardNumber, password);
  }

}
