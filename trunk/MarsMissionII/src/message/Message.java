package message;

import java.util.Date;

/**
 * Class for create, modify and read out of messages
 * 
 * @author Torsten Burschka
 * @author Steffen Boettcher
 * @author Marian Zoellner
 */

public class Message {

	/**
	 * Message time stamp
	 */
	private Date message_timeStamp = null;

	/**
	 * Message sender
	 */
	private String message_sender = null;

	/**
	 * Group of message sender 
	 */
	private String message_sender_group = null;

	/**
	 * Message receiver
	 */
	private String message_receiver = null;

	/**
	 * Group of message receiver
	 */
	private String message_receiver_group = null;

	/**
	 * Message data
	 */
	private String message_data = null;

	/**
	 * Message type
	 */
	private String message_type = null;

	/**
	 * Message data type
	 */
	public static String data_type = "data";

	/**
	 * Message text type
	 */
	public static String text_type = "text";

	/**
	 * Message command type
	 */
	public static String command_type = "command";
	
	/**
	 * Constructor - create a new message with default values
	 */	
	public Message () {
		this.message_data = "abcdefghijklmnopqrstuvwxyz0123456789";
		this.message_receiver = "node";
		this.message_receiver_group = "group";
		this.message_sender = "node";
		this.message_sender_group = "group";
		this.message_timeStamp = new Date(System.currentTimeMillis());
		this.message_type = textType();
	}

	/**
	 * Constructor - create a new message with specific values
	 * @param sender
	 *        the message sender
	 * @param sender_group
	 *        the group of message sender       
	 * @param receiver
	 *        the message receiver
	 * @param receiver_group
	 *        the group of message receiver
	 * @param type
	 *        the message type
	 * @param data
	 *        the message data
	 */
	public Message (String sender, String sender_group, String receiver, String receiver_group, String type, String data) {
		this.message_data = data;
		this.message_receiver = receiver;
		this.message_receiver_group = receiver_group;
		this.message_sender = sender;
		this.message_sender_group = sender_group;
		this.message_timeStamp = new Date(System.currentTimeMillis());
		this.message_type = type;
	}

	/**
	 * Refresh the time stamp of the message with actual system time
	 */
	public void refreshTimeStamp () {
		this.message_timeStamp = new Date (System.currentTimeMillis());
	}

	/**
	 * Returns the message time stamp
	 * @return the message time stamp
	 */
	public Date getTimeStamp () {
		return this.message_timeStamp;
	}

	/**
	 * Returns the message sender
	 * @return the message sender
	 */
	public String getSender() {
		return this.message_sender;
	}

	/**
	 * Set the message sender
	 * @param sender
	 *        the message sender
	 */
	public void setSender(String sender) {
		this.message_sender = sender;
	}

	/**
	 * Returns the group of sender
	 * @return the group of sender
	 */
	public String getSenderGroup() {
		return message_sender_group;
	}

	/**
	 * Set the group of sender
	 * @param sender_group
	 *        the sender group
	 */
	public void setSenderGroup(String sender_group) {
		this.message_sender_group = sender_group;
	}

	/**
	 * Returns the message receiver
	 * @return the message receiver
	 */
	public String getReceiver() {
		return message_receiver;
	}

	/**
	 * Set the message receiver
	 * @param receiver
	 *        the message receiver
	 */
	public void setReceiver(String receiver) {
		this.message_receiver = receiver;
	}

	/**
	 * Returns the group of receiver
	 * @return the group of receiver
	 */
	public String getReceiverGroup() {
		return message_receiver_group;
	}

	/**
	 * Set the group of receiver
	 * @param receiver_group
	 *        the receiver group
	 */
	public void setReceiverGroup(String receiver_group) {
		this.message_receiver_group = receiver_group;
	}

	/**
	 * Returns the message data - can be different type of data
	 * @return the message data
	 */
	public String getData() {
		return message_data;
	}

	/**
	 * Set the message data - can be different type of data
	 * @param data
	 *        the message data
	 */
	public void setData(String data) {
		this.message_data = data;
	}

	/**
	 * Returns the type of message data
	 * @return message type
	 *         the message type
	 */
	public String getType() {
		return message_type;
	}

	/**
	 * Set the type of message data
	 * @param type
	 *        the message type
	 */
	public void setType(String type) {
		this.message_type = type;
	}

	/**
	 * Returns the data type value
	 * @return data type value
	 */
	public String dataType() {
		return data_type;
	}

	/**
	 * Returns the text type value
	 * @return text type value
	 */
	public String textType() {
		return text_type;
	}

	/**
	 * Returns the command type value
	 * @return command type value
	 */
	public String commandType() {
		return command_type;
	}
	
	/**
	 * Set the time stamp of a message, used only by createVerifiedMessage
	 * @param timeStamp
	 *        the time stamp
	 */
	private void setTimeStamp(Date timeStamp) {
		this.message_timeStamp = timeStamp;
	}
	
	/**
	 * Returns a hash of the message, will be used for storing the message in database
	 * @param message
	 *        the message to hash
	 * @return
	 *        the hash of the message
	 */
	public static long hashMessage (Message message) {
		long hash = message.getTimeStamp().getTime() % 3;
		for (int i=0; i < message.getData().length(); i++)
			hash = hash + (long)message.getData().charAt(i);
		for (int i=0; i < message.getReceiver().length(); i++)
			hash = hash + (long)message.getReceiver().charAt(i);
		for (int i=0; i < message.getReceiverGroup().length(); i++)
			hash = hash + (long)message.getReceiverGroup().charAt(i);
		for (int i=0; i < message.getSender().length(); i++)
			hash = hash + (long)message.getSender().charAt(i);
		for (int i=0; i < message.getSenderGroup().length(); i++)
			hash = hash + (long)message.getSenderGroup().charAt(i);
		for (int i=0; i < message.getType().length(); i++)
			hash = hash + (long)message.getType().charAt(i);
		return hash;
	}
	
	/**
	 * Create a new message with all parameter if the hash is correct 
	 * @param sender
	 *        the message sender
	 * @param sender_group
	 *        the group of sender
	 * @param receiver
	 *        the message receiver
	 * @param receiver_group
	 *        the group of receiver
	 * @param type
	 *        the message type
	 * @param timeStamp
	 *        the message time stamp
	 * @param data
	 *        the message data
	 * @param hash
	 *        the hash over the other parameter
	 * @return
	 *        return a message object
	 */
	public static Message createVerifietMessage (String sender, String sender_group, String receiver, String receiver_group, String type, Date timeStamp, String data, long hash) {
		Message message = new Message();
		message.setSender(sender);
		message.setSenderGroup(sender_group);
		message.setReceiver(receiver);
		message.setReceiverGroup(receiver_group);
		message.setType(type);
		message.setTimeStamp(timeStamp);
		message.setData(data);
		if (hashMessage(message) == hash)
			return message;
		else
			return null;
	}
}