package com.unimaginable.classes;

import com.unimaginable.interfaces.Currency;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM {


  private List<Currency> currencyList;

  private List<Account> accountsList;

  public ATM() {
    currencyList = new ArrayList<>();
    accountsList = new ArrayList<>();
  }

  /**
   * Method that gets total amount of currency in atm(in rubles)
   *
   * @return amount of currency
   */
  public double getTotalATMBalanceInRubles() {
    double result = 0;
    for (Currency currency : currencyList) {
      result += currency.getTotalInRubles();
    }
    return result;
  }

  public ATM(List<Currency> currencyList, List<Account> accountsList) {
    this.currencyList = currencyList;
    this.accountsList = accountsList;
  }

  /**
   * method that checks for the presence of a card number in an ATM
   *
   * @param cardNumber card number
   * @return check result
   */
  public boolean checkCardNumber(String cardNumber) {
    for (Account account : accountsList) {
      if (account.checkCardPresence(cardNumber)) {
        return true;
      }
    }
    return false;
  }

  public List<Currency> getCurrencyList() {
    return currencyList;
  }

  public Currency getCurrency(Currency currencyToGet) {
    for (Currency currency : currencyList) {
      if (currency.equals(currencyToGet)) {
        return currency;
      }
    }
    return null;
  }

  public double getCurrencyTotal(Currency currencyToCheck) {
    for (Currency currency : currencyList) {
      if (currency.equals(currencyToCheck)) {
        return currency.getTotal();
      }
    }
    return 0;
  }

  public void addMoneyToATM(Integer nominalValue, Integer quantity, Currency currency) {
    getCurrency(currency).updateBanknoteQuantity(nominalValue, quantity);
  }

  public void removeMoneyFromATM(Map<Integer, Integer> nominalValuesWithQuantity,
      Currency currency) {
    for (Map.Entry<Integer, Integer> entry : nominalValuesWithQuantity.entrySet()) {
      getCurrency(currency).removeBanknote(entry.getKey(), entry.getValue());
    }
  }

  public void removeBalanceFromCard(String cardNumber, double amount, Currency currency) {
    Card card = getCard(cardNumber);
    double oldBalance = card.getBalance();
    card.setBalance(oldBalance - amount * currency.getRate());
  }

  public void addBalanceToCard(String cardNumber, double amount, Currency currency) {
    Card card = getCard(cardNumber);
    double oldBalance = card.getBalance();
    card.setBalance(oldBalance + amount * currency.getRate());
  }

  public double getCardBalance(String cardNumber) {
    Card card = getCard(cardNumber);
    return card.getBalance();
  }

  public String getCardHolderName(String cardNumber) {
    for (Account account : accountsList) {
      Card tempCard = account.getCard(cardNumber);
      if (tempCard != null) {
        return account.getHolderName();
      }
    }
    return null;
  }

  /**
   * Method calculates all possible withdrawing for given currency and amount
   *
   * @param currency
   * @param amount
   * @return List of maps with nominal value as key and quantity as value
   */
  public List<Map<Integer, Integer>> calculatePossibleWithdrawing(Currency currency,
      Integer amount) {
    int[] values = currency.getNominalValuesArray();
    int[] amounts = currency.getAmountsOfBanknotes();

    List<Integer[]> possibleAmountsToWithdraw = solveWithdrawProblem(values, amounts,
        new int[values.length], amount, 0);
    List<Map<Integer, Integer>> results = new ArrayList<>();

    for (Integer[] array : possibleAmountsToWithdraw) {
      Map<Integer, Integer> tempMap = new HashMap<>();
      for (int i = 0; i < array.length; i++) {
        if (array[i] != 0) {
          tempMap.put(values[i], array[i]);
        }
      }
      results.add(tempMap);
    }
    return results;
  }

  private static Integer[] copyArray(int[] array) {
    Integer[] ret = new Integer[array.length];
    for (int i = 0; i < array.length; i++) {
      ret[i] = array[i];
    }
    return ret;
  }

  /**
   * Method that calculates all variations of money withdraw
   *
   * @param values           values of banknotes
   * @param amounts          amount of banknotes
   * @param variation
   * @param amountToWithdraw amount to withdraw
   * @param position
   * @return list of variations
   */
  private static List<Integer[]> solveWithdrawProblem(int[] values, int[] amounts, int[] variation,
      int amountToWithdraw,
      int position) {
    List<Integer[]> list = new ArrayList<>();
    int value = compute(values, variation);
    if (value < amountToWithdraw) {
      for (int i = position; i < values.length; i++) {
        if (amounts[i] > variation[i]) {
          int[] newVariation = variation.clone();
          newVariation[i]++;
          List<Integer[]> newList = solveWithdrawProblem(values, amounts, newVariation,
              amountToWithdraw, i);
          if (newList != null) {
            list.addAll(newList);
          }
        }
      }
    } else if (value == amountToWithdraw) {
      list.add(copyArray(variation));
    }
    return list;
  }

  private static int compute(int[] values, int[] variation) {
    int ret = 0;
    for (int i = 0; i < variation.length; i++) {
      ret += values[i] * variation[i];
    }
    return ret;
  }

  public Card getCard(String cardNumber) {
    for (Account account : accountsList) {
      Card tempCard = account.getCard(cardNumber);
      if (tempCard != null) {
        return tempCard;
      }
    }
    return null;
  }

}
