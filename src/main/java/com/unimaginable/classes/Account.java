package com.unimaginable.classes;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public class Account {

  @NonNull
  private final String id;
  @Getter
  @NonNull
  private String holderName;
  private List<Card> cardList;

  public Account(String id, String holderName) {
    this.id = id;
    this.holderName = holderName;
    cardList = new ArrayList<>();
  }

  public Account(String id, String holderName, List<Card> cardList) {
    this.id = id;
    this.holderName = holderName;
    this.cardList = cardList;
  }

  public boolean checkCardPresence(String cardNumber) {
    if (cardList.size() != 0) {
      for (Card card : cardList) {
        if (card.getCardNumber().equals(cardNumber)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Method that returns card
   *
   * @param cardNumber card number
   * @return Card
   */
  public Card getCard(String cardNumber) {
    if (cardList.size() != 0) {
      for (Card card : cardList) {
        if (card.getCardNumber().equals(cardNumber)) {
          return card;
        }
      }
    }
    return null;
  }


  public void addCard(Card card) {
    cardList.add(card);
  }
}
