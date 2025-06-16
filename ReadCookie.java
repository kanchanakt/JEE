package com.tap.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ReadCookie")
public class ReadCookie extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		
		Cookie[] cookies = req.getCookies();
		
		for(int i=0; i<cookies.length; i++) {
			String name = cookies[0].getName();
			writer.println("<h3>name: "+ name +"</h3>");
			
			String value = cookies[1].getValue();
			writer.println("<h3>value: "+ value +"</h3>");
		}
		
	}
       
    

}
