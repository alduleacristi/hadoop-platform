package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.UsedQuery;

@Named
@Stateless
public class QueryUsedDAO {
	private static final String GET_ALL_USED_QUERYS = "select uq from used_query uq where uq.region.idRegion=:idRegion";
	private static final String GET_USED_QUERYS_BY_REGION_AND_QUERY = "select uq from used_query uq where uq.region.idRegion=:idRegion and uq.query.idQuery =:idQuery";

	@PersistenceContext
	private EntityManager em;

	public List<UsedQuery> getUsedQueryForRegion(long regionId) {
		return em.createQuery(GET_ALL_USED_QUERYS, UsedQuery.class)
				.setParameter("idRegion", regionId).getResultList();
	}

	public List<UsedQuery> getUsedUsedQueryByRegionAndQuery(Long idRegion,
			Long idQuery) {
		return em
				.createQuery(GET_USED_QUERYS_BY_REGION_AND_QUERY,
						UsedQuery.class).setParameter("idRegion", idRegion)
				.setParameter("idQuery", idQuery).getResultList();

	}

	public UsedQuery updateUsedQuery(Long idUsedQuery, Long timeRunning,
			Boolean successed, Boolean running) {
		UsedQuery usedQuery = getUsedQueryById(idUsedQuery);
		usedQuery.setTimeDuration(timeRunning);
		usedQuery.setSuccessed(successed);
		usedQuery.setRunning(running);

		em.merge(usedQuery);
		em.flush();
		em.refresh(usedQuery);

		return usedQuery;
	}

	public UsedQuery getUsedQueryById(Long id) {
		return em.find(UsedQuery.class, id);
	}

	public UsedQuery insertUsedQuery(UsedQuery entity) {
		em.persist(entity);
		em.flush();
		em.refresh(entity);

		return entity;
	}
}
