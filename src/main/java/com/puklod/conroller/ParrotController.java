package com.puklod.conroller;

import java.io.IOException;
import jakarta.persistence.PersistenceException;

import java.util.Iterator;
import java.util.List;

import com.puklod.dao.ParrotDao;
import com.puklod.dao.StoreDao;
import com.puklod.entity.Parrot;
import com.puklod.entity.Store;
import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/parrots")
public class ParrotController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher;
	private ParrotDao parrotDao;
	private StoreDao storeDao;

	public void init() {
		parrotDao = new ParrotDao();
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

		dispatcher = request.getRequestDispatcher("views/parrots.jsp");
		dispatcher.forward(request, response);
		
		removeLoginErrorMessage(request);
	}

	private void setRequestAttributes(HttpServletRequest request) throws IOException {
		setEntityList(request);
		setStoreList(request);
	}

	private void setEntityList(HttpServletRequest request) {

		List<Parrot> listParrot = null;

		try {
			listParrot = parrotDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}

		request.setAttribute("listEntity", listParrot);
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
			Parrot parrot = setEntity(request);
			parrot.validate();
			parrotDao.save(parrot);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Parrot parrot = setEntity(request);

		try {
			parrot.validate();
			parrotDao.update(parrot);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + parrot.getId() + " frissítése közben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private Parrot setEntity(HttpServletRequest request) {

		Parrot parrot = new Parrot();
		int storeId = Integer.parseInt(request.getParameter("store"));

		parrot.setId(request.getParameter("id"));
		parrot.setType(request.getParameter("type"));
		parrot.setFeatherColor(request.getParameter("featherColor"));
		parrot.setSize(request.getParameter("size"));
		parrot.setCageSizeRequirment(request.getParameter("cageSizeRequirment"));
		parrot.setCanTalk(request.getParameter("canTalk"));
		parrot.setStore(storeDao.get(storeId));

		return parrot;
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Parrot parrot = parrotDao.get(Integer.parseInt(request.getParameter("delete")));
		parrotDao.delete(parrot);
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
