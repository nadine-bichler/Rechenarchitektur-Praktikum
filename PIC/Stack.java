
public class Stack {
	// Attribute
	String[] elements;
	int currentSize;

	// Fähigkeiten
	public Stack(int capacity) {
		elements = new String[capacity];
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
	public String pop(){
		currentSize -= 1;
		return elements[currentSize] = null;
	}

	/**
	 * returns the element on top of the stack if the stack is filled
	 */
	public String top(){
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
