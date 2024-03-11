import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class GameDisplay extends Application {
   // these are global because they are constantly updated
     Text finalText = new Text(""); //text on end of game screen (changes depending on game outcome)
     Text moneyTotal = new Text();  //display total money held by player
     Text bettingText = new Text("Bet amount: 0"); //display of bet
      ArrayList<ImageView> playerCards = new ArrayList<>(); ArrayList<ImageView> dealerCards = new ArrayList<>();  //holds card images that are displayed
     HBox dealerElements = new HBox();  HBox playerElements = new HBox();  //holds cards that are displayed
      Button hitButton = new Button("HIT");  Button stayButton = new Button("STAY");
     Button StartNewGame = new Button("Start New Game");  Button ResetButton = new Button("Continue Gambling Journey");

    Button startButton = new Button("START");  //start button if pressed changes screen, but first needs betting amount from user
    TextField totalMoney = new TextField("Press enter");  //textfield that takes user input for total money

   Stage bettingWindow = new Stage();  //small window that shows up to insert betting
    HashMap<String,Scene> sceneMap = new HashMap<>();  //map of all scenes

    public static void launchDisplay() {  //runs start()
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {  //creates window and shows start screen
        //creates betting window
        bettingWindow.setTitle("betting Window");
        bettingWindow.setMaxHeight(100); bettingWindow.setMaxWidth(300);

        //creates pane for main window
        primaryStage.setTitle("Blackjack");

        //creates start, final and betting screen and displays start screen
        sceneMap.put("StartScreen", createStartScreen(primaryStage));
        bettingWindow.setScene(createBettingScreen(bettingWindow));
        sceneMap.put("GameScreen", createGameScreen(primaryStage)); //create game screen with data
        primaryStage.setScene(sceneMap.get("StartScreen"));
        primaryStage.show();
    }

    private Scene createBettingScreen(Stage bettingWindow) {  //creates element in for betting window
        Text text = new Text("Bet amount: ");
        setCommonText(text,28);
        text.setFill(Color.BLACK);

        TextField betInput = new TextField("press enter");
        betInput.setPrefHeight(30);  betInput.setPrefWidth(80);
        betInput.setOnKeyPressed(e-> {  //on enter if amount is valid returns frees start screen and closes window
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    int betAmount = parseInt(betInput.getText());
                    if (betAmount <= 0  || betAmount > BlackjackGame.totalWinnings) { //if bet is less than 0 or over amount of money user has
                        betInput.setText("Invalid input");
                    } else { //inputs bet into system and prepare game screen
                        BlackjackGame.gameReset();
                        BlackjackGame.setBet(betAmount);
                         hitButton.setVisible(true); stayButton.setVisible(true);
                         playerElements.setVisible(true); dealerElements.setVisible(true);
                        bettingWindow.hide();
                        updateGameScreen();
                        //if the player has an instant blackjack end game, if its a draw end it and show draw else continue
                        String temp = BlackjackGame.instantBlackjack();
                        if (temp != null) {
                            setFinalState(temp);
                        }
                    }
                } catch (NumberFormatException a) {
                    betInput.setText("Incorrect Format");
                }
            }
        });
        HBox components = new HBox(text,betInput);
        VBox screen = new VBox(components);
        screen.setAlignment(Pos.CENTER); screen.setPadding(new Insets(15,15,15,15));
       return new Scene(screen,300,100);

    }

    private Scene createStartScreen(Stage primaryStage) {  //creates start screen
        Text title = new Text("Blackjack");
        Text startAmountText = new Text("Starting Amount: $");

        Text errorMessage = new Text("Incorrect Format/Impossible money amount");  //error message if user input is wrong (displayed on bottom of screen)
        setCommonText(errorMessage,40);
        errorMessage.setFill(Color.RED);
        errorMessage.setVisible(false);

        //starts game after a bet is inputted
        startButton.setDisable(true);
        startButton.setOnAction(e->{
            primaryStage.setScene(sceneMap.get("GameScreen"));
            updateGameScreen();
            dealerElements.setVisible(false); playerElements.setVisible(false);
            bettingWindow.show();
        });

        //box that the user inputs the starting amount of money they want
        totalMoney.setPrefHeight(30);  totalMoney.setPrefWidth(90);
        totalMoney.setStyle("-fx-font-size: 15;"+"-fx-border-size: 20;"+"-fx-border-color: red;");

        totalMoney.setOnKeyPressed(e-> {  //on enter if amount is valid allows user to start
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    int total = parseInt(totalMoney.getText());
                    errorMessage.setVisible(false);
                    if (total <= 0) {  //if user inputs 0
                        errorMessage.setText("You need money to bet");
                        errorMessage.setVisible(true);
                    } else {  //if input is valid
                        totalMoney.setEditable(false);
                        startButton.setDisable(false);
                        BlackjackGame.totalWinnings = total;
                    }
                } catch (NumberFormatException a) {  //if input isn't a valid format
                    errorMessage.setVisible(true);
                }
            }
        });
        //title text characteristics
        setCommonText(title,50);
        setCommonText(startAmountText,50);
        title.setFont(Font.font( 100));
        title.setFill(Color.RED);

        HBox Title = new HBox(title);
        Title.setAlignment(Pos.CENTER);

        HBox MiddleText = new HBox(startAmountText,totalMoney);  //Holds text and textfield in center of screen
        MiddleText.setAlignment(Pos.CENTER_LEFT);
        MiddleText.setPadding(new Insets(30));

        VBox screen = new VBox(Title, MiddleText,startButton,errorMessage);
        screen.setAlignment(Pos.CENTER);
        screen.setBackground(Background.fill(Color.rgb(18,77,29)));
        screen.setPadding(new Insets(15,15,15,15));
        return new Scene(screen, 700, 600);
    }

    private Scene createGameScreen(Stage primaryStage) {  //creates the main game screen
        Text dealerText = new Text("Dealer cards:  ");  Text playerText = new Text("Player Cards:  ");
        setCommonText(dealerText,16);  setCommonText(playerText,16);
        dealerCards = setCardImages(BlackjackGame.bankerHand);
        playerCards = setCardImages(BlackjackGame.playerHand);

        setCommonText(moneyTotal,40);
        setCommonText(bettingText,40);

        hitButton.setPrefSize(200,100);
        hitButton.setOnAction(e->{ //hit button action
         boolean bust = BlackjackGame.hit();  //if the user busts returns true
           updateGameScreen();
           if (bust) {  //ends game and displays game end screen
               setFinalState("Player Bust\n - " + BlackjackGame.currentBet);
               if (BlackjackGame.totalWinnings == 0 ) { //player is out of money display button for full reset of game
                   finalText.setText("You're an addict. Get help");
                   setCommonText(finalText,30);
                   ResetButton.setVisible(true);
                   StartNewGame.setVisible(false);
               }
           }
        });

        stayButton.setPrefSize(200,100);
        stayButton.setOnAction(e->{ //stay button action
            BlackjackGame.bankerHand.get(1).hidden = false; //reveal hidden card
            updateGameScreen();
          while(BlackjackGame.dealerHit()) {
             updateGameScreen();
          }
          setFinalState(BlackjackGame.whoWon());
            if (BlackjackGame.totalWinnings == 0 ) { //player is out of money
                finalText.setText("You're an addict. Get help");
                setCommonText(finalText,20);
                ResetButton.setVisible(true);
                StartNewGame.setVisible(false);
            }
        });

        updateGameScreen();  //adds info to all elements

        finalText.setVisible(false); //displayed after game (changes on win condition)
        StartNewGame.setVisible(false); //displayed after game
        StartNewGame.setPrefSize(200,50);
        ResetButton.setVisible(false);
        ResetButton.setPrefSize(200,50);

        StartNewGame.setOnAction(e->{ //New Game button action
            StartNewGame.setVisible(false);
            updateGameScreen();
            bettingWindow.show();
            playerElements.setVisible(false); dealerElements.setVisible(false); //hide next games cards
            finalText.setVisible(false);
        });
        ResetButton.setOnAction(e->{ //Reset whole app to start screen
            primaryStage.setScene(sceneMap.get("StartScreen"));
            ResetButton.setVisible(false);
            finalText.setVisible(false);
            totalMoney.setEditable(true);
            startButton.setDisable(true);
            totalMoney.setText("press Enter");
             BlackjackGame.gameReset();
            updateGameScreen();
        });

        HBox buttons = new HBox(hitButton,stayButton);
        HBox endGameElements = new HBox(finalText,StartNewGame,ResetButton);
        endGameElements.setAlignment(Pos.CENTER_LEFT); endGameElements.setSpacing(25);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(30); buttons.setAlignment(Pos.CENTER);
        HBox dealerCards = new HBox(dealerText,dealerElements);
        HBox playerCards = new HBox(playerText,playerElements);
        VBox screen = new VBox(moneyTotal,dealerCards,playerCards,buttons,endGameElements,bettingText);
        screen.setSpacing(20);
        screen.setBackground(Background.fill(Color.rgb(18,77,29)));
        screen.setAlignment(Pos.CENTER); screen.setPadding(new Insets(15,15,15,15));
        return new Scene(screen, 700, 600);
    }

    //helper functions
    public void updateGameScreen() {  //updates elements on start screen
        moneyTotal.setText("Total amount of money: $" + BlackjackGame.totalWinnings);
        bettingText.setText("Bet: " + BlackjackGame.currentBet);

       playerCards = setCardImages(BlackjackGame.playerHand);
       dealerCards = setCardImages(BlackjackGame.bankerHand);
        dealerElements.getChildren().clear();
        playerElements.getChildren().clear();
        dealerElements.getChildren().addAll(dealerCards);
        playerElements.getChildren().addAll(playerCards);
        setCommonText(finalText,20);
    }

    private ArrayList<ImageView> setCardImages(ArrayList<Card> hand) {  //creates images for cards in array and displays them
        Image cardImage;
        ArrayList<ImageView> ImageList = new ArrayList<>();
        for (Card card : hand) {
            if (card.hidden) { cardImage = new Image("res/red_back.png"); } //if card is hidden display back png
            else { cardImage = new Image("res/" + card.toString() +".png"); }//default image at first
            ImageView cardView = new ImageView(cardImage);
            cardView.setFitHeight(300);
            cardView.setFitWidth(100);
            cardView.setPreserveRatio(true);
            ImageList.add(cardView);
        }
        return ImageList;
    }

    private void setCommonText(Text inputText, int fontSize) {  //sets text with basic style
        DropShadow ds = new DropShadow();ds.setOffsetY(3.0f);
        inputText.setEffect(ds);
        inputText.setFont(Font.font("Verdana", fontSize));
        inputText.setFill(Color.rgb(232,220,36));
    }

    private void setFinalState(String text) {
        updateGameScreen();
        hitButton.setVisible(false);stayButton.setVisible(false);StartNewGame.setVisible(true);
        finalText.setText(text);
        setCommonText(finalText,35);
        finalText.setVisible(true);
        if (text.contains("Draw")) { finalText.setFill(Color.DARKGRAY);}
       else if (text.contains("Dealer")) { finalText.setFill(Color.RED);}
       else if (text.contains("Player Bust")) { finalText.setFill(Color.RED);}
        else {finalText.setFill(Color.GREEN);}
    }
}
