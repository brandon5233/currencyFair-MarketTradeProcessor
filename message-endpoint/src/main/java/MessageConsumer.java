/*
The MessageConsumer puts each Transaction object into a processorBuffer, and hence consumes messages.
 */

import java.util.concurrent.ArrayBlockingQueue;

class MessageConsumer extends Thread {

    public void consume(Transaction[] transactions, ArrayBlockingQueue<Transaction> processorBuffer) {

        for (Transaction transaction : transactions) {
            if (transaction != null) {
                try {
                    processorBuffer.put(transaction);
                    //System.out.println("put " + transaction.userId + " into buffer");
                } catch (InterruptedException ie) {
                    System.out.println("Writing into processor_buffer interrupted");
                }
            }
        }
    }
}
