import java.util.*;
public class Card
{
  private ArrayList<String> HandDeckCheck = new ArrayList<String>(List.of(
  "AH","2H","3H","4H","5H","6H","7H","8H","9H","TH","JH","QH","KH",
  "AD","2D","3D","4D","5D","6D","7D","8D","9D","TD","JD","QD","KD",
  "AC","2C","3C","4C","5C","6C","7C","8C","9C","TC","JC","QC","KC",
  "AS","2S","3S","4S","5S","6S","7S","8S","9S","TS","JS","QS","KS"));

  private ArrayList<String> cards = new ArrayList<String>(List.of(
  "AH","2H","3H","4H","5H","6H","7H","8H","9H","TH","JH","QH","KH",
  "AD","2D","3D","4D","5D","6D","7D","8D","9D","TD","JD","QD","KD",
  "AC","2C","3C","4C","5C","6C","7C","8C","9C","TC","JC","QC","KC",
  "AS","2S","3S","4S","5S","6S","7S","8S","9S","TS","JS","QS","KS"));
  private int numOfCardInDeck = 52;
  private ArrayList<String> playerCards = new ArrayList<String>();


  public Card(){}
  public ArrayList<String> getplayerCards()
  {
    return playerCards;
  }
  public ArrayList<String> getDeck()
  {
    return cards;
  }
  public ArrayList<String> dealCard(int numOfCardsToDeal)
  {
    ArrayList<String> pCards = new ArrayList<String>();

    for(int i = 0; i < numOfCardsToDeal;i++)
    {
      Random ran = new Random();
      int num = ran.nextInt(numOfCardInDeck-1);
      pCards.add(cards.get(num));
      cards.remove(num);
      numOfCardInDeck = numOfCardInDeck - 1;
    }
    return pCards;
  }
  public void resetDeck()
  {
    cards = new ArrayList<String>(List.of(
    "AH","2H","3H","4H","5H","6H","7H","8H","9H","TH","JH","QH","KH",
    "AD","2D","3D","4D","5D","6D","7D","8D","9D","TD","JD","QD","KD",
    "AC","2C","3C","4C","5C","6C","7C","8C","9C","TC","JC","QC","KC",
    "AS","2S","3S","4S","5S","6S","7S","8S","9S","TS","JS","QS","KS"));
    playerCards = new ArrayList<String>();
    numOfCardInDeck = 52;
    System.out.println(cards);
    System.out.println(playerCards);
  }
  public ArrayList<String> getCardImageName(ArrayList<String> A)
  {
    ArrayList<String> playerCardsImages = new ArrayList<String>();
    for(int i = 0; i < A.size();i++)
    {
      String temp = A.get(i)+".png";
      playerCardsImages.add(temp);
    }
    return playerCardsImages;
  }
  public boolean isRoyalFlush(ArrayList<String> hand)
  {
    ArrayList<String> temp = new ArrayList<String>();

    boolean check = false;
    ArrayList<String> SpadeRoyalFlush = new ArrayList<String>(List.of("TS","JS","QS","KS","AS"));
    ArrayList<String> ClubRoyalFlush = new ArrayList<String>(List.of("TC","JC","QC","KC","AC"));
    ArrayList<String> DiamondRoyalFlush = new ArrayList<String>(List.of("TD","JD","QD","KD","AD"));
    ArrayList<String> HeartRoyalFlush = new ArrayList<String>(List.of("TH","JH","QH","KH","AH"));
    Collections.sort(SpadeRoyalFlush);
    Collections.sort(ClubRoyalFlush );
    Collections.sort(DiamondRoyalFlush);
    Collections.sort(HeartRoyalFlush);


    for(int i = 0; i < hand.size(); i++)
    {
      temp.add(hand.get(i));
    }
    temp.retainAll(SpadeRoyalFlush);
    Collections.sort(temp);

    if (temp.equals(SpadeRoyalFlush))
    {
      check = true;
      return check;
    }

    temp.clear();
    for(int i = 0; i < hand.size(); i++)
    {
      temp.add(hand.get(i));
    }
    temp.retainAll(ClubRoyalFlush);
    Collections.sort(temp);
    if (temp.equals(ClubRoyalFlush))
    {
      check = true;
      return check;
    }
    temp.clear();
    for(int i = 0; i < hand.size(); i++)
    {
      temp.add(hand.get(i));
    }
    temp.retainAll(DiamondRoyalFlush);
    Collections.sort(temp);

    if (temp.equals(DiamondRoyalFlush))
    {
      check = true;
      return check;
    }
    temp.clear();
    for(int i = 0; i < hand.size(); i++)
    {
      temp.add(hand.get(i));
    }
    temp.retainAll(HeartRoyalFlush);
    Collections.sort(temp);

    if (temp.equals(HeartRoyalFlush))
    {
      check = true;
      return check;
    }

    return check;
  }
  public boolean isStraightFlush(ArrayList<String> hand)
  {
    ArrayList<String> temp = new ArrayList<String>();
    Collections.sort(hand);
    boolean check = false;
    for(int i = 0; i <= 8; i++)
    {
      ArrayList<String> flush = new ArrayList<String>();
      flush.add(HandDeckCheck.get(i));
      flush.add(HandDeckCheck.get(i+1));
      flush.add(HandDeckCheck.get(i+2));
      flush.add(HandDeckCheck.get(i+3));
      flush.add(HandDeckCheck.get(i+4));
      Collections.sort(flush);
      for(int j = 0; j < hand.size();j++)
      {
        temp.add(hand.get(j));
      }
      temp.retainAll(flush);
      if(temp.equals(flush))
      {
        check = true;
        return check;
      }
      temp.clear();
    }
    for(int i = 13; i <= 21; i++)
    {
      ArrayList<String> flush = new ArrayList<String>();
      flush.add(HandDeckCheck.get(i));
      flush.add(HandDeckCheck.get(i+1));
      flush.add(HandDeckCheck.get(i+2));
      flush.add(HandDeckCheck.get(i+3));
      flush.add(HandDeckCheck.get(i+4));
      Collections.sort(flush);
      for(int j = 0; j < hand.size();j++)
      {
        temp.add(hand.get(j));
      }
      temp.retainAll(flush);
      if(temp.equals(flush))
      {
        check = true;
        return check;
      }
      temp.clear();
    }
    for(int i = 26; i <= 34; i++)
    {
      ArrayList<String> flush = new ArrayList<String>();
      flush.add(HandDeckCheck.get(i));
      flush.add(HandDeckCheck.get(i+1));
      flush.add(HandDeckCheck.get(i+2));
      flush.add(HandDeckCheck.get(i+3));
      flush.add(HandDeckCheck.get(i+4));
      Collections.sort(flush);
      for(int j = 0; j < hand.size();j++)
      {
        temp.add(hand.get(j));
      }
      temp.retainAll(flush);
      if(temp.equals(flush))
      {
        check = true;
        return check;
      }
      temp.clear();
    }
    for(int i = 39; i <= 47; i++)
    {
      ArrayList<String> flush = new ArrayList<String>();
      flush.add(HandDeckCheck.get(i));
      flush.add(HandDeckCheck.get(i+1));
      flush.add(HandDeckCheck.get(i+2));
      flush.add(HandDeckCheck.get(i+3));
      flush.add(HandDeckCheck.get(i+4));
      Collections.sort(flush);
      for(int j = 0; j < hand.size();j++)
      {
        temp.add(hand.get(j));
      }
      temp.retainAll(flush);
      if(temp.equals(flush))
      {
        check = true;
        return check;
      }
      temp.clear();
    }
    return check;
  }
  public boolean isFourOfAKind(ArrayList<String> hand)
  {
    boolean check = false;
    for(int i = 0; i < 13; i++)
    {
        ArrayList<String> FourOfAKind = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();

        FourOfAKind.add(HandDeckCheck.get(i));
        FourOfAKind.add(HandDeckCheck.get(i+13));
        FourOfAKind.add(HandDeckCheck.get(i+26));
        FourOfAKind.add(HandDeckCheck.get(i+39));
        for(int j = 0; j < hand.size();j++)
        {
          temp.add(hand.get(j));
        }
        temp.retainAll(FourOfAKind);
        Collections.sort(hand);
        Collections.sort(FourOfAKind);
        if(temp.equals(FourOfAKind))
        {
          check = true;
          return check;
        }
    }
    return check;
  }

  public boolean isFlush(ArrayList<String> hand)
  {
    boolean check = false;
    int counter = 0;
    for(int i = 0; i < hand.size();i++)
    {
      String temp = hand.get(i);
      if(temp.charAt(1)=='H'){ counter++;}
      if(counter >= 5)
      {
        check = true;
        return check;
      }
    }
    counter = 0;
    for(int i = 0; i < hand.size();i++)
    {
      String temp = hand.get(i);
      if(temp.charAt(1)=='C'){ counter++;}
      if(counter >= 5)
      {
        check = true;
        return check;
      }
    }
    counter = 0;
    for(int i = 0; i < hand.size();i++)
    {
      String temp = hand.get(i);
      if(temp.charAt(1)=='D'){ counter++;}
      if(counter >= 5)
      {
        check = true;
        return check;
      }
    }
    counter = 0;
    for(int i = 0; i < hand.size();i++)
    {
      String temp = hand.get(i);
      if(temp.charAt(1)=='S'){ counter++;}
      if(counter >= 5)
      {
        check = true;
        return check;
      }
    }

    return check;
  }
  public boolean isStraight(ArrayList<String> hand)
  {
    ArrayList<String> straight = new ArrayList<String>(List.of("2","3","4","5","6","7","8","9","10","J","Q","K","A"));
    ArrayList<String> hand2 = new ArrayList<String>();
    ArrayList<String> temp = new ArrayList<String>();

    boolean check = false;
    int index = 0;

    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < hand.size();j++)
      {
        hand2.add(Character.toString(hand.get(j).charAt(0)));
      }
      temp.add(straight.get(i));
      temp.add(straight.get(i+1));
      temp.add(straight.get(i+2));
      temp.add(straight.get(i+3));
      temp.add(straight.get(i+4));

      hand2.retainAll(temp);
      if(hand2.equals(temp))
      {
        check = true;
        return check;
      }
      temp.clear();
      hand2.clear();
    }
    return check;
  }

  public boolean isThreeOfAKind(ArrayList<String> hand)
  {
    boolean check = false;
    ArrayList<Character> hand2 = new ArrayList<Character>();
    for(int i = 0; i < hand.size();i++)
    {
      hand2.add(hand.get(i).charAt(0));
    }

    for(int i = 0; i < hand2.size(); i++ )
    {
      int count = 0;
      for(int j = 0; j < hand2.size(); j++)
      {
        if(hand2.get(i) == hand2.get(j))
        {
          count++;
        }
        if(count == 3)
        {
          check = true;
          return check;
        }
      }
    }
    return check;
  }

  public boolean isTwoOfAKind(ArrayList<String> hand)
  {
    boolean check = false;
    ArrayList<Character> hand2 = new ArrayList<Character>();
    for(int i = 0; i < hand.size();i++)
    {
      hand2.add(hand.get(i).charAt(0));
    }

    for(int i = 0; i < hand2.size(); i++ )
    {
      int count = 0;
      for(int j = 0; j < hand2.size(); j++)
      {
        if(hand2.get(i) == hand2.get(j))
        {
          count++;
        }
        if(count == 2)
        {
          check = true;
          return check;
        }
      }
    }
    return check;
  }

  public int isHighCard(ArrayList<String> hand)
  {
    int value;
    char temp;
    int index;
    ArrayList<Character> cardLevel = new ArrayList<Character>(List.of('2','3','4','5','6','7','8','9','T','J','Q','K','A'));
    ArrayList<Integer> cardvalue = new ArrayList<Integer>(List.of(1,2,3,4,5,6,7,8,9,10,11,12,13));
    ArrayList<Character> hand2 = new ArrayList<Character>();
    for(int i = 0; i < hand.size();i++)
    {
      hand2.add(hand.get(i).charAt(0));
    }
    temp = hand2.get(0);
    index = cardLevel.indexOf(temp);
    value = cardvalue.get(index);
    char temp2;
    int index2;
    int value2;

    for(int i = 1; i < hand.size();i++)
    {
      temp2 = hand2.get(i);
      index2 = cardLevel.indexOf(temp2);
      value2 = cardvalue.get(index2);
      if(value2 > value)
      {
        value = value2;
      }
    }
    System.out.println("Highest card in hand is: " + cardLevel.get(cardvalue.indexOf(value)));
    return value;
  }

  public boolean isTwoPairs(ArrayList<String> hand)
  {
    boolean check = false;
    ArrayList<Character> hand2 = new ArrayList<Character>();

    for(int i = 0; i < hand.size();i++)
    {
      hand2.add(hand.get(i).charAt(0));
    }

    for(int i = 0; i < hand2.size(); i++ )
    {
      ArrayList<String> hand3 = new ArrayList<String>();
      int count = 0;
      for(int j = 0; j < hand2.size(); j++)
      {
        if(hand2.get(i) == hand2.get(j))
        {
          count++;
        }
        if(count == 2)
        {
          for(int k = 0; k < hand2.size(); k++)
          {
            if(hand2.get(i) != hand2.get(k))
            {
              hand3.add(Character.toString(hand2.get(k)));
            }
          }
          check = isTwoOfAKind(hand3);
          if(check == true)
          {
            return check;
          }
          count = 0;
        }
      }
    }
    return check;
  }
  public boolean isFullHouse(ArrayList<String> hand)
  {
    boolean check = false;
    ArrayList<Character> hand2 = new ArrayList<Character>();
    for(int i = 0; i < hand.size();i++)
    {
      hand2.add(hand.get(i).charAt(0));
    }

    for(int i = 0; i < hand2.size(); i++ )
    {
      int count = 0;
      ArrayList<String> hand3 = new ArrayList<String>();
      for(int j = 0; j < hand2.size(); j++)
      {
        if(hand2.get(i) == hand2.get(j))
        {
          count++;
        }
        if(count == 3)
        {
          for(int k = 0; k < hand2.size(); k++)
          {
            if(hand2.get(i) != hand2.get(k))
            {
              hand3.add(hand.get(k));
            }
          }
          check = isTwoOfAKind(hand3);
          if(check == true)
          {
            return check;
          }
          count = 0;
        }
      }
    }
    return check;
  }
  public int handValue(ArrayList<String> hand)
  {
    int value = 0;
    if(isRoyalFlush(hand))
    {
      value = 10;
      return value;
    }
    if(isStraightFlush(hand))
    {
      value = 9;
      return value;
    }
    if(isFourOfAKind(hand))
    {
      value = 8;
      return value;
    }
    if(isFullHouse(hand))
    {
      value = 7;
      return value;
    }
    if(isFlush(hand))
    {
      value = 6;
      return value;
    }
    if(isStraight(hand))
    {
      value = 5;
      return value;
    }
    if(isThreeOfAKind(hand))
    {
      value = 4;
      return value;
    }
    if(isTwoPairs(hand))
    {
      value = 3;
      return value;
    }
    if(isTwoOfAKind(hand))
    {
      value = 2;
      return value;
    }
    else
    {
      value = 1;
      return value;
    }
  }




  public static void main(String [] args)
    {
      Card card2 = new Card();
      int playerNum = 4;

      ArrayList<String> p1 = new ArrayList<String>();
      p1 = card2.dealCard(2);
      ArrayList<String> p2 = new ArrayList<String>();
      p2 = card2.dealCard(2);
      ArrayList<String> p3 = new ArrayList<String>();
      p3 = card2.dealCard(2);
      ArrayList<String> p4 = new ArrayList<String>();
      p4 = card2.dealCard(2);
      ArrayList<String> dealer = new ArrayList<String>();
      dealer = card2.dealCard(5);

      p1.addAll(dealer);
      System.out.print(p1 + ": ");
      System.out.print(card2.handValue(p1));
      System.out.println();

      p2.addAll(dealer);
      System.out.print(p2 + ": ");
      System.out.print(card2.handValue(p2));
      System.out.println();

      p3.addAll(dealer);
      System.out.print(p3 + ": ");
      System.out.print(card2.handValue(p3));
      System.out.println();

      p4.addAll(dealer);
      System.out.print(p4 + ": ");
      System.out.print(card2.handValue(p4));
      System.out.println();

    }
}
