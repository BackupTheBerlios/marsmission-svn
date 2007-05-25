package message;

import message.Message;

/**
 * Class for handling Messages in a queue
 * 
 * @author Steffen Boettcher
 * @version 1.0
 */
public class Queue {

	/**
	 * Help class for the elements of the queue
	 */
	class Element {
		Message message;
		Element next;
	}
	
	/**
	 * First Element of queue
	 */
	private Element first = null;
	
	/**
	 * Last Element of queue
	 */
	private Element last = null;
	
	/**
	 * Queue element counter
	 */
	private long count = 0;
	
	/**
	 * Constructor - creates a new empty queue
	 */
	public Queue () {
		first = null;
		last  = null;
		count = 0;
	}
	
	/**
	 * Returns the number of elements in queue
	 * @return the number of elements in queue
	 */
	public long size () {
		return count;
	}
	
	/**
	 * Returns a boolean that value of the queue size (empty / not empty)
	 * @return true if queue is empty and false if queue is  not empty
	 */
	public boolean isEmpty () {
		if (count == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Add a message to queue
	 * @param message
	 *        a message, ready for sending
	 */
	public void addMessage(Message message) {
		if (count == 0) {
			first = new Element();
			last = first;
			count = 1;
		} else {
			last.next = new Element();
			last = last.next;
			count++;
		}
		last.message = message;
		last.next = null;
	}
	
	/**
	 * Returns the first message, which is ready for sending
	 * @return the first message, which is ready for sending
	 * @throws NullPointerException
	 *         if the queue is empty
	 */
	public Message getMessage() throws NullPointerException {
		Message returnMessage = null;
		try {
			returnMessage = first.message;
			if (count <= 0)
				throw new NullPointerException();
		} catch (NullPointerException npe) {
			throw new NullPointerException();
		}
		count--;
		first = first.next;
		if (first == null) {
			last = null;
			count = 0;
		}
		return returnMessage;
	}
}