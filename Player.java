import java.util.concurrent.ThreadLocalRandom;

public class Player {
    /**
     * The number of AI players created so far.
     */
    private static int aiCount = 0;
    /**
     * The number of cards in a player's hand.
     */
    private static final int HAND_SIZE = 12;
    /**
     * The cards in the player's hand.
     */
    private Weather[] cards = new Weather[HAND_SIZE];
    /**
     * The number of life preservers the player has remaining.
     */
    private int remainingLifePreservers;
    /**
     * Whether the player has been eliminated from that round.
     */
    private boolean eliminated;
    /**
     * The tide level of the player.
     */
    private int tideLevel;
    /**
     * The player's game score (accumminated from round 1).
     */
    private int score;
    /**
     * Whether the player is human or AI.
     */
    private boolean isHuman;
    /**
     * The name of the player.
     */
    private String name;
    /**
     * Returns the name of the player.
     *
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the tide level of the player.
     *
     * @return the tide level of the player.
     */
    public int getTideLevel() {
        return tideLevel;
    }
    /**
     * Constructor Creates a new player with the given name.
     *
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.tideLevel = 0;
        this.eliminated = false;
        this.remainingLifePreservers = 0;
        this.cards = new Weather[HAND_SIZE];
        this.isHuman = true;
    }
    /**
     * Creates a new AI player with a default name.
     * The name of AI is "AI 1", "AI 2", "AI 3", etc.
     * Their name does not repeat.
     */
    public Player() {
        if (aiCount == 0) {
            this.name = "AI 1";
        } else {
            this.name = "AI " + (aiCount + 1);
        }

        this.eliminated = false;
        this.isHuman = false;

        aiCount++;
        this.score = 0;
        this.tideLevel = 0;
        this.remainingLifePreservers = 0;
        this.cards = new Weather[HAND_SIZE];
    }
    /**
     * Returns whether the player is human or AI.
     *
     * @return whether the player is human or AI.
     */
    public boolean isHuman() {

        char firstChar = this.name.charAt(0);
        char secondChar = this.name.charAt(1);

        if (firstChar == 'A' && secondChar == 'I') return false;

        return isHuman;
    }
    /**
     * Resets the player for a new round.
     * In each new round they should have reset their attributes except
     * their name and score.
     */
    public void resetForNewRound() {
        this.remainingLifePreservers = 0;
        this.tideLevel = 0;
        this.eliminated = false;
        this.cards = new Weather[HAND_SIZE];
    }
    /**
     * Sets the tide level of the player. It will be called if the player
     * needs to take a tide card in a turn.
     *
     * @param tideLevel the tide level of the player.
     */
    public void setTideLevel(int tideLevel) {
        this.tideLevel = tideLevel;
    }
    /**
     * This method is to deal a weather card to the player
     *
     * @param c the card to be added.
     */
    public void addCard(Weather c) {

        int index = 0;
        while (index < cards.length && cards[index] != null) {
            index++;
        }

        if (index < cards.length) {
            cards[index] = c;
        }
    }

        /**
         * This method is to play a card from the player's hand.
         *
         * @param index the index of the card to be played.
         * @return the card to be played.
         */
        public Weather playCard(int index) {
            Weather cardPlayed = cards[index];
            Weather[] newArray = new Weather[cards.length - 1];

            for (int i = 0, j = 0; i < cards.length; i++) {
                if (i != index) {
                    newArray[j++] = cards[i];
                }
            }
            cards = newArray;

            return cardPlayed;
        }

        /**
         * Returns the number of cards in the player's hand.
         *
         * @return the number of cards in the player's hand.
         */
        public int getCardCount() {
            int count = 0;
            for (Weather card : cards) {
                if (card != null) {
                    count++;
                }
            }
            return count;
        }
        /**
         * Returns a random card from the player's hand.
         *
         * @return the card to be played.
         */
        public Weather playRandomCard() {
            if (getCardCount() > 0) {
                int rand = ThreadLocalRandom.current().nextInt(0, getCardCount());
                return playCard(rand);
            }
            return playCard(0);
        }

        /**
         * To compute the initialize life preservers for the player.
         */
        public void calcLifePreservers() {
            int value = 0;

            for (int i = 0; i < getCardCount(); i++) {
                value += cards[i].getLifePreserver();
            }
            if (value/2 == 0) {
                remainingLifePreservers = 0;
            }
            else if (value/2 < 0 || (value - 1)/2 == 0) {
                eliminated = true;
            }
            else if (value % 2 == 0) {
                remainingLifePreservers = value / 2;
            }
            else {
                remainingLifePreservers = (value - 1) / 2;
            }
        }
        /**
         * Returns the number of life preservers the player has remaining.
         */
        public int getLifePreservers() {
            return remainingLifePreservers;
        }
        /**
         * Decreases the number of life preservers the player has remaining.
         */
        public void decreaseLifePreservers() {
            if (remainingLifePreservers > 0) {
                remainingLifePreservers -= 1;
            }
        }
        /**
         * Returns whether the player has been eliminated from that round.
         */
        public boolean isEliminated() {
            //return eliminated;

            if(remainingLifePreservers <= 0){
                eliminated = true;
            }

            return eliminated;
        }
        /**
         * Returns the player's game score (accumminated from round 1).
         */
        public int getScore() {
            return score;
        }
        /**
         * Set the player's game score (accumminated from round 1).
         */
        public void setScore(int score) {
            this.score += score;
        }
        /**
         * Prints the player's hand.
         * Please refer to the sample program for the proper format.
         * The order of the card will not affect the correctness of the program.
         *
         * It prints nothing if the player is AI.
         */
        public void printHand() {

            if (isHuman) {
                for (int i = 0; i < cards.length; i++) {
                    if (!(cards[i] == null)) {
                        System.out.print(cards[i] + " ");
                    } else {
                        System.out.print("");
                    }
                }
            }
        }
    }