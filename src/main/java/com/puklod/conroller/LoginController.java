package com.puklod.conroller;

import java.io.IOException;

import com.puklod.dao.UserDao;
import com.puklod.entity.User;

import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao;
	
	public void init() {
		userDao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("login") != null) {
			login(request,response);
		} else if (request.getParameter("logout") != null) {
			logout(request,response);
		}
		
		response.sendRedirect(request.getHeader("referer"));
		
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		User user;
		
		try {
			user = userDao.get(request.getParameter("userName"), request.getParameter("password"));
			session = request.getSession();
			session.setAttribute("username", user.getUserName());
			session.setAttribute("privileg", user.getPrivileg());
		} catch (NoResultException e) {
			request.getSession().setAttribute("loginErrorMessage", "Hibás felhasználónév vagy jelszó");
		} catch (NullPointerException e) {
			request.getSession().setAttribute("loginErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.getSession().setAttribute("loginErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
}
