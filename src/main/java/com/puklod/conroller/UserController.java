package com.puklod.conroller;

import java.io.IOException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.Iterator;
import java.util.List;

import com.puklod.dao.UserDao;
import com.puklod.entity.User;
import com.puklod.exception.ExistingUserException;
import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher;
	private UserDao userDao;
	
	@Override
	public void init() {
		userDao = new UserDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		getAll(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Iterator<String> iterator = request.getParameterNames().asIterator();

		while (iterator.hasNext()) {
			switch (iterator.next()) {
			case "save":
				save(request, response);
				break;
			case "update":
				update(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
			case "edit":
				edit(request, response);
				break;
			case "abort":
				abort(request, response);
			}
		}
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestAttributes(request);

		dispatcher = request.getRequestDispatcher("views/users.jsp");
		dispatcher.forward(request, response);

		removeLoginErrorMessage(request);
	}

	private void setRequestAttributes(HttpServletRequest request) throws IOException {
		setEntityList(request);
	}

	private void setEntityList(HttpServletRequest request) {

		List<User> listUser = null;

		try {
			listUser = userDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}

		request.setAttribute("listEntity", listUser);
	}

	private void removeLoginErrorMessage(HttpServletRequest request) {
		request.getSession().removeAttribute("loginErrorMessage");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			User user = setEntity(request);
			user.validate();
			userDao.save(user);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + req.getMessage());
		} catch (ExistingUserException e) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + e.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();

		try {
			user = setEntity(request);
			user.validate();
			userDao.update(user);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + request.getParameter("id") + " frissítése közben: " + req.getMessage());
		} catch (ExistingUserException e) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + request.getParameter("id") + " frissítése közben: " + e.getMessage());
		} finally {
			getAll(request, response);
		}
	}

	private User setEntity(HttpServletRequest request) throws ExistingUserException {
		User user = new User();
		
		user.setId(request.getParameter("id"));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setPrivileg(request.getParameter("privileg"));
		
		if(isUserExistsInTheDatabase(user)) {
			throw new ExistingUserException("Az adott felhasználónév már foglalt!");
		}
	
		return user;
	}
	
	private boolean isUserExistsInTheDatabase(User user) {
		User databaseUser;
		int userId = user.getId();
		String userName = user.getUserName();
		
		try {
			databaseUser = userDao.get(user.getUserName());
			int databaseUserId = databaseUser.getId();
			String databaseUserName = databaseUser.getUserName();
			
			if(databaseUserName.equals(userName) && databaseUserId != userId) {
				return true;
			} else {
				return false;
			}
		} catch(NoResultException e) {
			return false;
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = userDao.get(Integer.parseInt(request.getParameter("delete")));
		userDao.delete(user);
		getAll(request, response);
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("editId", request.getParameter("edit"));
		getAll(request, response);
	}

	private void abort(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.removeAttribute("editId");
		getAll(request, response);
	}

}
