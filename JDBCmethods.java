package jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class JDBCmethods {

	public static void displayResultSet(ResultSet rs) throws SQLException {
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int numC = rsmd.getColumnCount(); //starts at 1.
			
			while (rs.next()) {
				for(int c=1; c<=numC; c++)
				{
					String currAttribute = rs.getString(rsmd.getColumnName(c));
					
					System.out.println(currAttribute+"\t");
				}
			}
			System.out.println("************************************************************************************");		
		}
		
	
	public static ResultSet runQuery(Connection myConn, String sqlStr) {
			
				Statement myStmt =null;
				ResultSet rs =null;
				try {
					myStmt = myConn.prepareStatement(sqlStr);
					rs = myStmt.executeQuery(sqlStr);
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//rs = myStmt.executeQuery(sqlStr);
				return rs;
		}
		
	public static Connection getConnection(String username, String password) {
			Connection myConnection = null;
			try {
				myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);
				System.out.println("Connected to database");
	
			}
			catch (SQLException e) {
				System.out.println("Failed to connect to the database");
			}
			return myConnection;
		}
		//maybe make this so that database of inputed name is created...?
	public static void createDatabase(Connection myConn) {
			Statement stmt3 = null;
			String sql = null;
	
			System.out.println("Creating database 'companytest'.....");
			try {
				stmt3 = myConn.createStatement();
				sql = "CREATE DATABASE COMPANY;";
				stmt3.executeUpdate(sql);
				System.out.println( "'company' created successfully.");
			}
			catch (SQLException e){
				System.out.println("Database already exists!");
			}
			
		}
		
	public static void initDept(Connection myConn) throws SQLException {
			Scanner scan = new Scanner(System.in);
			Statement stmt4 = null;
			String sql = null;
			String deptName, deptLocation;
			int deptNum = 0, mgrSsn = 0;
			System.out.println("Enter department name: ");
			deptName = scan.nextLine();
			System.out.println("Enter department location: ");
			deptLocation = scan.nextLine();
			System.out.println("Enter department number: ");
			
			//these try/catches only catch one instance of incorrect input. fix later.
			
			try {
				deptNum = scan.nextInt();
			}
			catch(NoSuchElementException e) {
				scan.next();
				System.out.println("Please enter a valid number");
				deptNum = scan.nextInt();
	
			}
			System.out.println("Enter department manager ssn: ");
			try {
				mgrSsn = scan.nextInt();
				}
			catch(NoSuchElementException e) {
				scan.next();
				System.out.println("Please enter a valid (9-digit) number");
				mgrSsn = scan.nextInt();
	
				}
			System.out.println("Initializing table.....");
			try {
				stmt4 = myConn.createStatement();
				sql = "INSERT INTO company.department (Dlocation, Dname, Dnumber, Mgr_ssn)"
						+ "VALUES ('"+deptLocation+"', '"+deptName+"', '"+deptNum+"', '"+mgrSsn+"');";
				stmt4.executeUpdate(sql);
				System.out.println("Table initialized successfully!");
				
			}
			catch(SQLIntegrityConstraintViolationException e) {
				System.out.println("Duplicate entry. Try again.");
			}
			
		}
		
	public static void initProject(Connection myConn) {
			Scanner scan = new Scanner(System.in);
			Statement stmt4 = null;
			String sql = null;
			String PName, PLocation;
			int deptNum = 0, Pnumber = 0; 
			System.out.println("Enter project name: ");
			PName = scan.nextLine();
			System.out.println("Enter project location: ");
			PLocation = scan.nextLine();
			System.out.println("Enter department number: ");
			boolean valid = false;
			
			//these try/catches only catch one instance of incorrect input. fix later.
			//these try/catches work (i think) integrate into above method.
			while (!valid) {
				try {
					deptNum = scan.nextInt();
					valid = true;
				}
				catch(NoSuchElementException e) {
					System.out.println("Please enter a valid number");
					scan.nextLine();
				}
			}
			valid = false;
			System.out.println("Enter project number: ");
			while (!valid) {
				try {
					Pnumber = scan.nextInt();
					valid = true;
					}
				catch(NoSuchElementException e) {
					System.out.println("Please enter a valid number");
					scan.nextLine();
					}
			}
			System.out.println("Initializing table.....");
			try {
				stmt4 = myConn.createStatement();
				sql = "INSERT INTO company.project (Dnum, Plocation, Pname, Pnumber)"
						+ "VALUES ('"+deptNum+"', '"+PLocation+"', '"+PName+"', '"+Pnumber+"');";
				stmt4.executeUpdate(sql);
				System.out.println("Table initialized successfully!");
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	public static void insertEmp(Connection myConn) throws SQLException {
			Statement stmt4 = null;
			Scanner scan = new Scanner(System.in);
			String sql = null, address = null, fname = null, lname = null;
			int dno = 0, ssn =0;
			
			System.out.println("Enter employee address: ");
			address = scan.nextLine();
			
			System.out.println("Enter employee's first name: ");
			fname = scan.nextLine();
			
			System.out.println("Enter employee's last name: ");
			lname = scan.nextLine();
			
			System.out.println("Enter employee's department number: ");
			dno = scan.nextInt();
			
			System.out.println("Enter employee's ssn: ");
			ssn = scan.nextInt();
			scan.nextLine();
			
			System.out.println("Inserting employee.....");
			try {
				stmt4 = myConn.createStatement();
				sql =  "INSERT INTO company.employee(Address, Dno, Fname, Lname, Ssn)"
						+ "VALUES ('"+address+"', '"+dno+"', '"+fname+"', '"+lname+"', '"+ssn+"');";
				stmt4.executeUpdate(sql);
				System.out.println("Employee inserted successfully!");
			}
			catch (SQLIntegrityConstraintViolationException e) {
				System.out.println("This employee already exists. Type \"Cancel\" or \"insert anyway\"");
				String input = scan.nextLine();
				String sql1 = "";
				while (!(input.equalsIgnoreCase("cancel") || input.equalsIgnoreCase("insert anyway"))) {
					System.out.println("Valid input required. ");
					input = scan.nextLine();
				} 
				if (input.equalsIgnoreCase("cancel")) {
					System.out.println("Operation cancelled");
				}
				else if (input.equalsIgnoreCase("insert anyway")) {
					sql1 = "UPDATE company.employee "
							+ "SET Address = '"+address+"', Dno = '"+dno+"', Fname = '"+fname+"', "
									+ "Lname = '"+lname+"' WHERE employee.Ssn = "+ssn;
					stmt4.executeUpdate(sql1);
					System.out.println("Employee inserted.");
				}
							
			}
			
		}
		
	public static void insertWorksOn(Connection myConn) throws SQLException {

			Statement stmt4 = null;
			Scanner scan = new Scanner(System.in);
			String sql = null;
			String sql2 = null;
			String sql3 = null;
			String sql4 = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			int eSsn =0, hours =0, pNo = 0;
			
			
			System.out.println("Enter employee's ssn: ");
			eSsn = scan.nextInt();
			
			
			//this does not seem to work... fix later.
			sql2 = "SELECT e1.Ssn "
					+ "FROM company.employee e1, company.works_on w1 "
					+ "WHERE e1.Ssn = "+eSsn+";";
			rs1 = runQuery(myConn, sql2);
			
			if (!(rs1.next())) {
				System.out.println("Employee with ssn: "+eSsn+" does not exist.");
				
			}
			else {
			
			
		
			
				System.out.println("Enter project number: ");
				pNo = scan.nextInt();
				
				sql3 = "SELECT p1.Pnumber "
						+ "FROM company.project p1, company.works_on w1 "
						+ "WHERE p1.Pnumber = "+pNo+";";
				
				rs2 = runQuery(myConn, sql3);
				
				if (!(rs2.next())) {
					System.out.println("Project with number: "+pNo+" does not exist.");
					
				}
				else {
				
				
					System.out.println("Enter the number of hours that the employee works on the project: ");
					hours = scan.nextInt();
					
					System.out.println("Inserting information.....");
					
					sql4 = "SELECT w1.Essn, w1.Pno, w1.Hours "
							+ "FROM company.works_on w1 "
							+ "WHERE w1.Essn = "+eSsn+" AND w1.Pno = "+pNo+" AND w1.Hours = "+hours+";";
					rs2 = runQuery(myConn, sql4);
					
					if (rs2.next()) {
						System.out.println("This record already exists. Operation cancelled.");
						
					}
					else {
					
						try {
							stmt4 = myConn.createStatement();
							sql =  "INSERT INTO company.works_on(Essn, Hours, Pno)"
									+ "VALUES ('"+eSsn+"', '"+hours+"', '"+pNo+"');";
							stmt4.executeUpdate(sql);
							System.out.println("Information inserted successfully!");
						}
						catch(SQLIntegrityConstraintViolationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	
	public static void runQuery2(Connection myConn) throws SQLException {
		String sqlStr = "";
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a SQL query");
		sqlStr = scan.nextLine();
		ResultSet rs = runQuery(myConn, sqlStr);
		displayResultSet(rs);
		
		//finish this.....?
		
	}
		
	public static void dispData(Connection myConn) throws SQLException {
			String sqlStr3 = "SELECT * " + 
					"FROM company.employee;";
			ResultSet rs3 = runQuery(myConn, sqlStr3);
			displayResultSet(rs3);
			
			System.out.println("***********************************************************************");
			
			String sqlStr4 = "SELECT * " + 
					"FROM company.department;";
			ResultSet rs4 = runQuery(myConn, sqlStr4);
			displayResultSet(rs4);
			
			System.out.println("***********************************************************************");

			String sqlStr5 = "SELECT * " + 
					"FROM company.project;";
			ResultSet rs5 = runQuery(myConn, sqlStr5);
			displayResultSet(rs5);
			
			System.out.println("***********************************************************************");

			String sqlStr6 = "SELECT * " + 
					"FROM company.works_on;";
			ResultSet rs6 = runQuery(myConn, sqlStr6);
			displayResultSet(rs6);
		}
		


}	
	

