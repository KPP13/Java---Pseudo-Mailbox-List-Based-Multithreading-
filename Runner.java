// run some producer threads with one consumer thread
public class Runner {
    public static void main(String[] args) {

        // consumer object with 10 elements and 1000 ms to sleep
        Consumer<String,Integer> cons = new Consumer<>(10, 1000);

        // consumer thread
        Thread consThread = new Thread(cons);
        consThread.setName("CONSUMER THREAD");
        consThread.start();

        // create some producers
        for (int i=0; i<3; i++) {
            Producer<String> pr = new Producer<>( cons ,"Producer " + i, 2000 );
            Thread pThr = new Thread(pr);
            pThr.setName("Producer " + i);
            pThr.start();
        }
    }
}