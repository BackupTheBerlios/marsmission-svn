package message;

import java.util.Date;

/**
 * Class for create, modify and read out of messages
 * 
 * @author Torsten Burschka
 * @author Steffen Boettcher
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
	public Date getTimeStap () {
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
	 * @param message_sender
	 *        the message sender
	 */
	public void setSender(String message_sender) {
		this.message_sender = message_sender;
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
	 * @param group
	 *        the sender group
	 */
	public void setSenderGroup(String group) {
		this.message_sender_group = group;
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
	 * @param message_receiver
	 *        the message receiver
	 */
	public void setReceiver(String message_receiver) {
		this.message_receiver = message_receiver;
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
	 * @param group
	 *        the receiver group
	 */
	public void setReceiverGroup(String group) {
		this.message_receiver_group = group;
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
	 * @param message_data
	 *        the message data
	 */
	public void setData(String message_data) {
		this.message_data = message_data;
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
	 * @param message_type
	 *        the message type
	 */
	public void setType(String message_type) {
		this.message_type = message_type;
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
}
