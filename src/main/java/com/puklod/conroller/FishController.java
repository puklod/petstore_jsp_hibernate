package com.puklod.conroller;

import java.io.IOException;
import jakarta.persistence.PersistenceException;

import java.util.Iterator;
import java.util.List;

import com.puklod.dao.FishDao;
import com.puklod.dao.StoreDao;
import com.puklod.entity.Fish;
import com.puklod.entity.Store;
import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/fishes")
public class FishController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher;
	private FishDao fishDao;
	private StoreDao storeDao;
	
	@Override
	public void init() {
		fishDao = new FishDao();
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

		dispatcher = request.getRequestDispatcher("views/fishes.jsp");
		dispatcher.forward(request, response);
		
		removeLoginErrorMessage(request);
	}

	private void setRequestAttributes(HttpServletRequest request) throws IOException {
		setEntityList(request);
		setStoreList(request);
	}

	private void setEntityList(HttpServletRequest request) {

		List<Fish> listFish = null;

		try {
			listFish = fishDao.getAll();
		} catch (NullPointerException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		} catch (PersistenceException e) {
			request.setAttribute("databaseErrorMessage", "Hiba az adatbáziskapcsolattal! Kérjük, próbálokzzon később!");
		}

		request.setAttribute("listEntity", listFish);
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
			Fish fish = setEntity(request);
			fish.validate();
			fishDao.save(fish);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("saveErrorMessage", "Hiba az új bejegyzés hozzáadása közben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Fish fish = setEntity(request);

		try {
			fish.validate();
			fishDao.update(fish);
		} catch (RequiredFieldEmptyException req) {
			request.setAttribute("updateErrorMessage",
					"Hiba a #" + fish.getId() + " frissítése közben: " + req.getMessage());
		} finally {
			getAll(request, response);
		}

	}

	private Fish setEntity(HttpServletRequest request) {

		Fish fish = new Fish();
		Store store = new Store();
		store.setId(request.getParameter("store"));

		fish.setId(request.getParameter("id"));
		fish.setType(request.getParameter("type"));
		fish.setColor(request.getParameter("color"));
		fish.setSize(request.getParameter("size"));
		fish.setTankSizeRequirment(request.getParameter("tankSizeRequirment"));
		fish.setStore(storeDao.get(store.getId()));

		return fish;
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Fish fish = fishDao.get(Integer.parseInt(request.getParameter("delete")));
		fishDao.delete(fish);
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
