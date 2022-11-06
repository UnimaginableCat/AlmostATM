package com.unimaginable.services.withdrawchecker;

import com.unimaginable.classes.ATM;
import com.unimaginable.classes.Card;

public class UserBalanceChecker extends WithdrawCheckOperationChain {

  private ATM atm;
  private String cardNumber;

  public UserBalanceChecker(ATM atm, String cardNumber) {
    this.atm = atm;
    this.cardNumber = cardNumber;
  }

  @Override
  public boolean check(Integer amount) {
    Card card = atm.getCard(cardNumber);
    if (card.getBalance() < amount) {
      System.out.println("You dont have enough money!");
      return false;
    }
    return checkNext(amount);
  }
}
