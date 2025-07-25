package com.smartqueue;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminPanelServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
