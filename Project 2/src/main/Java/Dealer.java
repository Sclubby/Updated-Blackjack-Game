import java.util.ArrayList;

public interface Dealer {
    public void generateDeck();
    // this should generate 52 cards, one for each of  13 faces and 4 suits.


    public ArrayList<Card> dealHand();
    //this will return an Arraylist of two card and leave the remainder of the deck able to be drawn later.

    public Card drawOne();
    // this will return the next card on top of the deck

    public void shuffleDeck();
    //this will return all 52 cards to the deck and shuffle their order

    public int deckSize();
    // this will return the number of cards left in the deck After a call to shuffleDeck() this should be 52.

}
