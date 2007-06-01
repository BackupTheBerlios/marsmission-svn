package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import base.Configuration;

/**
 * Class for database connectivity for groups
 * @author Steffen Boettcher
 *
 */
public class GroupServer {
	
	/**
	 * create database table for Groups
	 *
	 */
	public static void createGroupDatabase() {
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
			
			String query = "CREATE TABLE Groups (" +
					"nr integer IDENTITY PRIMARY KEY, " +
					"name VARCHAR(20), " +
					"groupname VARCHAR(20) )";
			
			stmt.execute(query);
			
			System.out.println("Database created successfully.\n");
			
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
	 * get Table with all groups of a node
	 */
	public static void getAllGroups() {
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
			
			String query = "SELECT * FROM Groups";
			
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.print(format("Nr", 5));
			System.out.print(format("Name", 20));
			System.out.println(format("Groupname", 20));
			
			System.out.print(format("==", 5));
			System.out.print(format("========", 20));
			System.out.println(format("=============", 20));
			
			
			while (rs.next())
            {
				System.out.print(format(rs.getString(1), 5));
				System.out.print(format(rs.getString(2), 20));
				System.out.println(format(rs.getString(3), 20));
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
	 * @param input: String
	 * @param size: size of the output string
	 * @return
	 */
	private static String format(String input, int size) {
		while (input.length() < size)
			input += " ";
		return input; 
	}
	
	/**
	 * delete group fellowship
	 * @param group: group name which fellowship will be deleted 
	 */
	public static void deleteGroupFellowship(String group) {
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
			
			String query = "DELETE FROM Groups WHERE groupname='" +
						   group + "' ";
			
			stmt.execute(query);
			
			System.out.println("Fellowship to " + group + " deleted.\n");
			
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
	 * check if node is member of a group
	 * @param group: group name which is to check 
	 */
	public static boolean checkGroupFellowship(String group) {
		boolean member = false;
		
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return false;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT COUNT(*) FROM Groups WHERE groupname='" + group + "' ";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				if (Integer.parseInt(rs.getString(1)) >= 1)
					member = true;
				else
					member = false;
			}
			
		} catch (SQLException e) {
			member = false;
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return member;
	}
	
	/**
	 * store a reference name to a group in database
	 * @param name: reference name
	 * @param group: group name 
	 */
	public static void addGroupFellowship(String name, String group) {
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
			
			String query = "INSERT INTO Groups VALUES ( null, '" +
						   name + "', '" + group + "' )";
			
			stmt.execute(query);
			
			System.out.println("Add " + name + " to group " + group + "\n");
			
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
	 * new user name in group
	 * @param group: group name
	 * @param newName: new name of the node
	 */
	public static void changeGroupFellowshipName(String group, String newName) {
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
			
			String query = "UPDATE Groups SET name='" +
						   newName + "' WHERE groupname='" +
						   group + "'";
			
			stmt.execute(query);
			
			System.out.println("Node name changed.\n");
			
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
	 * get node name in a certain group
	 * @param group: group name
	 */
	public static String getGroupFellowshipName(String group) {
		String output = "";
		
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return new String("");
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT name FROM Groups WHERE groupname='" + group + "' ";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				output = rs.getString(1);
			}
			
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
		return output;
	}
	
	/**
	 * get the nth tupel in database,
	 * informatics like: numbering starts with Zero!!!
	 * @param number: n
	 * @return String: group name\nname
	 */
	public static String getTupelWithNumber(int number) {
		String output = "";
		
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return "";
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT name, groupname FROM Groups A WHERE " +
						   number + " = ( SELECT COUNT(*) FROM Groups B WHERE B.nr<A.nr) ORDER BY nr";  
			
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				output = rs.getString(2) + "\n" + rs.getString(1);
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
		
		return output;
	}
	
	/**
	 * get number of groups where a node is member
	 * @return integer
	 */
	public static int getNumberOfGroups() {
		int number = 0;
		
		try {
			Class.forName( "org.hsqldb.jdbcDriver" );
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return 0;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection( "jdbc:hsqldb:"+Configuration.getProfilePath()+"MarsDB;shutdown=true", "sa" , "");
			
			Statement stmt = con.createStatement();
			
			String query = "SELECT COUNT(*) FROM Groups";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				number = Integer.parseInt(rs.getString(1));
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
		
		return number;
	}
	
	/**
	 * delete database for groups
	 * 
	 * BE SURE YOU REALLY WANT TO DELETE IT!!!!!!!
	 */
	public static void deleteGroupDatabase() {
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
			
			String query = "DROP TABLE Groups";

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
	
	public static void main(String[] args) {
		GroupServer.createGroupDatabase();
		GroupServer.addGroupFellowship("Steffen", "Apolda");
		GroupServer.addGroupFellowship("Torsten", "Lord of JXTA");
		GroupServer.addGroupFellowship("Marian", "Sklave vom Dienst");
		GroupServer.getAllGroups();
		boolean mem = GroupServer.checkGroupFellowship("Apolda");
		System.out.println("Mitgliedschaft: " + mem);
		int i = GroupServer.getNumberOfGroups();
		System.out.println("Number of Groups: " + i);
		GroupServer.changeGroupFellowshipName("Apolda", "Chef");
		String tmp = GroupServer.getTupelWithNumber(1);
		System.out.println("Tupel 2: " + tmp);
		GroupServer.deleteGroupFellowship("Sklave vom Dienst");
		GroupServer.getAllGroups();
		mem = GroupServer.checkGroupFellowship("Sklave vom Dienst");
		System.out.println("Mitgliedschaft: " + mem);
		String name = GroupServer.getGroupFellowshipName("Apolda");
		System.out.println("Name: " + name);
		GroupServer.deleteGroupDatabase();
	}
}
