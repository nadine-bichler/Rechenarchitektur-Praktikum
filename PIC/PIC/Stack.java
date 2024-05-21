package PIC;
public class Stack {
    // Attribute
    private static int[] elements = new int [8];
    private static int currentSize;

    // Fähigkeiten
    static {
        currentSize = 0;
    }

    /**
     * puts an element on top of the stack, if the stack is full the lowest element
     * is removed and the others slide down to create space
     */
    public static void push(int element){
        if (currentSize == 8){
            currentSize = 0;
        }
        elements[currentSize] = element;
        currentSize += 1;
    }

    /**
     * removes the element on top of the stack
     */
    public static int pop(){
        currentSize -= 1;
        if (currentSize == -1){
            currentSize = 7;
        }
        return elements[currentSize];
    }

    /**
     * returns the element on top of the stack if the stack is filled
     */
    public static int top(){
        return elements[currentSize - 1];
    }
}
