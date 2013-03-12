package cn.edu.nenu.acm.oj.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockOptions;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DAOTesting {
	protected static Logger log = LogManager.getLogger("GlobalDAO");

	@PersistenceContext
	private EntityManager em;

	@Autowired(required = true)
	private EntityManagerFactory emf;
	
	@Transactional
	public void persist(Object transientInstance) {
		try {
			em.persist(transientInstance);
			log.debug("persisting "+transientInstance.getClass().getName()+" successful");
		} catch (RuntimeException re) {
			log.error("persisting "+transientInstance.getClass().getName()+" failed", re);
			throw re;
		}
	}

	public void attachDirty(Object instance) {
		try {
			Session session = em.unwrap(Session.class);
			session.saveOrUpdate(instance);
			log.debug("attachDirty "+instance.getClass().getName()+" successful");
		} catch (RuntimeException re) {
			log.error("attachDirty "+instance.getClass().getName()+" failed");
			throw re;
		}
	}

	public void attachClean(Object instance) {
		try {
			Session session = em.unwrap(Session.class);
			session.buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attachClean "+instance.getClass().getName()+" successful");
		} catch (RuntimeException re) {
			log.error("attachClean "+instance.getClass().getName()+" failed");
			throw re;
		}
	}

	public void delete(Object persistentInstance) {
		log.debug("deleting Object instance");
		try {
			em.remove(persistentInstance);
			log.debug("delete "+persistentInstance.getClass().getName()+" successful");
		} catch (RuntimeException re) {
			log.error("delete "+persistentInstance.getClass().getName()+" failed");
			throw re;
		}
	}

	public Object merge(Object detachedInstance) {
		try {
			Object result = em.merge(detachedInstance);
			log.debug("merge "+detachedInstance.getClass().getName()+" successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge "+detachedInstance.getClass().getName()+" failed");
			throw re;
		}
	}

	public Object findById(Class<?> c,java.lang.Integer id) {
		try {
			Object instance = em.find(c, id);
			if (instance == null) {
				log.debug("findById successful, no "+c.getName()+" instance found");
			} else {
				log.debug("findById successful, "+c.getName()+" instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
