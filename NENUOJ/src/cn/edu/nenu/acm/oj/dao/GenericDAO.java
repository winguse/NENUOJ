package cn.edu.nenu.acm.oj.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope("request")
@SuppressWarnings("unchecked")
public class GenericDAO<T extends Serializable> {
	private Class<T> clazz;
	@PersistenceContext
	EntityManager em;

	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T findById(final Integer id) {
		return em.find(clazz, id);
	}

	public List<T> findAll() {
		return em.createQuery("from " + clazz.getName()).getResultList();
	}
	
	@Transactional
	public void persist(final T entity) {
		em.persist(entity);
	}
	
	@Transactional
	public T merge(final T entity) {
		return em.merge(entity);
	}
	
	@Transactional
	public void delete(final T entity) {
		em.remove(entity);
	}

	public void deleteById(final Integer entityId) {
		final T entity = findById(entityId);
		delete(entity);
	}
	
}
