package jdbcTest;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class JDBCDriver {


	public static void main(String[] args) throws SQLException{
		//Connection myConnection = null;
		Connection myConnection = JDBCmethods.getConnection("root", "");
		//String sqlStr1 = "select * from company.employee, company.department where employee.Dno = department.Dnumber";
		//String sqlStr2 = "select * from company.works_on";
		
		String input = "";
		int input2 = 1;
		Scanner scan = new Scanner(System.in);
		boolean valid = false;

		//ResultSet rs1 =runQuery(myConnection, sqlStr1);
		//ResultSet rs2 =runQuery(myConnection, sqlStr2);
		//displayResultSet(rs1);
		//displayResultSet(rs2);
		
		
		System.out.println();
		System.out.println();
		System.out.println("MENU: Enter a number (1-8) to perform the corresponding functions or enter 0 to quit:");
		System.out.println();
		System.out.println("************************************************************************************");
		System.out.println();
		System.out.println();
		System.out.println("1 - Create  database \'COMPANY\'");
		System.out.println("2 - Initialize department table");
		System.out.println("3 - Initialize project table");
		System.out.println("4 - Insert a new Employee");
		System.out.println("5 - Insert a tuple to works_on table");
		System.out.println("6 - Remove a works_on record");
		System.out.println("7 - Query Menu");
		System.out.println("8 - Check Database MetaData");
		
		while (!(valid && input2 ==0)) {
			try {
				input2 = scan.nextInt();
				while (!(input2 ==0 || input2 == 1 || input2 == 2 || input2 == 3 || input2 == 4 || input2 == 5 || input2 == 6 || input2 == 7 || input2 == 8))
				{
					System.out.println("Please enter an integer ranging from 1-8.");
					input2 = scan.nextInt();
				}
				switch (input2) {
				
				case 1: 
					JDBCmethods.createDatabase(myConnection);
					break;
					
				case 2: 
					JDBCmethods.initDept(myConnection);
					break;
					
				case 3: 
					JDBCmethods.initProject(myConnection);
					break;
					
				case 4: 
					JDBCmethods.insertEmp(myConnection);
					break;
					
				case 5: 
					JDBCmethods.insertWorksOn(myConnection);
					break;
					
				case 7:
					JDBCmethods.runQuery2(myConnection);
					break;
					
				case 8:
					JDBCmethods.dispData(myConnection);
					break;
				}
				valid = true;
			}
			catch (InputMismatchException e) {
				scan.next();
				System.out.println("You must enter an integer value.");
			}
		}
		
		
		
		/*Statement myStmt = myConnection.createStatement();
		String sqlStr = "select * from company.department";
		ResultSet rs = myStmt.executeQuery(sqlStr);
		while (rs.next()) {
			String dname = rs.getString("dname");
			String dnumber = rs.getString("dnumber");
			String ssn = rs.getString("mgr_ssn");
			String salary = rs.getString("mgr_start_date");
			//Employee e = new Employee(fname, lname, ssn, salary);
			//System.out.println(dname+"\t"+dnumber);
		}*/
	scan.close();
	}
	
}


 

