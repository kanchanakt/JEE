package com.tap.student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Validation extends HttpServlet {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet res = null;
	String url = null;
	String un = null;
	String pwd = null;
	
	@Override
	public void init(ServletConfig sc) throws ServletException {
		
		ServletContext scon = sc.getServletContext();
		url = scon.getInitParameter("url");
		un= scon.getInitParameter("username");
		pwd= scon.getInitParameter("password");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,un,pwd);		
		} catch(Exception e) {
			e.printStackTrace();
	    }
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		try {
			// login
			String query = "select * from student where un = ? and pwd = ?" ;
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			res = pstmt.executeQuery();
			if(res.next() == true) {
				
				writer.println("<h3>welcome to skyllx</h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/drive");
				rd.forward(req, resp);

			}else {
				RequestDispatcher rd=req.getRequestDispatcher("/invalidlogin.html");
				rd.forward(req, resp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
