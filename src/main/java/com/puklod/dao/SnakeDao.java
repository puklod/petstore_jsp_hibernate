package com.puklod.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.puklod.conroller.HibernateUtil;
import com.puklod.entity.Snake;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class SnakeDao implements Dao<Snake>{
	SessionFactory factory = HibernateUtil.getEntityManagerFactory();
	Session session;
	Transaction txn;

	@Override
	public Snake get(int id) {
		session = factory.openSession();
		Snake snake = session.find(Snake.class, id);
		session.close();
		return snake;
	}

	@Override
	public List<Snake> getAll() {
		session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Snake> cq = cb.createQuery(Snake.class);
		Root<Snake> rootEntry = cq.from(Snake.class);
		CriteriaQuery<Snake> all = cq.select(rootEntry);
		TypedQuery<Snake> tq = session.createQuery(all);
		List<Snake> Snakes = tq.getResultList();
		session.close();
		
		return Snakes;
	}

	@Override
	public void save(Snake snake) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.persist(snake);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Snake snake) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			session.merge(snake);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Snake snake) {
		try {
			session = factory.openSession();
			txn = session.beginTransaction();
			Snake mergedSnake = session.find(Snake.class, snake.getId());
			session.remove(mergedSnake);
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			txn.rollback();
		} finally {
			session.close();
		}
	}

}
