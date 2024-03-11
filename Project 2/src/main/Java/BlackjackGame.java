import java.util.ArrayList;

public class BlackjackGame {
    static ArrayList<Card> playerHand = new ArrayList<>();
    static ArrayList<Card> bankerHand = new ArrayList<>();
     static BlackjackDealer theDealer = new BlackjackDealer();
      static BlackjackGameLogic gameLogic = new BlackjackGameLogic();

   static double currentBet = 0; // the amount currently bet from the user
   static double totalWinnings = 0;  //the total amount of value that the user has.

    public static void main(String[] args) {
        initialSetUp();
        GameDisplay.launchDisplay();
    }

    private static void initialSetUp() {  //sets up hands and deck for the start of the round Returns true if blackjack is hit
        theDealer.generateDeck();
        theDealer.shuffleDeck();

        playerHand = theDealer.dealHand();
      // playerHand = theDealer.dealSpecificHand(14,"D",13,"D");  //for testing purposes
     //   bankerHand = theDealer.dealSpecificHand(14,"D",13,"D");  //for testing purposes
        bankerHand = theDealer.dealHand();
       bankerHand.get(1).hidden = true;
    }

    public static boolean hit() {  //gives player another card returns true if bust
       playerHand.add(theDealer.drawOne());
        if (gameLogic.handTotal(playerHand) > 21) {
            return true;
        }
        return false;
    }
    public static boolean dealerHit() { //gives dealer another card and returns true if can go again
        if (gameLogic.evaluateBankerDraw(bankerHand)) {
            bankerHand.add(theDealer.drawOne());
            return true;
        }
        return false;
    }

    public static void setBet(int betAmount) {
        BlackjackGame.currentBet = betAmount;
        BlackjackGame.totalWinnings -= betAmount;
    }

    public static void gameReset() {
        playerHand.clear();
        bankerHand.clear();
        currentBet = 0;
       initialSetUp();
    }

    public static String whoWon() {
        if (gameLogic.handTotal(bankerHand) > 21) {totalWinnings += (currentBet * 2); return "Bank Bust + $" + currentBet; } //if dealer busts
        String winner = gameLogic.whoWon(playerHand,bankerHand);
        if (winner.contains("Player")) { totalWinnings += (currentBet * 2); }  //add to winnings if player wins
        if (winner.contains("Draw")) { totalWinnings += currentBet; } //give money back on draw
        return winner;
    }

    public static String instantBlackjack() {
        if (gameLogic.handTotal(playerHand) == 21) {
            bankerHand.get(1).hidden = false;
            if(gameLogic.handTotal(bankerHand) == 21) { totalWinnings += currentBet; return "Draw"; }  //if both hand blackjack initially return draw
            totalWinnings += ((currentBet * 1.5) + currentBet);
            currentBet *= 1.5;
            return "BLACKJACK + $" + BlackjackGame.currentBet;  //if the player has an instant blackjack increase winning and end game
        }
        return null;
    }
}
