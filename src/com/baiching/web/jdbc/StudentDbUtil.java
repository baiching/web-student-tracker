package com.baiching.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents()  throws Exception {
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		// get a connection
		
		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM student;";
			
			myStmt = myConn.createStatement();
			//execute query
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				students.add(tempStudent);
			}
			
			return students;
			
		} finally {
			close(myConn, myStmt, myRs);
		} 
		
		//return students;
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myConn != null) {
				myConn.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myRs != null) {
				myRs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
