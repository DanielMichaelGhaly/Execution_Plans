package db_population_files;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Schema3 {

//	CREATE TABLE Sailors(sid INT PRIMARY KEY, sname CHAR(20), rating INT, age REAL);

	private static String hostname;
	private static int port_Number;
	private static String db_Name;
	private static String username;
	private static String password;

	public static long insertSailor(int ID, String Name, int rating, double age, Connection conn) {
		String SQL = "INSERT INTO Sailors(sid,sname,rating,age) " + "VALUES(?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, ID);
			pstmt.setString(2, Name);
			pstmt.setInt(3, rating);
			pstmt.setDouble(4, age);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

//	 CREATE TABLE Boat(bid INT PRIMARY KEY, bname CHAR(20), color CHAR(10));
	public static long insertBoat(int ID, String Name, String color, Connection conn) {
		String SQL = "INSERT INTO Boat(bid,bname,color) " + "VALUES(?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, ID);
			pstmt.setString(2, Name);
			pstmt.setString(3, color);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

//	 CREATE TABLE Reserves(sid INT REFERENCES Sailors, bid INT REFERENCES Boat, day date, PRIMARY KEY(sid,bid));
	public static long insertReserves(int sID, int bID, Date day, Connection conn) {
		String SQL = "INSERT INTO Reserves(sid,bid,day) " + "VALUES(?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, sID);
			pstmt.setInt(2, bID);
			pstmt.setDate(3, day);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	///////////////////////////////////////////////////////// Data Population
	///////////////////////////////////////////////////////// Methods
	///////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
	public static void populateSailor(Connection conn) {
		for (int i = 1; i < 19000; i++) {
			if (insertSailor(i, "Sailor" + i, (i%10) + 1, 18 + (int)(Math.random() * 42), conn) == 0) { //age between 18 & 60
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}
	
	public static String[] colors = {"Red","Blue", "Green"};
	public static void populateBoat(Connection conn) {
		for (int i = 1; i < 3000; i++) {
			if (insertBoat(i, "Boat" + i, colors[i%3], conn) == 0) {
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	@SuppressWarnings("deprecation")
	public static void populateReserves(Connection conn) {
		Random rand = new Random();
		
		for (int i = 1; i <= 1000; i++) {		
			if (insertReserves(i, 103, new Date(rand.nextInt(28) + 1, rand.nextInt(12) + 1, 2025), conn) == 0) { //rand.nextInt(19000) + 1 
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
				
		for (int i = 1001; i <= 2500; i++) { //red boats
			int redBoatId = (rand.nextInt(1000) + 1) * 3; //any id divisible by 3 identifies a red boat according to the colors list 
			if (insertReserves(i, redBoatId, new Date(rand.nextInt(28) + 1, rand.nextInt(12) + 1, 2025), conn) == 0) {
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
		
		for (int i = 1001; i <= 2500; i++) { //green boats
			int greenBoatId = ((rand.nextInt(1000)) * 3) + 2; // green boat 
			if (insertReserves(i, greenBoatId, new Date(rand.nextInt(28) + 1, rand.nextInt(12) + 1, 2025), conn) == 0) {
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
		
		for (int i = 2501; i <= 35000; i++) {  
			if (insertReserves(i, rand.nextInt(3000) + 1, new Date(rand.nextInt(28) + 1, rand.nextInt(12) + 1, 2025), conn) == 0) {
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
		
	}

	public static void insertSchema3(Connection connection) {
		populateSailor(connection);
		populateBoat(connection);
		populateReserves(connection);
	}

	public static void main(String[] argv) {

		hostname = "localhost";
		port_Number = 5432;
		db_Name = "schema3";
		username = "postgres";
		password = "hellojana";

		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port_Number + "/" + db_Name, username, password);
			insertSchema3(connection);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("Connection established successfully!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
