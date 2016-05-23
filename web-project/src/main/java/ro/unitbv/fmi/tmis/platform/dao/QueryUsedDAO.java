package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.UsedQuery;

@Named
@Stateless
public class QueryUsedDAO {
	private static final String GET_ALL_USED_QUERYS = "select uq from used_query uq where uq.region.idRegion=:idRegion";

	@PersistenceContext
	private EntityManager em;

	public List<UsedQuery> getUsedQueryForRegion(long regionId) {
		return em.createQuery(GET_ALL_USED_QUERYS, UsedQuery.class)
				.setParameter("idRegion", regionId).getResultList();
	}
}
