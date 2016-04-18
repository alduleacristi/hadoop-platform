package ro.unitbv.fmi.tmis.platform.dao;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.Latitude;

@Named
@Stateless
public class LatitudeDAO {
	@PersistenceContext
	private EntityManager em;

	public Latitude insertLatitude(Latitude latitude) {
		System.out.println("#### Insert latitude...");
		em.persist(latitude);
		em.refresh(latitude);

		return latitude;
	}
}
