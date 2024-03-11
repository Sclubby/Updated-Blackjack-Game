import java.util.ArrayList;

public interface Logic {

    public String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand);
    //given two hands this should return either player or dealer or push dependin on who wins.

    public int handTotal(ArrayList<Card> hand);
    //this should return the total value of all cards in the hand.

    public boolean evaluateBankerDraw(ArrayList<Card> hand);
    // this method should return true if the dealer should draw another card, i.e. if the value is 16 or less
}
