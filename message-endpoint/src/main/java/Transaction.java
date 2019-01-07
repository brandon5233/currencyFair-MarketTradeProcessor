/*
POJO class used by GSON to represent each trade message as a java object
 */

public class Transaction {
    public String userId = null;
    public String currencyFrom = null;
    public String currencyTo = null;
    public double amountSell = 0;
    public double amountBuy = 0;
    public double rate = 0;
    public String timePlaced = null;
    public String originatingCountry = null;

    public String toString(){
        return "userId:" + userId;
    }


}
