package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.exception.FailedToSaveException;
import ro.unitbv.fmi.tmis.platform.model.Region;

@Stateless
@Named
public class RegionDAO {
	private static final String GET_ALL_CITYS_QUERY = "select r from Region r";

	@PersistenceContext
	private EntityManager em;

	public List<Region> getAllRegions() {
		return em.createQuery(GET_ALL_CITYS_QUERY, Region.class)
				.getResultList();
	}

	public Region saveRegion(Region region) throws FailedToSaveException {
		try {
			em.persist(region);
			em.flush();
			em.refresh(region);
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new FailedToSaveException(
					"Failed to save region. Maybe a re gion with this name already exist",
					exc);
		}

		return region;
	}
}
