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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get patient and doctor IDs from the form
        int patientId = Integer.parseInt(req.getParameter("patient_id"));
        int doctorId = Integer.parseInt(req.getParameter("doctor_id"));

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int newTokenNo = 1;

        try {
            conn = DB_connection.getConnection();

            // Get last token number for the doctor today
            String query = "SELECT MAX(token_no) AS last_token FROM tokens WHERE doctor_id = ? AND DATE(time_generated) = CURDATE()";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, doctorId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int lastToken = rs.getInt("last_token");
                newTokenNo = lastToken + 1;
            }

            // Insert new token into DB
            String insertQuery = "INSERT INTO tokens (patient_id, doctor_id, token_no) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setInt(3, newTokenNo);
            pstmt.executeUpdate();

            // Send response
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<h2>Token Generated Successfully!</h2>");
            out.println("<p>Your Token Number: <strong>" + newTokenNo + "</strong></p>");
            out.println("<a href='index.jsp'>Back to Home</a>");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Token generation failed.");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}

