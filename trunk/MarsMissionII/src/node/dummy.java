package node;

/**
 * @author Torsten Burschka
 * 
 * Dummy zum Testen von Funktionen
 */

import database.MessageServer;
import message.Message;
import message.MessageFileOperations;
import message.Queue;
import base.Configuration;
import base.Utilities;

public class dummy  {

	private static void printMessage(Message message) {
		System.out.println("sender:         "+message.getSender());
		System.out.println("sender group:   "+message.getSenderGroup());
		System.out.println("receiver:       "+message.getReceiver());
		System.out.println("receiver group: "+message.getReceiverGroup());
		System.out.println("timestamp:      "+message.getTimeStamp());
		System.out.println("type:           "+message.getType());
		System.out.println("data:           "+message.getData());
	}
	
	public static void main(String[] args) {

/////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\n\nNow Open/Creating a profile named test123");
		Configuration.init("test123");
		
		System.out.println("Generate a new message.");
		// creates a new message object
		Message message = new Message();
		
		System.out.println("\nGenerate a new message queue.");
		// creates a new message queue
		Queue queue = new Queue();

		System.out.println("\nPrint the new (default) message.");
		// print the message
		printMessage(message);

		// give back the status of the message queue
		System.out.println("\nIs the message queue emtpy?: "+queue.isEmpty());
		 
		System.out.println("\nAdd the message to queue.");
		// add the message to message queue
		queue.addMessage(message);

		// give back the status of the message queue
		System.out.println("\nIs the message queue emtpy?: "+queue.isEmpty());

		// show number of messages in the message queue
		System.out.println("\nMessages now in queue: "+queue.size());
		
		System.out.println("Wait for 3000ms.");
		// let the program sleep for 3 seconds
		try {
			Thread.sleep(3000L);			
		} catch (InterruptedException ie) {}
		
		System.out.println("\nGenerate a new message.");
		// creates a new message object
		message = new Message();

		System.out.println("\nUpdate the message object.");
		// update the message object via setters
		message.setData("This is the second message object");
		message.setReceiver("Marian Zoellner");
		message.setReceiverGroup("Home");
		message.setSender("Torsten Burschka");
		message.setSenderGroup("Uni");
		message.setType(message.dataType());
		message.refreshTimeStamp();

		System.out.println("\nAdd the message to queue.");
		// add the message to message queue
		queue.addMessage(message);
		
		// show number of messages in the message queue
		System.out.println("\nMessages now in queue: "+queue.size());

		System.out.println("\nWriting message to a file named message-test.txt");
		// save the message to file
		MessageFileOperations.saveToFile(Configuration.getProfilePath()+"message-test.txt", message);

		System.out.println("\nCreate a hashsum of message:\n");
		// create a hash sum of the message
		long hash = Message.hashMessage(message);
		System.out.println(hash);

		System.out.println("\nNow create a new verified message from message and hash.");
		// create a new verified message, it will be fail, if hash isn't correct 
		message = Message.createVerifietMessage(message.getSender(), message.getSenderGroup(),message.getReceiver(),message.getReceiverGroup(),message.getType(),message.getTimeStamp(),message.getData(), hash);
		System.out.println("\nNew created message:");
		printMessage(message);
		
		System.out.println("\nTry to fake a message hash, set hash to 1001.");
		hash = 1001;
		// create a new verified message, it will be fail, if hash isn't correct
		message = Message.createVerifietMessage(message.getSender(), message.getSenderGroup(),message.getReceiver(),message.getReceiverGroup(),message.getType(),message.getTimeStamp(),message.getData(), hash);
		if (message == null)
			System.out.println("\nFaking a message failed. Message is null");
		else
			System.out.println("\nFaking a message succeded - damn bad hash sum.");
		
		System.out.println("\nNow read out "+queue.size()+" messages from queue:");
		while (!queue.isEmpty()) {
			// get a message from queue
			message = queue.getMessage();
			System.out.println("\nprint message from queue.");
			printMessage(message);
			System.out.println("\n"+queue.size()+" messages remaining!");
		}
		
		System.out.println("\nNow loading saved message from file-testt.txt");
		message = MessageFileOperations.loadFromFile(Configuration.getProfilePath()+"message-test.txt");
		System.out.println("\nLoaded message:");
		printMessage(message);

		System.out.println("Store message in Database");
		MessageServer.insertMessage(message);
		
		System.out.println("\nGenerate a new message.");
		// creates a new message object
		message = new Message();

		System.out.println("Update the message object.");
		// update the message object via setters
		message.setData("Third message");
		message.setReceiver("Steffen");
		message.setReceiverGroup("Studenten");
		message.setSender("Torsten");
		message.setSenderGroup("Programmierer");
		message.setType(message.commandType());

		System.out.println("Store message in Database");
		MessageServer.insertMessage(message);
		
		System.out.println("\n\nNow print the messages in Database sorted by sender:\n");
		MessageServer.getMessages(null,MessageServer.sortBySender);

		System.out.println("\n\nNow print the messages in Database sorted by receiver:\n");
		MessageServer.getMessages(null,MessageServer.sortByReceiver);

		System.out.println("\n\nNow print the messages in Database sorted by time stamp:\n");
		MessageServer.getMessages(null,MessageServer.sortByTimestamp);
		
		System.out.println("\n\nNow print the messages in Database sorted by type:\n");
		MessageServer.getMessages(null,MessageServer.sortByType);

		System.out.println("\nDelete Database");
		MessageServer.deleteMessageDatabase();
		
		String pathURI = "D:\\Dokumente und Einstellungen\\Windows\\Torsten Burschka\\";
		System.out.println();
		System.out.println("pathURI: "+pathURI);
		System.out.println("toURI:   "+Utilities.toURI(pathURI));
		System.out.println("toPath:  "+Utilities.toPath(pathURI));

		pathURI = "D:\\Dokumente und Einstellungen\\Windows\\Torsten Burschka";
		System.out.println();
		System.out.println("pathURI: "+pathURI);
		System.out.println("toURI:   "+Utilities.toURI(pathURI));
		System.out.println("toPath:  "+Utilities.toPath(pathURI));

		pathURI = "D:\\";
		System.out.println();
		System.out.println("pathURI: "+pathURI);
		System.out.println("toURI:   "+Utilities.toURI(pathURI));
		System.out.println("toPath:  "+Utilities.toPath(pathURI));
	}
}