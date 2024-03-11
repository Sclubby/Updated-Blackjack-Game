import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer implements Dealer {

    ArrayList<Card> deck = new ArrayList<>();

    @Override
    public void generateDeck() {  //generates whole deck and adds to the deck arraylist
        deck.clear();  //clears deck if it already has things in it
        String[] suits = {"C","D","H","S"};
        for (String suit : suits) {
            for (int value = 2; value < 15; value++) {
                Card card = new Card(suit,value);
                deck.add(card);
            }
        }
        if (deck.size() != 52) { throw new RuntimeException("deck isn't 52 cards on creation of new deck"); }
    }

    @Override
    public ArrayList<Card> dealHand() {  //deals two cards to a hand and returns an arraylist
       ArrayList<Card> hand = new ArrayList<>();
       if (deck.size() < 2) { generateDeck(); shuffleDeck(); }  //if deck is low make new deck
       hand.add(drawOne());
       hand.add(drawOne());
       return hand;
    }

    @Override
    public Card drawOne() {
        return  deck.remove(0);
    }

    @Override
    public void shuffleDeck() {
       Collections.shuffle(deck);
    }

    @Override
    public int deckSize() {
        return deck.size();
    }


    //ALL FUNCTIONS USED FOR TESTING


    public ArrayList<Card> dealSpecificHand(int value1,String suit1,int value2, String suit2) {  //for testing purposes
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(suit1,value1));
        hand.add(new Card(suit2,value2));
        return hand;
    }
    public Card dealSpecificCard(int value,String suit) {  //for testing purposes
       return new Card(suit,value);
    }

    public void printDeck() {  //for testing purposes
        for (Card x : deck) {
            System.out.println(x.toString());
        }
    }
}