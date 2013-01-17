package cn.edu.nenu.acm.oj.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class GlobeDAO {
	protected static Logger log = LogManager.getLogger("Actions");

	@Autowired(required = true)
	private EntityManagerFactory emf;
	protected SessionFactory sessionFactory=getSessionFactory();
	
	public void persist(Object transientInstance) {
		log.debug("persisting Object instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	private SessionFactory getSessionFactory() {
		return null;
	}

	public void attachDirty(Object instance) {
		log.debug("attaching dirty Object instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Object instance) {
		log.debug("attaching clean Object instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Object persistentInstance) {
		log.debug("deleting Object instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Object merge(Object detachedInstance) {
		log.debug("merging Object instance");
		try {
			Object result = (Object) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Object findById(java.lang.Integer id) {
		log.debug("getting Object instance with id: " + id);
		try {
			Object instance = (Object) sessionFactory.getCurrentSession()
					.get("cn.edu.nenu.acm.oj.entitybeans.Object", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Object> findByExample(Object instance) {
		log.debug("finding Object instance by example");
		try {
			List<Object> results = (List<Object>) sessionFactory
					.getCurrentSession()
					.createCriteria("cn.edu.nenu.acm.oj.entitybeans.Object")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
