package com.puklod.conroller;

import java.io.IOException;
import jakarta.persistence.PersistenceException;

import java.util.Iterator;
import java.util.List;

import com.puklod.dao.DogDao;
import com.puklod.dao.StoreDao;
import com.puklod.entity.Dog;
import com.puklod.entity.Store;
import com.puklod.exception.MinimumValueException;
import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dogs")
public class DogController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher;
	private DogDao dogDao;
	private StoreDao storeDao;

	public void init() {
		dogDao = new DogDao();
		storeDao = new StoreDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		getAll(request, response);
	}

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

		dispatcher = request.getRequestDispatcher("views/dogs.jsp");
		dispatcher.forward(request, response);
		
		removeLoginErrorMessage(request);
	}

	private void setRequestAttributes(HttpServletRequest request) throws IOException {
		setEntityList(request);
		setStoreList(request);
	}

	private void setEntityList(HttpServletRequest request) {

		List<Dog> listDog = null;

		try {
			listDog = dogDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}

		request.setAttribute("listEntity", listDog);
	}

	private void setStoreList(HttpServletRequest request) {

		List<Store> listStore = null;

		try {
			listStore = storeDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}

		request.setAttribute("listStore", listStore);
	}

	private void removeLoginErrorMessage(HttpServletRequest request) {
			request.getSession().removeAttribute("loginErrorMessage");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Dog dog = setEntity(request);
			dog.validate();
			dogDao.save(dog);
		} catch (MinimumValueException min) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + min.getMessage());
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Dog dog = setEntity(request);

		try {
			dog.validate();
			dogDao.update(dog);
		} catch (MinimumValueException min) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + dog.getId() + " frissítése közben: " + min.getMessage());
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + dog.getId() + " frissítése közben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private Dog setEntity(HttpServletRequest request) {

		Dog dog = new Dog();
		int storeId = Integer.parseInt(request.getParameter("store"));

		dog.setId(request.getParameter("id"));
		dog.setAge(request.getParameter("age"));
		dog.setType(request.getParameter("type"));
		dog.setFurColor(request.getParameter("furColor"));
		dog.setFurLength(request.getParameter("furLength"));
		dog.setSize(request.getParameter("size"));
		dog.setStore(storeDao.get(storeId));

		return dog;
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Dog dog = dogDao.get(Integer.parseInt(request.getParameter("delete")));
		dogDao.delete(dog);
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
