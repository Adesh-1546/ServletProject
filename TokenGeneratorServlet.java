package com.smartqueue;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/generateToken")
public class TokenGeneratorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		String mobno = req.getParameter("mobno");
		long phone = Long.parseLong(mobno);
		String serviceType = req.getParameter("service");

		Connection c = null;
		PreparedStatement UserPstmt = null;
		PreparedStatement TokenPstmt = null;
		ResultSet rs = null;
		ResultSet tokenRs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/queue_db", "root", "adeshpol141546");

			// Insert into users
			String UserSqlQuery = "INSERT INTO users (name, phone) VALUES (?, ?)";
			UserPstmt = c.prepareStatement(UserSqlQuery, Statement.RETURN_GENERATED_KEYS);
			UserPstmt.setString(1, name);
			UserPstmt.setLong(2, phone);
			UserPstmt.executeUpdate();

			rs = UserPstmt.getGeneratedKeys();
			int userId = 0;
			if (rs.next()) {
				userId = rs.getInt(1);
			}

			// Insert into tokens
			String TokenSqlQuery = "INSERT INTO tokens (user_id, service_type, status) VALUES (?, ?, ?)";
			TokenPstmt = c.prepareStatement(TokenSqlQuery, Statement.RETURN_GENERATED_KEYS);
			TokenPstmt.setInt(1, userId);
			TokenPstmt.setString(2, serviceType);
			TokenPstmt.setString(3, "WAITING");
			TokenPstmt.executeUpdate();

			tokenRs = TokenPstmt.getGeneratedKeys();
			int tokenId = 0;
			if (tokenRs.next()) {
				tokenId = tokenRs.getInt(1);
			}

			// Response
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<h2>Your Token Number is: " + tokenId + "</h2>");
			out.println("<p>Please wait. You'll be called shortly.</p>");
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().println("Error Generating Token: " + e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (tokenRs != null) tokenRs.close();
				if (UserPstmt != null) UserPstmt.close();
				if (TokenPstmt != null) TokenPstmt.close();
				if (c != null) c.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
