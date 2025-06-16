package com.tap.student;

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

public class Eligible extends HttpServlet {
	
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
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		PrintWriter writer = resp.getWriter();
		
		try {		
			String query = "select *from student where un = ? and pwd = ?";	
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet res = pstmt.executeQuery();
			res.next();
			
			int ten = res.getInt(3);
			int twe = res.getInt(4);
			int grad = res.getInt(5);
			
			String query2 = "select *from drives where 10th <= ? and 12th  <= ? and grad <= ?";
			PreparedStatement pstmt2 = con.prepareStatement(query2);
			
			pstmt2.setInt(1, ten);
			pstmt2.setInt(2, twe);
			pstmt2.setInt(3, grad);
			ResultSet res2 = pstmt2.executeQuery();
			
			
			 writer.println("<h3> "+ res.getString(2)+" " + "Drives your eligible for:</h3>");
			 writer.println("<table border=1>\r\n"
			    		+ "	<tr>\r\n"
			    		+ "		<th>id</th>\r\n"
			    		+ "		<th>name</th>\r\n"
			    		+ "		<th>10th</th>\r\n"
			    		+ "		<th>12th</th>\r\n"
			    		+ "		<th>grad</th>\r\n"
			    		+ "		<th>profile</th>\r\n"
			    		+ "		<th>package</th>\r\n"
			    		+ "		<th>skills</th>		\r\n"
			    		+ "	</tr>");
			    	    
			    
			    while(res2.next() == true) {
			    	int id = res2.getInt(1);
			    	String name = res2.getString(2);
			    	int ten1 = res2.getInt(3);
			    	int twe1 = res2.getInt(4);
			    	int grad1 = res2.getInt(5);
			    	String profile = res2.getString(6);
			    	float pac = res2.getFloat(7);
			    	String skills = res2.getString(8);
			    	
			    	writer.println("<tr>\r\n"
			    			+ "		<td>"+ id +" </td>\r\n"
			    			+ "		<td>"+ name +" </td>\r\n"
			    			+ "		<td>"+ ten1 +" </td>\r\n"
			    			+ "		<td>"+ twe1 +" </td>\r\n"
			    			+ "		<td>"+ grad1 +"</td>\r\n"
			    			+ "		<td>"+ profile +"</td>\r\n"
			    			+ "		<td>"+ pac +"</td>\r\n"
			    			+ "		<td>"+ skills +"</td>\r\n"
			    			+ "	</tr>")	;
			    }
			    
			    writer.println("</table>");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
