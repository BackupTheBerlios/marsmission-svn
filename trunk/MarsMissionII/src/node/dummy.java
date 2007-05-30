package node;

/**
 * @author Torsten Burschka
 * 
 * Dummy zum Testen von Funktionen
 */

import message.Message;
import message.MessageFileOperations;
import message.Queue;
import base.Configuration;
import base.Utilities;


public class dummy  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println();
		
		System.out.println("generate new message.");
		Message message = new Message();
		Queue queue = new Queue();

		System.out.println("print new message.");
		System.out.println("sender:         "+message.getSender());
		System.out.println("sender group:   "+message.getSenderGroup());
		System.out.println("receiver:       "+message.getReceiver());
		System.out.println("receiver group: "+message.getReceiverGroup());
		System.out.println("timestamp:      "+message.getTimeStamp());
		System.out.println("type:           "+message.getType());
		System.out.println("data:           "+message.getData());

		System.out.println();
		System.out.println("message queue emtpy: "+queue.isEmpty());
		queue.addMessage(message);
		System.out.println("added message to queue.");
		System.out.println("now messages in queue: "+queue.size());
		try {
			Thread.sleep(3000L);			
		} catch (InterruptedException ie) {}
		message = new Message();
		message.setData("hallo123");
		message.setReceiver("MaZ");
		message.setSender("Torsten");
		message.setType(message.dataType());
		message.refreshTimeStamp();

		System.out.println("\nprint updated message.");
		System.out.println("sender:         "+message.getSender());
		System.out.println("sender group:   "+message.getSenderGroup());
		System.out.println("receiver:       "+message.getReceiver());
		System.out.println("receiver group: "+message.getReceiverGroup());
		System.out.println("timestamp:      "+message.getTimeStamp());
		System.out.println("type:           "+message.getType());
		System.out.println("data:           "+message.getData());

		System.out.println();
		System.out.println("message queue emtpy: "+queue.isEmpty());
		queue.addMessage(message);
		System.out.println("added message to queue.");
		System.out.println("now messages in queue: "+queue.size());


		System.out.println("\nread out "+queue.size()+" messages from queue:");
		
		while (!queue.isEmpty()) {
			message = queue.getMessage();
			System.out.println("\nprint message from queue.");
			System.out.println("sender:         "+message.getSender());
			System.out.println("sender group:   "+message.getSenderGroup());
			System.out.println("receiver:       "+message.getReceiver());
			System.out.println("receiver group: "+message.getReceiverGroup());
			System.out.println("timestamp:      "+message.getTimeStamp());
			System.out.println("type:           "+message.getType());
			System.out.println("data:           "+message.getData());
			System.out.println(queue.size()+" messages remaining!");
		}
		
		System.out.println("\nCreate a hashsum of last message:\n");
		long hash = Message.hashMessage(message);
		System.out.println(hash);
		System.out.println("\nNow create a new message from message and hash.");
		message = Message.createVerifietMessage(message.getSender(), message.getSenderGroup(),message.getReceiver(),message.getReceiverGroup(),message.getType(),message.getTimeStamp(),message.getData(), hash);
		System.out.println("New created message.");	
		System.out.println("sender:         "+message.getSender());
		System.out.println("sender group:   "+message.getSenderGroup());
		System.out.println("receiver:       "+message.getReceiver());
		System.out.println("receiver group: "+message.getReceiverGroup());
		System.out.println("timestamp:      "+message.getTimeStamp());
		System.out.println("type:           "+message.getType());
		System.out.println("data:           "+message.getData());
		
		System.out.println("\nTry to fake a message hash, set hash to 1001.");
		hash = 1001;
		message = Message.createVerifietMessage(message.getSender(), message.getSenderGroup(),message.getReceiver(),message.getReceiverGroup(),message.getType(),message.getTimeStamp(),message.getData(), hash);
		if (message == null)
			System.out.println("Faking a message failed.");
		else
			System.out.println("Faking a message succeded.");
		
		message = new Message();
		message.setData("ein test");
		message.setReceiver("Steffen");
		message.setSender("Torsten");
		message.setType(message.commandType());
		System.out.println("Writing message to file message-test.txt");
		MessageFileOperations.saveToFile("message-test.txt", message);
		message = null;
		System.out.println("Loading saved message from file-testt.txt");
		message = MessageFileOperations.loadFromFile("message-test.txt");
		System.out.println("sender:         "+message.getSender());
		System.out.println("sender group:   "+message.getSenderGroup());
		System.out.println("receiver:       "+message.getReceiver());
		System.out.println("receiver group: "+message.getReceiverGroup());
		System.out.println("timestamp:      "+message.getTimeStamp());
		System.out.println("type:           "+message.getType());
		System.out.println("data:           "+message.getData());
	
		
		
		System.out.println();
		
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
		
		System.out.println("\n\nNow Open/Creating a profile named test123");
		
		Configuration conf = new Configuration("test123");
		System.out.println(Configuration.getGlobalPath());
		System.out.println(Configuration.getProjectPath());
	}

}