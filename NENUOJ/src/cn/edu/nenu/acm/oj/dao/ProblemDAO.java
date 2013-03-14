package cn.edu.nenu.acm.oj.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.dto.ProblemSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Problem;

@Repository
public class ProblemDAO extends AbstractDAO<Problem> {
	
	ProblemDAO(){
		super();
		super.setClazz(Problem.class);
	}
	
	public List<ProblemSimpleDTO> getProblemList(String judgerSource,String filterString,int page,int pageSize){
//		String jpql="SELECT p FROM Problem p WHERE 1=1 ";
//		Object[] value;
//		if(!"".equals(judgerSource)){
//			TypedQuery<Judger> judgerQuery = em.createNamedQuery("Judger.findBySource", Judger.class);
//			judgerQuery.setParameter("source", judgerSource);
//			Judger judger = null;
//			try{
//				judger = judgerQuery.getSingleResult();
//			}catch(Exception e){}
//			
//			jpql+="AND p.";
//		}
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Problem>  query = cb.createQuery(Problem.class);
		Root<Problem>  problemRoot = query.from(Problem.class);
		Predicate predicate = cb.conjunction();
		if(!"".equals(judgerSource)){
			TypedQuery<Judger> judgerQuery = em.createNamedQuery("Judger.findBySource", Judger.class);
			judgerQuery.setParameter("source", judgerSource);
			Judger judger = null;
			try{
				judger = judgerQuery.getSingleResult();
			}catch(Exception e){}
			if(judger!=null){
				predicate = cb.and(predicate,cb.equal(problemRoot.get("judger"), judger));
			}
		}
		return null;
	}
}
