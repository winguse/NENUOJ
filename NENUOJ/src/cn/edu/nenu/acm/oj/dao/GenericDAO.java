package cn.edu.nenu.acm.oj.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GenericDAO {

	@PersistenceContext
	protected EntityManager em;

	/**
	 * @see EntityManager#find(Class, Object)
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T extends Serializable> T findById(final Integer id, final Class<T> clazz) {
		return em.find(clazz, id);
	}

	public <T extends Serializable> List<T> findAll(final Class<T> clazz) {
		return em.createQuery("from " + clazz.getName(), clazz).getResultList();
	}

	/**
	 * Only supported the defined named query in entity beans.
	 * 
	 * @param column
	 *            Java Style Name Column, like firstSecondThird.
	 * @param value
	 *            value looking for
	 * @param clazz
	 * @return a list of result
	 */
	public <T extends Serializable> List<T> findByColumn(final String column, final Object value, final Class<T> clazz) {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() + ".findBy" + column.substring(0, 1).toUpperCase()
				+ column.substring(1), clazz);
		query.setParameter(column, value);
		return query.getResultList();
	}

	public <T extends Serializable> List<T> namedQuery(final String namedQuery, String[] names, Object[] values,
			final Class<T> clazz) {
		TypedQuery<T> query = em.createNamedQuery(namedQuery, clazz);
		int parameterLength = 0;
		if (names != null && values != null)
			parameterLength = names.length > values.length ? names.length : values.length;
		for (int i = 0; i < parameterLength; i++) {
			query.setParameter(names[i], values[i]);
		}
		return query.getResultList();
	}

	@Transactional
	public <T extends Serializable> void persist(final T entity) {
		em.persist(entity);
	}

	@Transactional
	public <T extends Serializable> T merge(final T entity) {
		return em.merge(entity);
	}

	@Transactional
	public <T extends Serializable> void delete(final T entity) {
		em.remove(entity);
	}

	public <T extends Serializable> void deleteById(final Integer entityId, final Class<T> clazz) {
		final T entity = findById(entityId, clazz);
		delete(entity);
	}

}
