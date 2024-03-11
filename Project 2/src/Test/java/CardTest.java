import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    @Test
    public void getGameValueTest() {
        BlackjackDealer dealer = new BlackjackDealer();
        Card card = dealer.dealSpecificCard(2,"D");
        assertEquals(card.getGameValue(),2,"Incorrect number game value of card");
        card = dealer.dealSpecificCard(12,"D");
        assertEquals(card.getGameValue(),10,"Incorrect face game value of card");
         card = dealer.dealSpecificCard(14,"D"); //ace
        assertEquals(card.getGameValue(),11,"Incorrect ace game value of card");
    }
}
