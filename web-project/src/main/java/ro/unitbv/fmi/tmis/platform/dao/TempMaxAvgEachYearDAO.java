package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.TempMaxAvgEachYear;

@Named
@Stateless
public class TempMaxAvgEachYearDAO {
	@PersistenceContext
	private EntityManager em;

	public TempMaxAvgEachYear insertTempMaxAvgEachYear(
			TempMaxAvgEachYear entity) {
		em.persist(entity);
		em.flush();
		em.refresh(entity);

		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<TempMaxAvgEachYear> getTempMaxAvgEachYearByRegionId(
			long regionId) {
		return em
				.createQuery(
						"select t from temp_max_avg_each_year t where t.region.idRegion=:idRegion")
				.setParameter("idRegion", regionId).getResultList();
	}
}
