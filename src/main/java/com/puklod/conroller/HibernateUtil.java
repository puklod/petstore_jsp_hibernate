package com.puklod.conroller;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(value="/HibernateUtil", loadOnStartup=1)
public class HibernateUtil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
private static SessionFactory factory;
	
	
	public HibernateUtil() {
		super();
	}
	
	public void init() {
	
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch(Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
		
	public static SessionFactory getEntityManagerFactory() {

		return factory;
		
		
	}

}
