package com.unimaginable.services.withdrawchecker;

public class AmountMultiplyChecker extends WithdrawCheckOperationChain {

  private Integer multiplicityOfNumber;

  public AmountMultiplyChecker(Integer multiplicityOfNumber) {
    this.multiplicityOfNumber = multiplicityOfNumber;
  }

  @Override
  public boolean check(Integer amount) {
    if (amount % multiplicityOfNumber != 0) {
      System.out.println("Number is not a multiple!");
      return false;
    }
    return checkNext(amount);
  }
}
