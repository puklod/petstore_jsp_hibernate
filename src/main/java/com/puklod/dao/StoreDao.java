package com.puklod.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.puklod.conroller.HibernateUtil;
import com.puklod.entity.Store;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class StoreDao implements Dao<Store> {
	
	SessionFactory factory = HibernateUtil.getEntityManagerFactory();
	Session session;
	Transaction txn;

	@Override
	public Store get(int id) {
		session = factory.openSession();
		Store store = session.find(Store.class, id);
		session.close();
		return store;
	}
	
	public Store get(String name, String address) throws NoResultException{
		try {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Store> cq = cb.createQuery(Store.class);
		Root<Store> from = cq.from(Store.class);
		cq.select(from);
		cq.where(
				cb.equal(from.get("name"),name),
				cb.equal(from.get("address"),address)
		);
		TypedQuery<Store> result = session.createQuery(cq);
		return result.getSingleResult();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Store> getAll() {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Store> cq = cb.createQuery(Store.class);
		Root<Store> rootEntry = cq.from(Store.class);
		CriteriaQuery<Store> all = cq.select(rootEntry);
		TypedQuery<Store> tq = session.createQuery(all);
		List<Store> stores = tq.getResultList();
		session.close();
		
		return stores;
	}
	
	@Override
	public void save(Store store) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.persist(store);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Store store) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.merge(store);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Store store) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			Store mergedStore = session.find(Store.class, store.getId());
			session.remove(mergedStore);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

}
