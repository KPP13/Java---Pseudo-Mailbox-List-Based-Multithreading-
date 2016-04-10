// T1 object, T2 - priority
public class Tuple<T1, T2 extends Number> {

    private T1 messageObj;  // object with message, i.e. String
    private T2 priority;    // priority - lower number = higher priority

    // simple constructor
    Tuple(T1 obj, T2 priority) {
        messageObj = obj;
        this.priority = priority;
    }

    // return message
    public T1 getMessage() {
        return messageObj;
    }

    // get priority
    public int getPriority() {
        return priority.intValue();
    }

    // toString
    @Override
    public String toString() {
        return messageObj.toString();
    }
}
