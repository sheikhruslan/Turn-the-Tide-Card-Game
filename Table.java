import java.util.Scanner;

public class Table {
    /**
     * The total number of turns in one round.
     */
    private static final int TOTAL_TURN = 12;
    /**
     * The total number of tide cards. Totally 24.
     */
    private static final int TOTAL_TIDE_CARDS = TOTAL_TURN * 2;
    /**
     * The total number of weather cards. Totally 60.
     */
    private static final int TOTAL_WEATHER_CARD = 60;
    /**
     * The tide cards. Each card has two.
     */
    private int[] tide = new int[TOTAL_TIDE_CARDS];
    /**
     * The weather decks. We divide all 60 Weather cards into several decks.
     * Each deck has 12 cards. There are maximum 5 decks. In each round, a
     * player takes one deck. The decks are shuffled in a deterministic way.
     * After each round, they give the decks to the next player.
     *
     * If there are three players in the game, there should be only three decks.
     * If there are four players in the game, there should be only four decks.
     * If there are five players in the game, there should be five decks.
     * The game does not support two players or less, six players or more.
     */
    private Weather[][] decks;
    /**
     * The players array.
     */
    private Player[] players;

    /**
     * Main method, do not change this method.
     */
    public static void main(String[] arg) {
        System.out.println("Welcome to the Turn the Tile game!");
        int player = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Please enter the number of players you want to play: ");
            player = scanner.nextInt();
        } while (player < 2 || player > 5);
        new Table(player).run();
    }

    public Table(int player) {

        // prepare players array

        Player human = new Player("Anya Forger");

        Player[] playerTemp = new Player[player];
        playerTemp[0] = human;

        for (int i = 1; i < player; i++) {
            Player aiPlayer = new Player();
            playerTemp[i] = aiPlayer;
        }
        players = playerTemp;

        // prepare tide cards, 1 to 12

        for (int i = 0; i < 12; i++) {
            tide[i * 2] = i + 1;
            tide[i * 2 + 1] = i + 1;
        }

        int[][] MAGIC_NUMBERS = {
                { 31, 26, 54, 19, 8, 45, 39, 16, 28, 42, 3, 38 },  //deck1
                { 30, 60, 58, 56, 51, 10, 41, 48, 14, 9, 32, 44 },  //deck2
                { 29, 35, 7, 49, 1, 21, 13, 46, 6, 18, 43, 24 },   //deck3
                { 27, 34, 33, 11, 12, 47, 23, 55, 40, 37, 22, 15 }, //deck4, if any
                { 57, 36, 50, 20, 53, 52, 59, 4, 25, 2, 17, 5 }     //deck5, if any
        };

        // assign weather cards from the array weather to decks according to the
        // MAGIC_NUMBERS

        Weather[][] tempDecksArray = new Weather[players.length][MAGIC_NUMBERS[0].length];
        for (int i = 0; i < player; i++) {
            for (int j = 0; j < TOTAL_TURN; j++) {
                Weather card = new Weather(MAGIC_NUMBERS[i][j]);
                tempDecksArray[i][j] = card;
            }
        }
        decks = tempDecksArray;
    }

    private void shuffleTideCard() {
        // A deterministic shuffle required by the program
        int[] order = { 4, 23, 8, 11, 3, 16, 21, 14, 0, 6, 18, 15, 19, 9, 5, 12, 13, 2, 17, 7, 10, 20, 1, 22 };
        int[] newTide = new int[TOTAL_TIDE_CARDS];
        for (int i = 0; i < TOTAL_TIDE_CARDS; i++) {
            newTide[i] = tide[order[i]];
        }
        tide = newTide;
    }

    public void run() {

        for (int round = 0; round < players.length; round++) {
            int[] tideCards = new int[24];

            for (int i = 0; i < 12; i++) {
                tideCards[i * 2] = i + 1;
                tideCards[i * 2 + 1] = i + 1;
            }

            for (int i = 0; i <= round; i++){
                shuffleTideCard();
            }

            System.out.println();
            System.out.println("Round " + (round + 1) + " starts.");
            System.out.println("---------------------------------");
            oneRound(round);
        }
    }

    private void oneRound(int round) {

        for (int i = round; i < decks.length; i++) {
            for (int j = 0; j < decks[i].length; j++) {
                players[i - round].addCard(decks[i][j]);
            }
        }
        for (int i = 0; i < round; i++) {
            for (int j = 0; j < decks[i].length; j++) {
                players[decks.length - round + i].addCard(decks[i][j]);
            }
        }

        for (int i = 0; i < players.length; i++) {
            players[i].calcLifePreservers();
        }
        System.out.println("Turn 1 starts.");
        oneTurn();

        int turns = 1;
        int activePlayers = players.length;
        while (!(turns == 12 || activePlayers < 3)) {
            turns += 1;
            System.out.println("Turn " + turns + " starts.");
            oneTurn();
            activePlayers = 0;
            for (int i = 0; i < players.length; i++) {
                if (!players[i].isEliminated()) {
                    activePlayers++;
                }
            }
        }
        for (int i = 0; i < players.length; i++) {
            players[i].setScore(players[i].getLifePreservers());
        }

        int[] newTide = new int[24];

        for (int i = 0; i < 12; i++) {
            newTide[i * 2] = i + 1;
            newTide[i * 2 + 1] = i + 1;
        }

        tide = newTide;

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName() + " has " + players[i].getScore() + " points.");
            players[i].resetForNewRound();
        }
    }

    private int draw() {

        // Draw the first tide card from the tide deck
        int tideCard = tide[0];

        // Resize the array
        int[] newTideDeck = new int[tide.length - 1];
        for (int i = 1; i < tide.length; i++) {
            newTideDeck[i - 1] = tide[i];
        }
        tide = newTideDeck;

        // Return the value of the tide card
        return tideCard;
    }

    private void oneTurn() {
        int[] drawnTideCards = new int[2];
        drawnTideCards[0] = draw();
        drawnTideCards[1] = draw();

        int biggestTide = 0, secondBiggestTide = 0;
        int biggestIndex = 0, secondBiggestIndex = 0;

        if(drawnTideCards[0] < drawnTideCards[1]){
            biggestTide = drawnTideCards[1];
            secondBiggestTide = drawnTideCards[0];

        } else if(drawnTideCards[0] > drawnTideCards[1]){
            biggestTide = drawnTideCards[0];
            secondBiggestTide = drawnTideCards[1];

        } else {
            biggestTide = drawnTideCards[0];
            secondBiggestTide = drawnTideCards[0];
        }
        System.out.println("The higher tide is " + biggestTide + " and the lower tide is " + secondBiggestTide);

        for(int i = 0; i < players.length; i++){
            System.out.println(players[i].getName() + " has tide level " + players[i].getTideLevel() + ", " + players[i].getLifePreservers() + " life preservers");
        }

        Scanner sc = new Scanner(System.in);
        Weather[] cards_played = new Weather[players.length];

        for(int i = 0; i < players.length; i++){
            if(players[i].isHuman()){

                players[i].printHand();

                int input = 0;
                do {
                    System.out.println();
                    System.out.print("Player " + players[i].getName()+ ", you have " + players[i].getLifePreservers() +  " life preservers, please select a card to play: ");
                    input = sc.nextInt();
                } while (input < 0 || input >= players[i].getCardCount());

                cards_played[i] = players[i].playCard(input);

            } else cards_played[i] = players[i].playRandomCard();
        }
        if(drawnTideCards[0] < drawnTideCards[1]){
            secondBiggestIndex = findIndexWithSecondBiggestCard(cards_played);
            players[secondBiggestIndex].setTideLevel(drawnTideCards[1]);
            biggestIndex = findIndexWithBiggestCard(cards_played);
            players[biggestIndex].setTideLevel(drawnTideCards[0]);
        }
        if(drawnTideCards[0] >= drawnTideCards[1]){
            secondBiggestIndex = findIndexWithSecondBiggestCard(cards_played);
            players[secondBiggestIndex].setTideLevel(drawnTideCards[0]);
            biggestIndex = findIndexWithBiggestCard(cards_played);
            players[biggestIndex].setTideLevel(drawnTideCards[1]);
        }

        drownPlayer();

        System.out.println();

    }

    private int findIndexWithBiggestCard(Weather[] cards) {
        int maxIndex = -1;
        boolean hasCard = false;

        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                if (!hasCard) {
                    maxIndex = i;
                    hasCard = true;
                } else {
                    if (cards[i].getValue() > cards[maxIndex].getValue()) {
                        maxIndex = i;
                    }
                }
            }
        }
        return maxIndex;
    }

    private int findIndexWithSecondBiggestCard(Weather[] cards) {
        if (cards == null || cards.length < 2) {
            return -1;
        }

        int maxIndex = findIndexWithBiggestCard(cards);
        int secondMaxIndex = -1;

        for (int i = 0; i < cards.length; i++) {
            if (i == maxIndex || cards[i] == null) {
                continue;
            }
            if (secondMaxIndex == -1 || cards[i].getValue() > cards[secondMaxIndex].getValue()) {
                secondMaxIndex = i;
            }
        }

        return secondMaxIndex;
    }

    private void drownPlayer() {
        int maxIndex = 0;
        int maxTideLevel = -1;

        for (int i = 0; i < players.length; i++) {
            if (!players[i].isEliminated()) {
                int tideLevel = players[i].getTideLevel();
                if (tideLevel > maxTideLevel) {
                    maxTideLevel = tideLevel;
                    maxIndex = i;
                }
            }
        }

        for (int i = 0; i < players.length; i++) {
            if (players[i].isEliminated()) {
                for (int j = 0; j < players[i].getCardCount(); j++) {
                    players[i].addCard(null);
                }
                System.out.println("Player " + players[i].getName() + " is eliminated.");
            }
        }

        for (int i = 0; i < players.length; i++) {
            if (i == maxIndex) {
                players[i].decreaseLifePreservers();
            }
        }
    }
}