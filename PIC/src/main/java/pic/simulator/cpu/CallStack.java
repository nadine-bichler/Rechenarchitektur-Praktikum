package pic.simulator.cpu;

/**
 * Hardware call stack of the PIC 16F84 (8 entries, circular on overflow).
 */
public class CallStack {

    private static final int DEPTH = 8;

    private final int[] elements = new int[DEPTH];
    private int size = 0;
    private Runnable popListener;

    /**
     * Notified whenever an entry is removed. Used by the UI to refresh the stack view.
     */
    public void setPopListener(Runnable popListener) {
        this.popListener = popListener;
    }

    public int getSize() {
        return size;
    }

    /**
     * Puts an element on top of the stack. If the stack is full the lowest
     * element is overwritten in a circular fashion.
     */
    public void push(int element) {
        if (size == DEPTH) {
            size = 0;
        }
        elements[size] = element;
        size++;
    }

    public int pop() {
        size--;
        if (size == -1) {
            size = DEPTH - 1;
        }
        if (popListener != null) {
            popListener.run();
        }
        return elements[size];
    }

    /**
     * Returns the element on top of the stack if the stack is filled.
     */
    public int top() {
        return elements[size - 1];
    }

    public void reset() {
        for (int i = 0; i <= size; i++) {
            pop();
        }
    }
}
