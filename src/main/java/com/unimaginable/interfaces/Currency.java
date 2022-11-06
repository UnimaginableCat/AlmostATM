package com.unimaginable.interfaces;

public interface Currency {

  public double getRate();

  public void addNewBanknote(Integer nominalValue, Integer quantity);

  public void updateBanknoteQuantity(Integer nominalValue, Integer quantity);

  public Integer getBanknoteQuantity(Integer nominalValue);

  public double getTotal();

  public double getTotalInRubles();

  public String getName();

  public Integer getMinimalNominalValue();

  public int[] getNominalValuesArray();

  public int[] getAmountsOfBanknotes();

  public void removeBanknote(Integer nominalValue, Integer quantity);
}
