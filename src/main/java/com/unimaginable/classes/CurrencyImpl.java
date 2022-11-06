package com.unimaginable.classes;

import com.unimaginable.interfaces.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.Getter;


public class CurrencyImpl implements Currency {

  private double rate; // The exchange rate in relation to the ruble, that is, how much is 1 unit of currency in rubles
  @Getter
  private String name;

  private double totalValue; // The total value of the currency in the ATM (in foreign currency, not rubles)

  private double totalValueInRubles;
  private Map<Integer, Integer> banknotes; // Map, where the key is the denomination of the banknote, and the value is the number of banknotes in the ATM

  public CurrencyImpl(String name, double rate) {
    this.name = name;
    this.rate = rate;
    banknotes = new HashMap<>();
    totalValue = 0;
    totalValueInRubles = 0;
  }

  public int[] getNominalValuesArray() {
    int[] array = new int[banknotes.size()];
    int i = 0;
    for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
      array[i] = entry.getKey();
      i++;
    }
    return array;
  }

  @Override
  public int[] getAmountsOfBanknotes() {
    int[] array = new int[banknotes.size()];
    int i = 0;
    for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
      array[i] = entry.getValue();
      i++;
    }
    return array;
  }

  @Override
  public void removeBanknote(Integer nominalValue, Integer quantity) {
    Integer oldAmount = banknotes.get(nominalValue);
    banknotes.put(nominalValue, oldAmount - quantity);
    recalculateTotalValue();
  }

  @Override
  public double getRate() {
    return rate;
  }

  /**
   * Method that adds banknotes to atm
   *
   * @param nominalValue nominal value of a banknote
   * @param quantity     quantity of banknotes
   */
  public void addNewBanknote(Integer nominalValue, Integer quantity) {
    banknotes.put(nominalValue, quantity);
    recalculateTotalValue();
  }

  @Override
  public void updateBanknoteQuantity(Integer nominalValue, Integer quantity) {
    Integer oldQuantity = banknotes.get(nominalValue);
    if (oldQuantity != null) {
      banknotes.put(nominalValue, oldQuantity + quantity);
    } else {
      banknotes.put(nominalValue, quantity);
    }
    recalculateTotalValue();
  }

  /**
   * Method that returns banknote quantity
   *
   * @param nominalValue nominal value of banknote
   * @return quantity of banknote
   */
  public Integer getBanknoteQuantity(Integer nominalValue) {
    return banknotes.get(nominalValue);
  }

  @Override
  public double getTotal() {
    return totalValue;
  }

  @Override
  public double getTotalInRubles() {
    return totalValueInRubles;
  }

  @Override
  public Integer getMinimalNominalValue() {
    Optional<Integer> optMinimalNominalValue
        = banknotes.entrySet().stream()
        .filter(p -> p.getValue() > 0)
        .findFirst()
        .map(Entry::getKey);

    Integer minimalNominalValue = 0;
    if (optMinimalNominalValue.isPresent()) {
      minimalNominalValue = optMinimalNominalValue.get();
    }
    for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
      if (entry.getKey() < minimalNominalValue) {
        minimalNominalValue = entry.getKey();
      }
    }
    return minimalNominalValue;
  }


  /**
   * Method that recalculates total value
   */
  private void recalculateTotalValue() {
    double tempValueInCurrency = 0;
    double tempValueInRubles = 0;
    for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
      tempValueInCurrency += entry.getKey() * entry.getValue();
      tempValueInRubles += entry.getKey() * entry.getValue() * rate;

    }
    totalValue = tempValueInCurrency;
    totalValueInRubles = tempValueInRubles;
  }


}
