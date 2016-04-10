import java.util.Random;

public class Producer<T1> implements Runnable {

    private final T1 message;   // message object
    private final int time;     // time for sleep

    private final Consumer cons;      // consumer (to send a message)

    // constructor: consumer, message, time to sleep
    Producer(final Consumer<String, Integer> obj, final T1 msg, final int time) {
        cons = obj;
        message = msg;
        this.time = time;
    }

    // add new object with message from a thread
    private void addObj() {
        Random rnd = new Random();      // random priority

        Tuple<T1, Integer> obj = new Tuple<>(message, (int)(rnd.nextDouble()*10) );

        cons.addElement(obj);           // send a message
    }

    // runnable
    @Override
    public void run() {
        // infinite loop
        while (true) {

            try {
                addObj();               // add new message
                Thread.sleep(time);     // wait for a moment
            }
            catch(InterruptedException e) {
                Thread.currentThread().interrupt();     // flag
                System.out.println("Producer Thread interrupted...");
            }
        }

    }
}
