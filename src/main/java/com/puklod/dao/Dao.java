package com.puklod.dao;

import java.util.Collection;


public interface Dao<T> {
	T get(int id);
	Collection<T> getAll();
	void save(T t);
	void update(T t);
	void delete(T t);
}
