package com.puklod.conroller;

import java.io.IOException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import com.puklod.exception.ExistingStoreException;
import com.puklod.exception.RequiredFieldEmptyException;

import java.util.Iterator;
import java.util.List;

import com.puklod.dao.StoreDao;
import com.puklod.entity.Store;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/stores")
public class StoreController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher;
	private StoreDao storeDao;
	
	@Override
	public void init() {
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
				abort(request, response);
			}
		}
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestAttributes(request);

		dispatcher = request.getRequestDispatcher("views/stores.jsp");
		dispatcher.forward(request, response);
		
		removeLoginErrorMessage(request);
	}

	private void setRequestAttributes(HttpServletRequest request) throws IOException {
		setEntityList(request);
	}

	private void setEntityList(HttpServletRequest request) {

		List<Store> listStore = null;

		try {
			listStore = storeDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}

		request.setAttribute("listEntity", listStore);
	}

	private void removeLoginErrorMessage(HttpServletRequest request) {
			request.getSession().removeAttribute("loginErrorMessage");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Store store = setEntity(request);
			store.validate();
			storeDao.save(store);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + req.getMessage());
		} catch (ExistingStoreException e) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + e.getMessage());
		} finally {
			getAll(request, response);
		}

	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Store store = new Store();

		try {
			store = setEntity(request);
			store.validate();
			storeDao.update(store);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + request.getParameter("id") + " frissítése közben: " + req.getMessage());
		} catch (ExistingStoreException e) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + request.getParameter("id") + " frissítése közben: " + e.getMessage());
		} finally {
			getAll(request, response);
		}
	}

	private Store setEntity(HttpServletRequest request) throws ExistingStoreException {
		Store store = new Store();
		
		store.setId(request.getParameter("id"));
		store.setName(request.getParameter("name"));
		store.setAddress(request.getParameter("address"));
		
		if(isStoreExistsInTheDatabase(store)) {
			throw new ExistingStoreException("Az adott üzlet már szerepel az adatbázisban!");
		}
	
		return store;
	}
	
	private boolean isStoreExistsInTheDatabase(Store store) {
			
		try {
			if(storeDao.get(store.getName(),store.getAddress()) != null){
				return true;	
			}else {
				return false;
			}
		} catch(NoResultException e) {
			return false;
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Store store = storeDao.get(Integer.parseInt(request.getParameter("delete")));
		storeDao.delete(store);
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
