/**
 * A class that represents the weather card.
 * A weather card has a value from 1 to 60 and a life preserver value.
 * No life preserver if the weather value is less than 13 or greater than 48.
 * There is a full life preserver if the weather value is between 25 and 36 (inclusive).
 * There is a half life preserver if the weather value is not the above cases.
 * 
 */
public class Weather {
    /**
     * No life preserver is denoted by the constant 0.
     */
    private static final int NO_LIFE_PRESERVER = 0;
    /**
     * Half life preserver is denoted by the constant 1.
     */
    private static final int HALF_LIFE_PRESERVER = 1;
    /**
     * Full life preserver is denoted by the constant 2.
     */
    private static final int FULL_LIFE_PRESERVER = 2;
    private final int lifePreserver;
    private final int value;

    public static int calculateLifePreservers(int numPeople) {
        int totalWeatherValue = 0;
        for (int i = 0; i < numPeople; i++) {
            int weatherValue = (int) (Math.random() * 60) + 1;
            Weather weather = new Weather(weatherValue);
            totalWeatherValue += weatherValue;
        }
        int averageWeatherValue = totalWeatherValue / numPeople;
        Weather averageWeather = new Weather(averageWeatherValue);
        return averageWeather.getLifePreserver() == Weather.NO_LIFE_PRESERVER ? 0 : numPeople;
    }

    /**
     * Constructor
     */
    public Weather(int value) {
        this.value = value;
        if (value < 13 || value > 48) {
            lifePreserver = NO_LIFE_PRESERVER;
        } else if (value >= 25 && value <= 36) {
            lifePreserver = FULL_LIFE_PRESERVER;
        } else {
            lifePreserver = HALF_LIFE_PRESERVER;
        }
    }
    /**
     * Returns the value of the card.
     */
    public int getValue() {
        return value;
    }
    /**
     * Returns the life preserver value of the card.
     * It should return either 0, 1, or 2 for that weather card.
     * 
     * Don't be confuse with the life preserver value of the player.
     */
    public int getLifePreserver() {
        return lifePreserver;
    }

    public String toString() {
        if (lifePreserver == NO_LIFE_PRESERVER) {
            return value + "(.)";
        } else if (lifePreserver == HALF_LIFE_PRESERVER) {
            return value + "(u)";
        } else {
            return value + "(o)";
        }
    }
}