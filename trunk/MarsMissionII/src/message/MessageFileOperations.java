package message;

import message.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class MessageFileOperations {

	
	public static void saveToFile(String fileName, Message message) {
		long hash = Message.hashMessage(message);
		try {
			System.out.println("Saving message to "+fileName);
			FileWriter file = new FileWriter(fileName);
			file.write("message_sender="+message.getSender()+"\n");
			file.write("message_sender_group="+message.getSenderGroup()+"\n");
			file.write("message_receiver="+message.getReceiver()+"\n");
			file.write("message_receiver_group="+message.getReceiverGroup()+"\n");
			file.write("message_type="+message.getType()+"\n");
			file.write("message_timestamp="+message.getTimeStamp().getTime()+"\n");
			file.write("message_hash="+hash+"\n");
			file.write("message_data="+message.getData()+"\n");
			file.close();
			System.out.println("Successfully write to file.");
		} catch (IOException ioe) {
			System.err.println("Error writing to file.");
			ioe.printStackTrace();
		}
	}
		

	public static Message loadFromFile(String filename) {
		Message message;

		String[] currentLine = new String[8];

		try {
			FileReader file = new FileReader(filename);
			BufferedReader buffer = new BufferedReader(file);
			for (int i=0; i < 7; i++) {
				currentLine[i] = buffer.readLine();
			}
			boolean eof = false;
			while (!eof) {
				String line = buffer.readLine();
				if (line == null)
					eof = true;
				else
					currentLine[7] = currentLine[7] + line;
			}
			buffer.close();

			int[] index = new int[8];
			for (int i=0; i<8; i++) {
				index[i] = currentLine[i].indexOf("=");
				currentLine[i] = currentLine[i].substring(index[i]+1);
			}
				
			String sender         = currentLine[0];
			String sender_group   = currentLine[1];
			String receiver       = currentLine[2];
			String receiver_group = currentLine[3];
			String type           = currentLine[4];
			Date timeStamp        = new Date(Long.parseLong(currentLine[5]));
			long hash             = Long.parseLong(currentLine[6]);
			String data           = currentLine[7];

			message = Message.createVerifietMessage(sender, sender_group, receiver, receiver_group, type, timeStamp, data, hash);

		} catch (IOException ioe) {
			ioe.printStackTrace();
			message = null;
		}
		return message;
	}
}
