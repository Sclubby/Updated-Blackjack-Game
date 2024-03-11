import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealerTest {
    @Test
    public void testGenerateDeck() {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        assertEquals(dealer.deck.size(),52,"Incorrect number of cards in generated deck");
        dealer.deck.remove(0);
        dealer.deck.remove(1);
        dealer.generateDeck();
        assertEquals(dealer.deck.size(),52,"Incorrect number of cards in generated deck");
    }
    @Test
    public void testDealHandWithDeck() {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        ArrayList<Card> hand;
        hand = dealer.dealHand();
        assertEquals(dealer.deck.size(),50,"Incorrect number of cards dealt by dealHand");
        assertEquals(hand.size(),2,"Incorrect number of cards dealt by dealHand");
    }
    @Test
    public void testDealHandNoDeck() {
        BlackjackDealer dealer = new BlackjackDealer();
        ArrayList<Card> hand;
        hand = dealer.dealHand();
        assertEquals(dealer.deck.size(),50,"Incorrect number of cards dealt by dealHand");
        assertEquals(hand.size(),2,"Incorrect number of cards dealt by dealHand");
    }
}
