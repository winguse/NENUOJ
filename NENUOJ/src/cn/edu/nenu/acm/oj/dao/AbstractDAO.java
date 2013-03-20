package cn.edu.nenu.acm.oj.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public abstract class AbstractDAO<T extends Serializable> {
	
	protected static Logger log = LogManager.getLogger("DAO");
	
	protected Class<T> clazz;
	@PersistenceContext
	protected EntityManager em;

	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T findById(final Integer id) {
		return em.find(clazz, id);
	}

	public List<T> findAll() {
		return em.createQuery("from " + clazz.getName(),clazz).getResultList();
	}

	/**
	 * Only supported the defined named query in entity beans.
	 * 
	 * @param column
	 *            Java Style Name Column, like firstSecondThird.
	 * @param value
	 *            value looking for
	 * @return a list of result
	 */
	public List<T> findByColumn(final String column, final Object value) {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() + ".findBy" + column.substring(0, 1).toUpperCase()
				+ column.substring(1), clazz);
		query.setParameter(column, value);
		return query.getResultList();
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
