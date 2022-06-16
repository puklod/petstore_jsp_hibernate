package com.puklod.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.puklod.conroller.HibernateUtil;
import com.puklod.entity.Dog;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DogDao implements Dao<Dog>{
	SessionFactory factory = HibernateUtil.getEntityManagerFactory();
	Session session;
	Transaction txn;

	@Override
	public Dog get(int id) {
		session = factory.openSession();
		Dog dog = session.find(Dog.class, id);
		session.close();
		return dog;
	}

	@Override
	public List<Dog> getAll() {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Dog> cq = cb.createQuery(Dog.class);
		Root<Dog> rootEntry = cq.from(Dog.class);
		CriteriaQuery<Dog> all = cq.select(rootEntry);
		TypedQuery<Dog> tq = session.createQuery(all);
		List<Dog> dogs = tq.getResultList();
		session.close();
		
		return dogs;
	}

	@Override
	public void save(Dog dog) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.persist(dog);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Dog dog) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.merge(dog);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Dog dog) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			Dog mergedDog = session.find(Dog.class, dog.getId());
			session.remove(mergedDog);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

}
