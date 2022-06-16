package com.puklod.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.puklod.conroller.HibernateUtil;
import com.puklod.entity.User;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class UserDao implements Dao<User> {
	SessionFactory factory = HibernateUtil.getEntityManagerFactory();
	Session session;
	Transaction txn;
	
	@Override
	public User get(int id) {
		session = factory.openSession();
		User user = session.find(User.class, id);
		session.close();
		return user;
	}
	
	public User get(String userName, String password) throws NoResultException{
		try {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> from = cq.from(User.class);
		cq.select(from);
		cq.where(
				cb.equal(from.get("userName"),userName),
				cb.equal(from.get("password"),password)
		);
		TypedQuery<User> result = session.createQuery(cq);
		return result.getSingleResult();
		} finally {
			session.close();
		}
	}
	
	public User get(String userName) throws NoResultException{
		try {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> from = cq.from(User.class);
		cq.select(from);
		cq.where(
				cb.equal(from.get("userName"),userName)
		);
		TypedQuery<User> result = session.createQuery(cq);
		return result.getSingleResult();
		} finally {
			session.close();
		}
	}
		
	@Override
	public List<User> getAll() {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> rootEntry = cq.from(User.class);
		CriteriaQuery<User> all = cq.select(rootEntry);
		TypedQuery<User> tq = session.createQuery(all);
		List<User> users = tq.getResultList();
		session.close();
		
		return users;
	}
	
	@Override
	public void save(User user) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.persist(user);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	
	@Override
	public void update(User user) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.merge(user);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(User user) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			User mergedUser = session.find(User.class, user.getId());
			session.remove(mergedUser);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

}
