package node;

/**
 * @author Torsten Burschka
 * 
 * Dummy zum Testen von Funktionen
 */

import communication.Communication;
import communication.Connect;

import message.Message;
import message.Queue;
import base.Utilities;


public class dummy extends Connect {

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
		System.out.println("timestamp:      "+message.getTimeStap());
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
		System.out.println("timestamp:      "+message.getTimeStap());
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
			System.out.println("timestamp:      "+message.getTimeStap());
			System.out.println("type:           "+message.getType());
			System.out.println("data:           "+message.getData());
			System.out.println(queue.size()+" messages remaining!");
		}
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

	}
}