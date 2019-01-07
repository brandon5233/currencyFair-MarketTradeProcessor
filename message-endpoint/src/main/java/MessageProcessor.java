/*
The Processor takes Transaction objects from the buffer.
Its goal is to create a map of frequencies of countries where transactions have originated.
Each country's frequency is calculated after each transaction.
This frequency is converted to a percentage to total transactions.
This percentage is represented by a color band ranging from red(0%) to green(100%).
This map is send to the each client via a websocket to be rendered at the frontend.

The significance of this is to be able to view a color-graded global map
to identify the countries who use our services the most.
 */

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class MessageProcessor extends Thread {
    ArrayBlockingQueue<Transaction> processorBuffer;
    HashMap<String, Integer> countryFreqMap;
    int transactionCount;

    MessageProcessor(ArrayBlockingQueue processorBuffer) {
        this.processorBuffer = processorBuffer;

        countryFreqMap = new HashMap<>();
        transactionCount = 0;
        System.out.println("Processor Started");
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Transaction transaction = processorBuffer.take();
                //System.out.println("*****************Threaded Transaction : " + transaction.toString());
                processTransaction(transaction);

            } catch (InterruptedException ie) {
                System.out.println("Interrupted when reading from processorBuffer");
            }
        }
    }

    void processTransaction(Transaction transaction) {
        String originCountry = transaction.originatingCountry;
        if (countryFreqMap.containsKey(originCountry)) {
            int countryCount = countryFreqMap.get(originCountry);
            countryFreqMap.put(originCountry, countryCount + 1);
        } else {
            countryFreqMap.put(originCountry, 1);
        }
        transactionCount++;
        DeliveryPacket dp = new DeliveryPacket();

        for (Map.Entry<String, Integer> entry : countryFreqMap.entrySet()) {
            int percent = ((entry.getValue() * 100 / transactionCount));
            System.out.println("percent: " + percent + " entryVal: " + entry.getValue() + " txnCount: " + transactionCount);
            dp.originCountryGradients.put(entry.getKey(), calcGradient(percent));
            dp.originCountryPercents.put(entry.getKey(), percent);
        }

        for (Session s : Main.sessions) {
            try {
                if (s.isOpen()) {
                    s.getRemote().sendString(new Gson().toJson(dp, DeliveryPacket.class));
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    String calcGradient(int percent) {
        if (percent >= 0 && percent < 10) return "#FF0000";
        else if (percent >= 10 && percent < 20) return "#FF3500";
        else if (percent >= 20 && percent < 30) return "#FF6B00";
        else if (percent >= 30 && percent < 40) return "#FFA100";
        else if (percent >= 40 && percent < 50) return "#FFD600";
        else if (percent >= 50 && percent < 60) return "#F1FF00";
        else if (percent >= 60 && percent < 70) return "#BBFF00";
        else if (percent >= 70 && percent < 80) return "#86FF00";
        else if (percent >= 80 && percent < 90) return "#50FF00";
        else if (percent >= 90 && percent <= 100) return "#1AFF00";
        else return "#000000";
    }
}
