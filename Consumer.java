import java.util.LinkedList;
import java.util.List;

// list based consumer with pseudo - mailbox object
// T1 for object with message
// T2 for priority
public class Consumer<T1, T2 extends Number> implements Runnable {

    private final List<Tuple<T1, T2>> mailbox;      // mailbox - array
    private final int limit;                        // limit (capacity)
    private final int time;                         // time to sleep

    // constructor
    Consumer(final int n, final int time) {
        mailbox = new LinkedList<>();
        limit = n;
        this.time = time;
    }

    // check if the mailbox if empty
    private boolean isEmpty() {
        return mailbox.size() == 0;
    }

    // check if the mailbox is full
    private boolean isFull() {
        return mailbox.size() == limit;
    }

    // add new object to the mailbox (of there is a free space)
    public void addElement(final Tuple<T1,T2> obj) {

        synchronized (mailbox) {        // synchronize mailbox object

            while (isFull()) {          // mailbox is full...
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting...");
                    mailbox.wait();
                }
                catch(InterruptedException e) {
                    Thread.currentThread().interrupt();     // flag
                    System.out.println("Consumer Thread interrupted...");
                }
            }

            mailbox.add(obj);
            mailbox.notifyAll();        // send a signal
        }
    }

    // take a message from a mailbox with the highest priority
    private String getMessage() throws InterruptedException {
        Tuple<T1, T2> tuple;        // temporary object

        synchronized(mailbox) {     // synchronize mailbox

            while (isEmpty()) {     // mailbox is empty...
                System.out.println(Thread.currentThread().getName() + " is waiting...");
                mailbox.wait();
            }

            // sort the messages with priorities
            mailbox.sort((e1, e2) -> e1.getPriority() - e2.getPriority());

            // get the first object
            tuple = ((LinkedList<Tuple<T1, T2>>) mailbox).pollFirst();

            // signal other threads
            mailbox.notifyAll();
        }

        return "Message: " + tuple.getMessage().toString() + ", priority: " + tuple.getPriority();
    }

    // runnable...
    @Override
    public void run() {
        // infinite loop
        while (true) {
            try {
                System.out.println(getMessage());
                Thread.sleep(time);
            }
            catch(InterruptedException e) {
                Thread.currentThread().interrupt();     // flag
                System.out.println("Consumer Thread interrupted...");
            }

        }
    }
}
