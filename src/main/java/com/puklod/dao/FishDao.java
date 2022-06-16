package com.puklod.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.puklod.conroller.HibernateUtil;
import com.puklod.entity.Fish;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class FishDao implements Dao<Fish>{
	SessionFactory factory = HibernateUtil.getEntityManagerFactory();
	Session session;
	Transaction txn;

	@Override
	public Fish get(int id) {
		session = factory.openSession();
		Fish fish = session.find(Fish.class, id);
		session.close();
		return fish;
	}

	@Override
	public List<Fish> getAll() {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Fish> cq = cb.createQuery(Fish.class);
		Root<Fish> rootEntry = cq.from(Fish.class);
		CriteriaQuery<Fish> all = cq.select(rootEntry);
		TypedQuery<Fish> tq = session.createQuery(all);
		List<Fish> Fishs = tq.getResultList();
		session.close();
		
		return Fishs;
	}

	@Override
	public void save(Fish fish) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.persist(fish);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Fish fish) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.merge(fish);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Fish fish) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			Fish mergedFish = session.find(Fish.class, fish.getId());
			session.remove(mergedFish);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

}
