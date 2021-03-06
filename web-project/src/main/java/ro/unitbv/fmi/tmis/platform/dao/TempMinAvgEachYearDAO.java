package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		System.out.println("New row inserted for region ["
				+ entity.getRegion().getName() + "] year [" + entity.getYear()
				+ "] month [" + entity.getMonth() + "]");

		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<TempMinAvgEachYear> getTempMinAvgEachYearByRegionId(
			long regionId) {
		return em
				.createQuery(
						"select t from temp_min_avg_each_year t where t.region.idRegion=:idRegion")
				.setParameter("idRegion", regionId).getResultList();
	}
}
