package com.BankManagement;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uname = req.getParameter("uname");
		String password = req.getParameter("password");
		
		//Database connection
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//load class
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//connect to DB
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankDB", null);
			
			//check if user exists
			String sql = "SELECT * FROM users WHERE username=? AND password=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1,uname);
			pstmt.setString(2,password);
			
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
			
				//start session
				HttpSession session = req.getSession();
				session.getAttribute("Uname"+uname);
				
				//redirect to dashboard
				resp.sendRedirect("dashboard.jsp");
				
			}else {
				PrintWriter out = resp.getWriter();
				out.println("<h3 style='color:red'>Invalid username or password</h3>");
				
				//redirect to login page
				resp.sendRedirect("login.html?error=invalid");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().println("Error: " + e.getMessage());
			
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (c != null) c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

}
