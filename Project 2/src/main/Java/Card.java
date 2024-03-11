public class Card {
    String suit;
    int value;
    boolean hidden = false;

    Card(String theSuit, int theValue) {
        suit = theSuit;
        value = theValue;
    }

    public int getGameValue() {  //returns value of card based off of game rules
          if (value == 14) { return 11; } //ace
          if (value < 10) {return value; } //number cards
          return 10;  //face cards
    }

    public String toString() {
        return this.value + this.suit;
    }
}