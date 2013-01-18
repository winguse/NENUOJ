package cn.edu.nenu.acm.oj.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockOptions;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("request")
public class GlobalDAO implements InitializingBean,DisposableBean  {
	protected static Logger log = LogManager.getLogger("GlobalDAO");

	@Autowired(required = true)
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void persist(Object transientInstance) {
		try {
			EntityManager em=emf.createEntityManager();
			em.persist(transientInstance);
			em.close();
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

	@SuppressWarnings("unchecked")
	public List<Object> findByExample(Class<?> c,Object instance) {
		log.debug("finding Object instance by example");
		try {
			Session session=em.unwrap(Session.class);
			List<Object> results = (List<Object>) session
					.createCriteria(c)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public void destroy() throws Exception {
		em.close();
		log.debug("GlebalDAO destroy, close the em.");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		em=emf.createEntityManager();
		log.debug("GlebalDAO afterPropertiesSet, init the em.");
	}
}
