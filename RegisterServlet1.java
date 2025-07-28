package com.BankManagement;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.*;


@WebServlet("/register")
public class RegisterServlet1 extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String name = req.getParameter("name");
		
		String mobno = req.getParameter("mobno");
		long phoneno = Long.parseLong(mobno);
		
		String email = req.getParameter("email");
		
		String adhar = req.getParameter("adhar");
		
		String pan = req.getParameter("pan");
		
		String income = req.getParameter("income");
		double incomey1 = Double.parseDouble(income);
		
		String dob = req.getParameter("dob");
		
		String gender = req.getParameter("gender");
		
		String city = req.getParameter("city");
		
		String state = req.getParameter("state");
		
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankManagement", "root", "adeshpol141546");

            String sql = "INSERT INTO user_details ( name, phoneno,email, adhar, pan, income, dob, gender, city, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = c.prepareStatement(sql);
            
            pstmt.setString(1, name);
            pstmt.setLong(2, phoneno);
            pstmt.setString(3, email);
            pstmt.setString(4, adhar);
            pstmt.setString(5, pan);
            pstmt.setDouble(6, incomey1);
            pstmt.setString(7, dob);
            pstmt.setString(8, gender);
            pstmt.setString(9, city);
            pstmt.setString(10, state);
            
//            System.out.println("Name: " + name);
//            System.out.println("Phone: " + phoneno);
//            System.out.println("Email: " + email);
//            
            pstmt.executeUpdate();
            
            

            c.close();
            resp.sendRedirect("success.html"); // or dashboard/home page
			
		} catch (Exception e) {
			e.printStackTrace();
            resp.getWriter().println("Error saving details: " + e.getMessage());
		}finally{
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
