package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import message.Message;


/**
 * Class for DatabaseConnectivity
 * @author Steffen
 *
 */
public class DatabaseServer {
	
	/**
	 * create database for messages
	 *
	 */
	public static void createDatabase() {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "CREATE TABLE Messages (" +
					"message_nr integer IDENTITY PRIMARY KEY, " +
					"message_receiver varchar(50), " +
					"receiver_group varchar(50), " +
					"message_sender varchar(50), " +
					"sender_group varchar(50), " +
					"message_timestamp integer, " +
					"message_data varchar(100), " +
					"message_type varchar(50) )";
			
			stmt.execute(query);
			
			System.out.println("\nDatabase created successfully.\n");
			
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
	 * delete database for messages
	 * 
	 * BE SURE YOU REALLY WANT TO DELETE IT!!!!!!!
	 */
	public static void deleteDatabase() {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "DROP TABLE Messages";
			
			stmt.execute(query);
			
			System.out.println("\nDatabase deleted successfully.\n");
			
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
	
	public static void insertMessage(Message mess) {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "INSERT " +
						   "INTO Messages " + 
						   "VALUES (null, " +
						   "'" + mess.getReceiver() + "', " +
						   "'" + mess.getReceiverGroup() + "', " +
						   "'" + mess.getSender() + "', " +
						   "'" + mess.getSenderGroup() + "', " +
						   "'" + mess.getTimeStap().getSeconds() + "', " +
						   "'" + mess.getData() + "', " +
						   "'" + mess.getType() + "' )";
			
			System.out.println(query);
			
			stmt.execute(query);
			
			System.out.println("\nMessage saved.\n");
			
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
	
	public static void getAllMessages() {
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT * FROM Messages";
			
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Nr\tReceiver\tReceivergroup\tSender" + 
							"\tSendergroup\tTimestamp\tData\tType\n");
			
			while (rs.next())
            {
                System.out.print(rs.getString(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.print(rs.getString(3) + "\t");
                System.out.print(rs.getString(4) + "\t");
                System.out.print(rs.getString(5) + "\t");
                System.out.print(rs.getString(6) + "\t");
                System.out.print(rs.getString(7) + "\t");
                System.out.println(rs.getString(8) + "\t");
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
	
	public static void main(String[] args) {
		DatabaseServer.createDatabase();
		Message tmp = new Message("Steffen", "Steffengroup", "Ellen", "Ellengroup",
				Message.data_type, "Koche Kaffee!");
		DatabaseServer.insertMessage(tmp);
		DatabaseServer.getAllMessages();
		DatabaseServer.deleteDatabase();
	}
}
