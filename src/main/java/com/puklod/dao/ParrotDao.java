package com.puklod.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.puklod.conroller.HibernateUtil;
import com.puklod.entity.Parrot;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ParrotDao implements Dao<Parrot>{
	SessionFactory factory = HibernateUtil.getEntityManagerFactory();
	Session session;
	Transaction txn;

	@Override
	public Parrot get(int id) {
		session = factory.openSession();
		Parrot parrot = session.find(Parrot.class, id);
		session.close();
		return parrot;
	}

	@Override
	public List<Parrot> getAll() {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Parrot> cq = cb.createQuery(Parrot.class);
		Root<Parrot> rootEntry = cq.from(Parrot.class);
		CriteriaQuery<Parrot> all = cq.select(rootEntry);
		TypedQuery<Parrot> tq = session.createQuery(all);
		List<Parrot> parrots = tq.getResultList();
		session.close();
		
		return parrots;
	}

	@Override
	public void save(Parrot parrot) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.persist(parrot);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Parrot parrot) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.merge(parrot);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Parrot parrot) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			Parrot mergedParrot = session.find(Parrot.class, parrot.getId());
			session.remove(mergedParrot);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

}
