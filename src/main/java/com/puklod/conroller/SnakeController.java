package com.puklod.conroller;

import java.io.IOException;
import jakarta.persistence.PersistenceException;

import java.util.Iterator;
import java.util.List;

import com.puklod.dao.SnakeDao;
import com.puklod.dao.StoreDao;
import com.puklod.entity.Snake;
import com.puklod.entity.Store;
import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/snakes")
public class SnakeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher;
	private SnakeDao snakeDao;
	private StoreDao storeDao;
	
	@Override
	public void init() {
		snakeDao = new SnakeDao();
		storeDao = new StoreDao();
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
				abort(request,response);
			}
		}
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestAttributes(request);

		dispatcher = request.getRequestDispatcher("views/snakes.jsp");
		dispatcher.forward(request, response);
		
		removeLoginErrorMessage(request);
	}

	private void setRequestAttributes(HttpServletRequest request) throws IOException {
		setEntityList(request);
		setStoreList(request);
	}

	private void setEntityList(HttpServletRequest request) {

		List<Snake> listSnake = null;

		try {
			listSnake = snakeDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatb??ziskapcsolattal! K??rj??k, pr??b??lokzzon k??s??bb!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatb??ziskapcsolattal! K??rj??k, pr??b??lokzzon k??s??bb!");
		}

		request.setAttribute("listEntity", listSnake);
	}

	private void setStoreList(HttpServletRequest request) {

		List<Store> listStore = null;

		try {
			listStore = storeDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatb??ziskapcsolattal! K??rj??k, pr??b??lokzzon k??s??bb!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatb??ziskapcsolattal! K??rj??k, pr??b??lokzzon k??s??bb!");
		}

		request.setAttribute("listStore", listStore);
	}

	private void removeLoginErrorMessage(HttpServletRequest request) {
			request.getSession().removeAttribute("loginErrorMessage");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Snake snake = setEntity(request);
			snake.validate();
			snakeDao.save(snake);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("saveErrorMessage", "Hiba az ??j bejegyz??s hozz??ad??sa k??zben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Snake snake = setEntity(request);

		try {
			snake.validate();
			snakeDao.update(snake);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + snake.getId() + " friss??t??se k??zben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private Snake setEntity(HttpServletRequest request) {

		Snake snake = new Snake();
		Store store = new Store();
		store.setId(request.getParameter("store"));

		snake.setId(request.getParameter("id"));
		snake.setType(request.getParameter("type"));
		snake.setColor(request.getParameter("color"));
		snake.setSize(request.getParameter("size"));
		snake.setIsVenomous(request.getParameter("isVenomous"));
		snake.setStore(storeDao.get(store.getId()));

		return snake;
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Snake snake = snakeDao.get(Integer.parseInt(request.getParameter("delete")));
		snakeDao.delete(snake);
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
