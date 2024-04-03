/**
 * Creates a stack with arrays
 * @author Nadine Bichler
 */
package PIC;
import java.util.EmptyStackException;

public class ArrayStack {
	// Attribute
	String[] elements;
	int currentSize;

	// Fähigkeiten
	public ArrayStack(int capacity) {
		elements = new String[capacity];
		currentSize = 0;
	}

	/**
	 * puts an element on top of the stack, if the stack is full the lowest element
	 * is removed and the others slide down to create space
	 */
	public void push(String element) throws FullStackException {
		if (currentSize == elements.length) {
			throw new FullStackException("Stack full: maxiumum capacity is " + elements.length);
		}
		elements[currentSize] = element;
		currentSize += 1;
	}

	/**
	 * removes the element on top of the stack if the stack is filled
	 */
	public String pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		currentSize -= 1;
		return elements[currentSize] = null;
	}

	/**
	 * returns the element on top of the stack if the stack is filled
	 */
	public String top() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return elements[currentSize - 1];
	}

	/**
	 * checks if the stack is empty
	 */
	public boolean isEmpty() {
		for (String element : elements) {
			if (element != null) {
				return false;
			}
		}
		return true;
	}
}
