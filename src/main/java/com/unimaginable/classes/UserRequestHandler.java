package com.unimaginable.classes;

import com.unimaginable.interfaces.Currency;
import com.unimaginable.services.credschecker.CardNumberChecker;
import com.unimaginable.services.credschecker.CardNumberLengthChecker;
import com.unimaginable.services.credschecker.CredsCheckerOperationChain;
import com.unimaginable.services.credschecker.CredsChecker;
import com.unimaginable.services.credschecker.PasswordChecker;
import com.unimaginable.services.credschecker.PasswordLengthChecker;
import com.unimaginable.services.withdrawchecker.AmountMultiplyChecker;
import com.unimaginable.services.withdrawchecker.AtmBalanceChecker;
import com.unimaginable.services.withdrawchecker.UserBalanceChecker;
import com.unimaginable.services.withdrawchecker.WithdrawCheckOperationChain;
import com.unimaginable.services.withdrawchecker.WithdrawChecker;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserRequestHandler {

  private ATM atm;
  static Scanner scanner = new Scanner(System.in);
  private final String WELCOME_MESSAGE = "Welcome to our ATM!\n"
      + "What do you want to do?\n"
      + "0. Login as customer\n"
      + "1. Login as admin";
  private final String USER_LOGIN_MESSAGE = "You must enter card and password to continue!";

  private final String ADMIN_MESSAGE = "Possible actions:\n"
      + "0. Add banknotes to ATM\n"
      + "1. Exit\n";
  private final String USER_ACTIONS_MESSAGE = "Possible actions:\n"
      + "0. Withdraw money\n"
      + "1. Add money to card balance\n"
      + "2. Exit\n";

  public UserRequestHandler(ATM atm) {
    this.atm = atm;
    workingMode();
  }

  public UserRequestHandler() {
    atm = new ATM();
    workingMode();
  }

  private void adminAddingBanknotesMode() {
    List<Currency> currencyList = atm.getCurrencyList();
    System.out.print("Choose currency:\n");

    printCurrencyNames(currencyList);

    int currencyChoice = inputNumberAndCheck(currencyList.size());
    Currency chosenCurrency = currencyList.get(currencyChoice);

    System.out.println("Enter nominal value:");
    Integer nominalValue = scanner.nextInt();

    System.out.println("Enter quantity:");
    Integer quantity = scanner.nextInt();

    atm.addMoneyToATM(nominalValue, quantity, chosenCurrency);
    System.out.println("Succesfully added money to ATM!");
  }

  private void adminMode() {
    boolean working = true;
    while (working) {
      System.out.println(ADMIN_MESSAGE);
      int adminChoice = inputNumberAndCheck(2);
      switch (adminChoice) {
        case 0: {
          adminAddingBanknotesMode();
          break;
        }
        case 1: {
          working = false;
          break;
        }
      }
    }

  }

  private void workingMode() {
    while (true) {
      System.out.println(WELCOME_MESSAGE);
      int choice = inputNumberAndCheck(2);
      switch (choice) {
        case 0: {
          userCredsInputMode();
          break;
        }
        case 1: {
          adminMode();
          break;
        }
        default: {
          System.out.println("Wrong choice");
          break;
        }
      }
    }
  }

  private void printCurrencyNames(List<Currency> currencyNamesList) {
    int count = 0;
    for (Currency currency : currencyNamesList) {
      System.out.printf("%d. %s\n", count, currency.getName());
      count++;
    }
  }

  private void printCurrencyInfo(Currency currency) {
    System.out.printf("Chosen currency: %s\n", currency.getName());
    System.out.printf("Total %s in ATM: %.2f\n", currency.getName(), currency.getTotal());
  }

  private int inputNumberAndCheck(int numOfChoices) {
    int number;
    int numOfAttempts = 0;
    do {
      if (numOfAttempts != 0) {
        System.out.println("Enter valid value");
      }
      while (!scanner.hasNextInt()) {
        System.out.println("That's not a number!");
        scanner.next();
      }
      number = scanner.nextInt();
      numOfAttempts++;
    } while (number < 0 || number > numOfChoices - 1);
    return number;
  }

  private void printPossibleWithdrawings(List<Map<Integer, Integer>> possibleWithdrawings) {
    int count = 0;
    for (Map<Integer, Integer> variant : possibleWithdrawings) {
      System.out.printf("%d. %s\n", count, variant);
      count++;
    }
  }

  private void depositMoney(String cardNumber) {
    double userBalance = atm.getCardBalance(cardNumber);
    List<Currency> currencyList = atm.getCurrencyList();
    System.out.printf("Your balance: %.2f\n", userBalance);
    System.out.print("Choose currency:\n");

    printCurrencyNames(currencyList);

    int currencyChoice = inputNumberAndCheck(currencyList.size());
    Currency chosenCurrency = currencyList.get(currencyChoice);

    System.out.println("Enter nominal value:");
    Integer nominalValue = scanner.nextInt();

    System.out.println("Enter quantity:");
    Integer quantity = scanner.nextInt();

    atm.addMoneyToATM(nominalValue, quantity, chosenCurrency);
    atm.addBalanceToCard(cardNumber, nominalValue * quantity, chosenCurrency);
    System.out.println("Succesfully added money to balance!");
  }

  private void withdrawMoney(String cardNumber) {
    double totalCurrency = atm.getTotalATMBalanceInRubles();
    double userBalance = atm.getCardBalance(cardNumber);
    List<Currency> currencyList = atm.getCurrencyList();

    System.out.printf("Total currency in ATM(in rubles): %.2f\n", totalCurrency);
    System.out.printf("Your balance: %.2f\n", userBalance);
    System.out.print("Choose currency:\n");

    printCurrencyNames(currencyList);

    int currencyChoice = inputNumberAndCheck(currencyList.size());
    Currency chosenCurrency = currencyList.get(currencyChoice);
    printCurrencyInfo(chosenCurrency);

    System.out.printf("Enter a multiple of %d amount to withdraw:\n",
        chosenCurrency.getMinimalNominalValue());

    WithdrawCheckOperationChain withdrawCheckOperationChain = WithdrawCheckOperationChain.link(
        new AtmBalanceChecker(atm, chosenCurrency),
        new AmountMultiplyChecker(chosenCurrency.getMinimalNominalValue()),
        new UserBalanceChecker(atm, cardNumber)
    );
    WithdrawChecker withdrawChecker = new WithdrawChecker();
    withdrawChecker.setOperationChain(withdrawCheckOperationChain);

    int amount = scanner.nextInt();
    boolean withdrawCheck = withdrawChecker.checkOperationAvailability(amount);

    if (withdrawCheck) {
      List<Map<Integer, Integer>> possibleWithdrawings = atm.calculatePossibleWithdrawing(
          chosenCurrency, amount);
      if (possibleWithdrawings.size() == 0) {
        System.out.println("Cant withdraw this amount of money, try another amount");
        return;
      }

      printPossibleWithdrawings(possibleWithdrawings);
      System.out.println("Choose one of the options to withdraw:\n");
      int withdrawChoice = inputNumberAndCheck(possibleWithdrawings.size());

      atm.removeMoneyFromATM(possibleWithdrawings.get(withdrawChoice), chosenCurrency);
      atm.removeBalanceFromCard(cardNumber, amount, chosenCurrency);

      System.out.println("Succesfully withdrawn money!");
    }

  }

  private void userActionsMode(String cardNumber) {
    boolean working = true;
    while (working) {
      System.out.print(USER_ACTIONS_MESSAGE);
      int choice = inputNumberAndCheck(3);
      switch (choice) {
        case 0: {
          withdrawMoney(cardNumber);
          break;
        }
        case 1: {
          depositMoney(cardNumber);
          break;
        }
        case 2: {
          working = false;
          break;
        }
      }
    }
  }


  private void userCredsInputMode() {
    // Init card number checker chain
    CredsCheckerOperationChain cardNumberCheckChain = CredsCheckerOperationChain.link(
        new CardNumberLengthChecker(),
        new CardNumberChecker(atm)
    );
    CredsChecker cardNumberCheck = new CredsChecker();
    cardNumberCheck.setOperationChain(cardNumberCheckChain);

    // init card password checker chain
    CredsCheckerOperationChain passwordCheckChain = CredsCheckerOperationChain.link(
        new PasswordLengthChecker(),
        new PasswordChecker(atm)
    );
    CredsChecker passwordCheck = new CredsChecker();
    passwordCheck.setOperationChain(passwordCheckChain);

    System.out.println(USER_LOGIN_MESSAGE);
    int count = 0; // error count

    while (true) {
      if (count != 0) {
        System.out.printf("Number of attempts: %s\n", count);

      }
      if (count > 3) {
        System.out.print("You have reached limit of attempts, quitting...\n");
        break;
      }

      System.out.print("Card number:");
      String cardNumber = scanner.next();
      System.out.print("Password:");
      String password = scanner.next();

      boolean cardCheckResult = cardNumberCheck.checkOperationAvailability(cardNumber, password);
      boolean passCheckResult = passwordCheck.checkOperationAvailability(cardNumber, password);

      if (cardCheckResult && passCheckResult) {
        System.out.print("Authorization successful\n");
        System.out.printf("Hello, %s\n", atm.getCardHolderName(cardNumber));
        userActionsMode(cardNumber);
        break;
      }

      if (!cardCheckResult || !passCheckResult) {
        count++;
      }

    }
  }
}
