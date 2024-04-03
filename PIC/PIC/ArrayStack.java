package PIC;
public class ArrayStack {
	// Attribute
	int[] elements;
	int currentSize;

	// Fähigkeiten
	public ArrayStack(int capacity) {
		elements = new int[capacity];
		currentSize = 0;
	}

	/**
	 * puts an element on top of the stack, if the stack is full the lowest element
	 * is removed and the others slide down to create space
	 */
	public void push(int element){
		elements[currentSize] = element;
		currentSize += 1;
	}

	/**
	 * removes the element on top of the stack if the stack is filled
	 */
	public int pop(){
		currentSize -= 1;
		return elements[currentSize] = 0;
	}

	/**
	 * returns the element on top of the stack if the stack is filled
	 */
	public int top(){
		return elements[currentSize - 1];
	}

	/**
	 * checks if the stack is empty
	 */
	public boolean isEmpty() {
		for (int element : elements) {
			if (element != 0) {
				return false;
			}
		}
		return true;
	}
}
