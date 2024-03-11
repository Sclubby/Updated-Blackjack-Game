import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void HitTest() {
        BlackjackDealer dealer = BlackjackGame.theDealer;
        dealer.generateDeck();
       assertFalse(BlackjackGame.hit(),"Incorrect boolean return of hit function (false)");
        assertEquals(BlackjackGame.playerHand.size(),1,"Inccorect size of player hand");
        BlackjackGame.playerHand.clear();
        BlackjackGame.playerHand = dealer.dealSpecificHand(12,"D",12,"H");
        BlackjackGame.playerHand.add(dealer.dealSpecificCard(12,"C"));
        assertTrue(BlackjackGame.hit(),"Incorrect boolean return of hit function (true)");
    }
    @Test
    public void dealerHitTest() {
        BlackjackGame.bankerHand.clear();
        BlackjackDealer dealer = BlackjackGame.theDealer;
        dealer.generateDeck();
        assertTrue(BlackjackGame.dealerHit(),"Incorrect boolean return of dealerhit function (false)");
        assertEquals(BlackjackGame.bankerHand.size(),1,"Incorrect size of banker hand");
        BlackjackGame.bankerHand.clear();
        BlackjackGame.bankerHand = dealer.dealSpecificHand(12,"D",12,"H");
        BlackjackGame.bankerHand.add(dealer.dealSpecificCard(12,"C"));
        assertFalse(BlackjackGame.dealerHit(),"Incorrect boolean return of dealerhit function (true)");
    }
    @Test
    public void instantBlackjackTest() {
        BlackjackDealer dealer = BlackjackGame.theDealer;
        dealer.generateDeck();
        BlackjackGame.playerHand = dealer.dealSpecificHand(10,"D",14,"H");
        assertTrue(BlackjackGame.instantBlackjack().contains("BLACKJACK"), "Incorrect instant blackjack");
        BlackjackGame.bankerHand = dealer.dealSpecificHand(10,"D",14,"H");
        assertTrue(BlackjackGame.instantBlackjack().contains("Draw"), "Incorrect instant blackjack draw");
        BlackjackGame.playerHand.clear();
        BlackjackGame.playerHand = dealer.dealSpecificHand(10,"D",10,"H");
        assertNull(BlackjackGame.instantBlackjack(),"Incorrect boolean return of instant blackjack not happening (null)");
    }
    @Test
    public void currentBetTest() {
        assertEquals(BlackjackGame.currentBet,0,"Incorrect default bet");
    }
    @Test
    public void totalWinningsTest() {
        BlackjackDealer dealer = BlackjackGame.theDealer;
        assertEquals(BlackjackGame.totalWinnings,0,"Incorrect default bet");
        BlackjackGame.totalWinnings = 100;
        BlackjackGame.setBet(100);
        BlackjackGame.playerHand = dealer.dealSpecificHand(5,"D",6,"D");
        BlackjackGame.bankerHand = dealer.dealSpecificHand(5,"D",6,"D");
        BlackjackGame.whoWon();  //draw
        assertEquals(BlackjackGame.totalWinnings,100,"Incorrect winnings after draw");
        BlackjackGame.setBet(100);
        BlackjackGame.playerHand.clear();
        BlackjackGame.playerHand = dealer.dealSpecificHand(5,"D",7,"D");
        BlackjackGame.whoWon(); //player wins
         assertEquals(BlackjackGame.totalWinnings,200,"Incorrect winnings after player wins");
        BlackjackGame.setBet(100);
         BlackjackGame.bankerHand.clear();
        BlackjackGame.bankerHand = dealer.dealSpecificHand(5,"D",8,"D");
        BlackjackGame.whoWon();  //dealer wins
        assertEquals(BlackjackGame.totalWinnings,100,"Incorrect winnings after dealer wins");
        BlackjackGame.setBet(100);
        BlackjackGame.playerHand.clear();
       BlackjackGame.playerHand = BlackjackGame.theDealer.dealSpecificHand(10,"D",14,"D");
       BlackjackGame.instantBlackjack(); //instant blackjack should be +150
        assertEquals(BlackjackGame.totalWinnings,250,"Incorrect winnings after instant blackjack");
        BlackjackGame.setBet(100);
        BlackjackGame.bankerHand.clear();
        BlackjackGame.bankerHand = BlackjackGame.theDealer.dealSpecificHand(10,"D",14,"D");
        BlackjackGame.instantBlackjack(); //both have instant blackjacks (draw)
        assertEquals(BlackjackGame.totalWinnings,250,"Incorrect winnings after instant blackjack for both player and dealer (draw)");

    }

}

