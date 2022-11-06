package com.unimaginable.services.withdrawchecker;

import com.unimaginable.classes.ATM;
import com.unimaginable.interfaces.Currency;

public class AtmBalanceChecker extends WithdrawCheckOperationChain {

  private ATM atm;
  private Currency currency;

  public AtmBalanceChecker(ATM atm, Currency currency) {
    this.currency = currency;
    this.atm = atm;
  }

  @Override
  public boolean check(Integer amount) {
    if (atm.getCurrencyTotal(currency) < amount || atm.getCurrencyTotal(currency) == 0) {
      System.out.println("Not enough money in atm!");
      return false;
    }
    return checkNext(amount);
  }
}
