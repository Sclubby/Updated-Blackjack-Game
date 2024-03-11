import java.util.ArrayList;

public class BlackjackGameLogic implements Logic {

    @Override
    public String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand) {  //returns who won between dealer and player
        int playerNum = handTotal(playerHand1);
        int dealerNum = handTotal(dealerHand);
        if (dealerNum > playerNum) { return "Dealer Won - $" + BlackjackGame.currentBet; }
        if (dealerNum == playerNum) { return "Draw"; }
        return "Player Won + $" + BlackjackGame.currentBet;
    }

    @Override
    public int handTotal(ArrayList<Card> hand) { //returns number of cards in hand
        int value = 0;
        int numOfAces = 0;  //if the player has an ace = true
        for (Card card : hand) {
            if (card.value == 14) { numOfAces++;}
            value += card.getGameValue();
        }
        if (value > 21) {  //if over 21 change ace from 11 to 1
            for (int i = 0; i < numOfAces; i++) { //do this multiple times if many aces and still over 21
                value -= 10;
                if (value <= 21) { break; }
            }
        }
        return value;
    }

    @Override
    public boolean evaluateBankerDraw(ArrayList<Card> hand) {  //determines if the dealer should hit or stay
        if (handTotal(hand) <= 16) { return true; } //true = hit
        return false;  //false = stay
    }
}
