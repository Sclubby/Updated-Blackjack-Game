import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

        @Test
        public void whoWonTestDraw() {
            BlackjackDealer dealer = new BlackjackDealer();
            BlackjackGameLogic logic = new BlackjackGameLogic();
            ArrayList<Card> handPlayer = dealer.dealSpecificHand(5,"D",10,"D");
            ArrayList<Card> handDealer = dealer.dealSpecificHand(5,"D",10,"D");
           assertEquals(logic.whoWon(handPlayer,handDealer),"Draw","incorrect whoWon wasn't draw");
        }
    @Test
    public void whoWonTestPlayerWin() {
        BlackjackDealer dealer = new BlackjackDealer();
        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList<Card> handPlayer = dealer.dealSpecificHand(6,"D",10,"D");
        ArrayList<Card> handDealer = dealer.dealSpecificHand(5,"D",10,"D");
        assertTrue(logic.whoWon(handPlayer,handDealer).contains("Player Won"),"Player should win but doesnt in whoWon function");
    }
    @Test
    public void whoWonTestDealerWins() {
        BlackjackDealer dealer = new BlackjackDealer();
        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList<Card> handPlayer = dealer.dealSpecificHand(5,"D",10,"D");
        ArrayList<Card> handDealer = dealer.dealSpecificHand(6,"D",10,"D");
        assertTrue(logic.whoWon(handPlayer,handDealer).contains("Dealer Won"),"Dealer should win but doesnt in whoWon function");
    }
    @Test
    public void HandTotalBasicTest() {
        BlackjackDealer dealer = new BlackjackDealer();
        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList<Card> hand = dealer.dealSpecificHand(5,"D",10,"D");
        assertEquals(logic.handTotal(hand), 15,"Incorrect size of hand with number cards");
        hand.add(dealer.dealSpecificCard(10,"C"));
        assertEquals(logic.handTotal(hand), 25,"Incorrect size of hand with adding new cards");
        hand.clear();
        hand = dealer.dealSpecificHand(12,"D",13,"D"); //both face cards
        assertEquals(logic.handTotal(hand), 20,"Incorrect size of hand with face cards");
    }
    @Test
    public void HandTotalWithAceTest() {
        BlackjackDealer dealer = new BlackjackDealer();
        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList<Card> hand = dealer.dealSpecificHand(5,"D",14,"D"); //ace is 14
        assertEquals(logic.handTotal(hand), 16,"Incorrect size of hand with ace card");
        hand.clear();
        hand = dealer.dealSpecificHand(14,"D",14,"S"); //both face cards
        assertEquals(logic.handTotal(hand), 12,"Incorrect size of hand with multiple ace cards");
       hand.add(dealer.dealSpecificCard(14,"H"));
        assertEquals(logic.handTotal(hand), 13,"Incorrect size of hand with adding ace cards");
    }
    @Test
    public void evaluateBankerDrawTest() {
        BlackjackDealer dealer = new BlackjackDealer();
        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList<Card> hand = dealer.dealSpecificHand(5,"D",9,"D"); //ace is 14
        assertTrue(logic.evaluateBankerDraw(hand),"Incorrect stay for dealer when dealer should hit (less than 16)");
        hand.add(dealer.dealSpecificCard(2,"D"));
        assertTrue(logic.evaluateBankerDraw(hand),"Incorrect stay for dealer when dealer should hit (equal to 16)");
        hand.add(dealer.dealSpecificCard(2,"H"));
        assertFalse(logic.evaluateBankerDraw(hand),"Incorrect hit for dealer when dealer should stay (over 16)");
    }
}