package cn.edu.nenu.acm.oj.dao;

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * This is the Object based DAO, Base on Hibernate API. However, this class is
 * using ugly method to get the Hibernate Session from JAP configured context.
 * So, I am deciding to remove the use of it.
 * 
 * @author Winguse
 * @param <T>
 */
@Repository
//@Scope("request")
@SuppressWarnings("unchecked")
@Deprecated
public class HibernateDAO<T extends Serializable> implements InitializingBean, DisposableBean {
	protected static Logger log = LogManager.getLogger("HibernateDAO");
	private Class<T> clazz;
	private Session session;

	@Autowired(required = true)
	private EntityManagerFactory emf;
	private EntityManager em;

	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T findById(final Integer id) {
		return (T) session.get(clazz, id);
	}

	public List<T> findByExample(final T example) {
		return (List<T>) session.createCriteria(clazz).add(create(example)).list();
	}

	public List<T> findAll() {
		return session.createQuery("from " + clazz.getName()).list();
	}

	public void persist(final T entity) {
		session.getTransaction().begin();
		try {
			session.persist(entity);
			session.getTransaction().commit();
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
			log.error("persist failed: " + entity.getClass().getName());
		}
	}

	public T merge(final T entity) {
		return (T) session.merge(entity);
	}

	public void delete(final T entity) {
		session.getTransaction().begin();
		try {
			session.delete(entity);
			session.getTransaction().commit();
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
			log.error("delete failed: " + entity.getClass().getName());
		}
	}

	public void deleteById(final Integer entityId) {
		final T entity = findById(entityId);
		delete(entity);
	}

	public void attachDirty(Object instance) {
		session.saveOrUpdate(instance);
	}

	public void attachClean(Object instance) {
		session.buildLockRequest(LockOptions.NONE).lock(instance);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		em = emf.createEntityManager();
		session = em.unwrap(Session.class);
	}

	@Override
	public void destroy() throws Exception {
		em.close();
	}

}
