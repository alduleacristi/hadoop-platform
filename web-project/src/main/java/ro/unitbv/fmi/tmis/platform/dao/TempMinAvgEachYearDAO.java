package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.TempMaxAvgEachYear;
import ro.unitbv.fmi.tmis.platform.model.TempMinAvgEachYear;

@Named
@Stateless
public class TempMinAvgEachYearDAO {
	@PersistenceContext
	private EntityManager em;

	public TempMinAvgEachYear insertTempMinAvgEachYear(TempMinAvgEachYear entity) {
		em.persist(entity);
		em.flush();
		em.refresh(entity);

		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<TempMaxAvgEachYear> getPrecipitationAvgEachYearByRegionId(
			long regionId) {
		return em
				.createQuery(
						"select t from tem_min_avg_each_year t where t.region.idRegion=:idRegion")
				.setParameter("idRegion", regionId).getResultList();
	}
}
