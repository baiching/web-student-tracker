package com.baiching.web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
	
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		// create student db utill
		
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list the students 
		try {
			listStudents(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get students from db util
		List<Student> students = studentDbUtil.getStudents();
		
		//add students to the request
		request.setAttribute("student_list", students);
		
		//send to Jsp page(view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
