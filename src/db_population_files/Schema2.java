package db_population_files;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Schema2 {

//	CREATE TABLE Employee(Fname CHAR(20), Minit CHAR(10), Lname CHAR(20), ssn INT PRIMARY KEY, Bdate date, address CHAR(20), sex CHARACTER(1), salary INT, Super_snn INT REFERENCES Employee(ssn), dno INT);

	private static String hostname;
	private static int port_Number;
	private static String db_Name;
	private static String username;
	private static String password;
	public static long insertEmployee(String Fname, String Minit, String Lname, int ssn, Date Bdate, String address,
			String sex, int salary, int superSSN, int dno, Connection conn) {
		String SQL = "INSERT INTO Employee(Fname,Minit,Lname,ssn,Bdate,address,sex,salary,Super_snn,dno) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, Fname);
			pstmt.setString(2, Minit);
			pstmt.setString(3, Lname);
			pstmt.setInt(4, ssn);
			pstmt.setDate(5, Bdate);
			pstmt.setString(6, address);
			pstmt.setString(7, sex);
			pstmt.setInt(8, salary);
			pstmt.setInt(9, superSSN);
			pstmt.setInt(10, dno);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(4);
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
//	 CREATE TABLE Department(Dname CHAR(20), Dnumber INT PRIMARY KEY, Mgr_snn int REFERENCES employee, Mgr_start_date date );

	public static long insertDepartment(String Dname, int Dnumber, int MgrSSN, Date startDate, Connection conn) {
		String SQL = "INSERT INTO Department(Dname,Dnumber,Mgr_snn,Mgr_start_date) " + "VALUES(?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, Dname);
			pstmt.setInt(2, Dnumber);
			pstmt.setInt(3, MgrSSN);
			pstmt.setDate(4, startDate);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(2);
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

//	 CREATE TABLE Dept_locations(Dnumber integer REFERENCES Department, Dlocation CHAR(20), PRIMARY KEY(Dnumber,Dlocation));
	public static long insertDeptLocations(int Dnumber, String Dlocation, Connection conn) {
		String SQL = "INSERT INTO Dept_locations(Dnumber,Dlocation) " + "VALUES(?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(2, Dlocation);
			pstmt.setInt(1, Dnumber);

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

//	 CREATE TABLE Project(Pname CHAR(20), Pnumber INT PRIMARY KEY, Plocation CHAR(50), Dnumber INT REFERENCES Department);
	public static long insertProject(String Pname, int Pnumber, String pLocation, int Dnumber, Connection conn) {
		String SQL = "INSERT INTO Project(Pname,Pnumber,Plocation,Dnumber) " + "VALUES(?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, Pname);
			pstmt.setInt(2, Pnumber);
			pstmt.setString(3, pLocation);
			pstmt.setInt(4, Dnumber);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(2);
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
//	 CREATE TABLE Works_on(Essn int REFERENCES Employee, Pno int REFERENCES Project, Hours int, PRIMARY KEY(Essn,Pno));

	public static long insertWorksOn(int Essn, int pNo, int hours, Connection conn) {
		String SQL = "INSERT INTO Works_on(Essn,Pno,Hours) " + "VALUES(?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, pNo);
			pstmt.setInt(1, Essn);
			pstmt.setInt(3, hours);

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

//	 CREATE TABLE Dependent(Essn INT REFERENCES Employee, Dependent_name CHAR(20), sex CHARACTER(1), Bdate date, Relationship CHAR(20), PRIMARY KEY(Essn, Dependent_name));
	public static long insertDependent(int Essn, String dependentName, String sex, Date Bdate, String relationship,
			Connection conn) {
		String SQL = "INSERT INTO Dependent(Essn,Dependent_name,sex,Bdate,Relationship) " + "VALUES(?,?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, Essn);
			pstmt.setString(2, dependentName);
			pstmt.setString(3, sex);
			pstmt.setDate(4, Bdate);
			pstmt.setString(5, relationship);

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

	/////////////////////////////////////////////// Data Population Methods
	/////////////////////////////////////////////// //////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public static void populateEmployee(Connection conn) {
		for (int i = 1; i <= 16000; i++) {
			String sex = (i % 2 == 0) ? "M" : "F";
			String fname = "EmpF" + i;
			String lname = "EmpL" + i;
			String address = "Address_" + i;
			int ssn = i;
			Date bdate = Date.valueOf("1980-01-01"); // use java.sql.Date
			// For the first employee, make supervisor their own SSN to satisfy FK constraint
			int super_ssn = (i > 1) ? i - 1 : i;
			int salary = 40000 + (i % 50000); // set salary in a meaningful range
			int dno = (i % 150) + 1; // spread employees across 150 departments

			int result = (int)insertEmployee(fname, "M" + i, lname, ssn, bdate, address, sex, salary, super_ssn, dno, conn);
			if (result == 0) {
				System.err.println("insertion of employee " + i + " failed");
				break;
			} else {
				System.out.println("Employee " + i + " inserted.");
			}
		}
	}




	@SuppressWarnings("deprecation")
	public static void populateDepartment(Connection conn) {
		for (int i = 1; i <= 150; i++) {
			String dname = "Department_" + i;
			int dnumber = i;
			int mgr_ssn = i; // Manager SSN corresponds to employee SSN 1 to 150
			Date mgr_start_date = Date.valueOf("2000-01-01");

			int result = (int)insertDepartment(dname, dnumber, mgr_ssn, mgr_start_date, conn);
			if (result == 0) {
				System.err.println("Department " + i + " failed.");
				break;
			} else {
				System.out.println("Department " + i + " inserted.");
			}
		}
	}



	public static void populateDeptLocations(Connection conn) {
		for (int i = 1; i <= 150; i++) {
			String location = "Location_" + i;
			int dnumber = i;

			int result = (int)insertDeptLocations(dnumber, location, conn);
			if (result == 0) {
				System.err.println("Department location " + i + " failed.");
				break;
			} else {
				System.out.println("Department location " + i + " inserted.");
			}
		}
	}




	public static void populateProject(Connection conn) {
		for (int i = 1; i <= 9200; i++) {
			String pname = "Project_" + i;
			int pnumber = i;
			String plocation = "Location_" + ((i % 150) + 1); // matches existing dept locations
			int dnum = (i % 150) + 1;

			int result = (int)insertProject(pname, pnumber, plocation, dnum, conn);
			if (result == 0) {
				System.err.println("Project " + i + " failed.");
				break;
			} else {
				System.out.println("Project " + i + " inserted.");
			}
		}
	}



	public static void populateWorksOn(Connection conn) {
		Random random = new Random();

		for (int empId = 1; empId <= 16000; empId++) {
			int projectsToAssign = 2 + random.nextInt(4); // 2 to 5 projects
			Set<Integer> assignedProjects = new HashSet<>();

			for (int j = 0; j < projectsToAssign; j++) {
				int pno;
				do {
					pno = 1 + random.nextInt(9200);
				} while (assignedProjects.contains(pno)); // avoid duplicate projects for same employee

				assignedProjects.add(pno);

				int hours = 10 + random.nextInt(21); // 10 to 30 hours

				int result = (int)insertWorksOn(empId, pno, hours, conn);
				if (result == 0) {
					System.err.println("works_on insert failed for emp " + empId + " project " + pno);
				} else {
					System.out.println("Employee " + empId + " assigned to project " + pno);
				}
			}
		}
	}




	@SuppressWarnings("deprecation")
	public static void populateDependent(Connection conn) {
		int dependentCount = 0;
		int maxDependents = 10000;

		// Assume 8000 employees will have at least one dependent
		for (int empId = 1; empId <= 8000 && dependentCount < maxDependents; empId++) {
			// We'll create dependents where dependent name = employee fname to satisfy query
			// First, we need to fetch employee's fname and sex to use here

			String fname = "EmpF" + empId;  // since you generate employees with this pattern
			String sex = (empId % 2 == 0) ? "M" : "F";

			// Create 1-3 dependents per employee
			int numDependents = 1 + (int)(Math.random() * 3);

			for (int j = 0; j < numDependents && dependentCount < maxDependents; j++) {
				String depName;
				String depSex;

				if (j == 0) {
					// For the first dependent, **set dependent_name = employee fname and sex = employee sex**
					depName = fname;
					depSex = sex;
				} else {
					// For other dependents, create different names and alternate sex
					depName = "Dep_" + empId + "_" + j;
					depSex = (j % 2 == 0) ? "M" : "F";
				}

				String relationship = (j == 0) ? "spouse" : "child";
				Date birthDate = Date.valueOf("199" + (j % 10) + "-01-01"); // Random birth years in 1990s

				int result = (int)insertDependent(empId, depName, depSex, birthDate, relationship, conn);
				if (result == 0) {
					System.err.println("insertion of dependent for employee " + empId + " failed");
				} else {
					dependentCount++;
					System.out.println("Dependent inserted for employee " + empId + ": " + depName);
				}
			}
		}
		System.out.println("Total dependents inserted: " + dependentCount);
	}



	public static void insertSchema2(Connection connection) {
		//populateEmployee(connection);
		//populateDepartment(connection);
		//populateDeptLocations(connection);
		//populateProject(connection);
		populateWorksOn(connection);
		//populateDependent(connection);
	}

	public static void main(String[] argv) {

		hostname = "localhost";
		port_Number = 5432;
		db_Name = "schema2";
		username = "postgres";
		password = "postgreSQL2025";

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
			insertSchema2(connection);

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
