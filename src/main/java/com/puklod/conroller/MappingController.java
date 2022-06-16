package com.puklod.conroller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class MappingController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public void init() {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equals("/")) {
			request.getRequestDispatcher("views/index.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("views/" + request.getServletPath() + ".jsp").forward(request, response);
		}
		
		request.getSession().removeAttribute("loginErrorMessage");
	}	
}
