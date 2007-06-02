package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import message.Message;

import base.Configuration;

/**
 * Class for database connectivity for messages
 * @author Steffen Boettcher
 *
 */
public class MessageServer {
	
	/**
	 * Sortieren nach Sendern
	 */
	public static final int sortBySender = 0;
	
	/**
	 * Sortieren nach Empfängern 
	 */
	public static final int sortByReceiver = 1;

	/**
	 * Sortieren nach Zeitstempel 
	 */
	public static final int sortByTimestamp = 2;

	/**
	 * Sortieren nach Nachrichtentyp 
	 */
	public static final int sortByType = 3;
	
	
	/**
	 * create database tables:
	 * Messages, Groups
	 */
	public static void createMessageDatabase() {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "CREATE TABLE Messages (" +
					"message_nr integer IDENTITY PRIMARY KEY, " +
					"message_receiver varchar(20), " +
					"receiver_group varchar(20), " +
					"message_sender varchar(20), " +
					"sender_group varchar(20), " +
					"message_timestamp BIGINT, " +
					"message_data varchar(255), " +
					"message_type varchar(10)," +
					"hash BIGINT )";
			
			stmt.execute(query);
			
			System.out.println("Message database created successfully.\n");
			
		} catch (SQLException e) {
			// do nothing
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * delete database for messages
	 * 
	 * BE SURE YOU REALLY WANT TO DELETE IT!!!!!!!
	 */
	public static void deleteMessageDatabase() {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "DROP TABLE Messages";
			
			stmt.execute(query);
			
			System.out.println("Database deleted successfully.\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * insert a message to database
	 * @param message
	 *        Message to insert
	 */
	public static void insertMessage(Message message) {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "INSERT " +
						   "INTO Messages " + 
						   "VALUES (null, " +
						   "'" + message.getReceiver() + "', " +
						   "'" + message.getReceiverGroup() + "', " +
						   "'" + message.getSender() + "', " +
						   "'" + message.getSenderGroup() + "', " +
						   message.getTimeStamp().getTime() + ", " +
						   "'" + message.getData() + "', " +
						   "'" + message.getType() + "', " + 
						   Message.hashMessage(message) + ")";
			
			stmt.execute(query);
			
			System.out.println("Message saved.\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * get messages from database
	 * @param sender
	 *        node which messages should be shown (null = show all messages in database)
	 */
	public static void getMessages(String sender, int sort) {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT * FROM Messages";
			if (sender != null)
				query += " WHERE message_sender = '" + sender + "'";
			
			// nach Sender sortieren
			if (sort == 0)
				query += " ORDER BY message_sender";
			// nach Receiver sortieren 
			else if (sort == 1)
				query += " ORDER BY message_receiver";
			// nach Zeitstempel sortieren
			else if (sort == 2)
				query += " ORDER BY message_timestamp DESC";
			// nach Nachrichtentyp sortieren
			else
				query += " ORDER BY message_type";
			
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.print(format("Nr", 5));
			System.out.print(format("Reciever", 20));
			System.out.print(format("Receivergroup", 20));
			System.out.print(format("Sender", 20));
			System.out.print(format("Sendergroup", 20));
			System.out.print(format("Zeitstempel", 15));
			System.out.print(format("Typ", 10));
			System.out.println("Daten");
			
			System.out.print(format("==", 5));
			System.out.print(format("========", 20));
			System.out.print(format("=============", 20));
			System.out.print(format("======", 20));
			System.out.print(format("===========", 20));
			System.out.print(format("===========", 15));
			System.out.print(format("===", 10));
			System.out.println("=====\n");
			
			
			while (rs.next())
            {
				System.out.print(format(rs.getString(1), 5));
				System.out.print(format(rs.getString(2), 20));
				System.out.print(format(rs.getString(3), 20));
				System.out.print(format(rs.getString(4), 20));
				System.out.print(format(rs.getString(5), 20));
				System.out.print(format(rs.getString(6), 15));
				System.out.print(format(rs.getString(8), 10));
				System.out.println(rs.getString(7));
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * method needed for table output
	 * @param input
	 *        String
	 * @param size
	 *        size of the output string
	 * @return
	 */
	private static String format(String input, int size) {
		while (input.length() < size)
			input += " ";
		return input; 
	}
	
	/**
	 * get message from database
	 * @param pnr
	 *        message primary key
	 * @param mess
	 *        message to save
	 * @return true if messages was saved
	 */
	public static boolean saveInMessage(int pnr, Message mess) {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return false;
		}
		
		Connection con = null;
		boolean result = true;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT * FROM Messages WHERE message_nr=" + pnr;
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
            {
                mess = new Message(rs.getString(4), rs.getString(5),
                				   rs.getString(2), rs.getString(3),
                				   rs.getString(8), rs.getString(7) );
                mess = Message.createVerifietMessage(rs.getString(4), rs.getString(5), 
                									 rs.getString(2), rs.getString(3), 
                									 rs.getString(8), 
                									 new Date(Long.parseLong(rs.getString(6))),
                									 rs.getString(7), 
                									 Long.parseLong(rs.getString(9)));
            }
			
			if (mess != null)
				System.out.println("Message loaded.\n");
			else
				System.out.println("Criminal hash value! Message not created!!!\n");
			
		} catch (SQLException e) {
			System.out.println("Message not found!\n");
			result = false;
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public static void main(String[] args) {
		MessageServer.createMessageDatabase();
		Message tmp = new Message("Steffen", "Steffengroup", "Ellen", "Ellengroup",
				Message.data_type, "Koche Kaffee!");
		MessageServer.insertMessage(tmp);
		tmp = new Message("Strauss", "Straussgroup", "Louis", "Louisgroup",
				Message.text_type, "Hallo Kumpel!");
		MessageServer.insertMessage(tmp);
		tmp = new Message("Strauss", "Straussgroup", "Steffen", "Steffengroup",
				Message.command_type, "Fuck off!");
		MessageServer.insertMessage(tmp);
		MessageServer.getMessages(null, MessageServer.sortByType);
		Message newMess = new Message();;
		MessageServer.saveInMessage(2, newMess);
		MessageServer.deleteMessageDatabase();
	}
}