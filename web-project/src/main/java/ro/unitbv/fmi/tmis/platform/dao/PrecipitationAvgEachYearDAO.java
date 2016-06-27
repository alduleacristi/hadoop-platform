package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.PrecipitationAvgEachYear;

@Named
@Stateless
public class PrecipitationAvgEachYearDAO {
	@PersistenceContext
	private EntityManager em;

	public PrecipitationAvgEachYear insertPrecipitationAvgEachYear(
			PrecipitationAvgEachYear entity) {
		em.persist(entity);
		em.flush();
		em.refresh(entity);
		System.out.println("New row inserted for region ["
				+ entity.getRegion().getName() + "] year [" + entity.getYear()
				+ "] month [" + entity.getMonth() + "]");

		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<PrecipitationAvgEachYear> getPrecipitationAvgEachYearByRegionId(
			long regionId) {
		return em
				.createQuery(
						"select p from precipitation_avg_each_year p where p.region.idRegion=:idRegion")
				.setParameter("idRegion", regionId).getResultList();
	}
}
