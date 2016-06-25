package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.exception.AlreadyExistException;
import ro.unitbv.fmi.tmis.platform.exception.FailedToDeleteException;
import ro.unitbv.fmi.tmis.platform.exception.FailedToSaveException;
import ro.unitbv.fmi.tmis.platform.model.Region;

@Stateless
@Named
public class RegionDAO {
	private static final String GET_ALL_REGIONS_QUERY = "select r from Region r where r.type=:type";
	private static final String SEARCH_FOR_REGION_QUERY = "select r from Region r where lower(r.name) like :name and r.type=:type";

	@PersistenceContext
	private EntityManager em;

	public List<Region> getAllRegions(String type) {
		return em.createQuery(GET_ALL_REGIONS_QUERY, Region.class)
				.setParameter("type", type).getResultList();
	}

	public List<Region> searchForRegion(String type, String name) {
		return em.createQuery(SEARCH_FOR_REGION_QUERY, Region.class)
				.setParameter("name", "%" + name.toLowerCase() + "%").setParameter("type", type)
				.getResultList();
	}

	public List<Region> getPaginatedResult(String type, int offset, int limit) {
		return em.createQuery(GET_ALL_REGIONS_QUERY, Region.class).setParameter("type", type)
				.setFirstResult(offset).setMaxResults(limit).getResultList();
	}

	public Region saveRegion(Region region) throws FailedToSaveException,
			AlreadyExistException {
		try {
			Region existingRegion = getRegionByName(region.getName());
			if (existingRegion == null) {
				em.persist(region);
				em.flush();
				em.refresh(region);
			} else {
				System.out.println("Region with name [" + region.getName()
						+ "] already exist");
				throw new AlreadyExistException("Region with name ["
						+ region.getName() + "] already exist");
			}
		} catch (AlreadyExistException aex) {
			throw aex;
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new FailedToSaveException(
					"Failed to save region. Maybe a re gion with this name already exist",
					exc);
		}

		return region;
	}

	public Region getRegionById(long id) {
		return em.find(Region.class, id);
	}

	public Region getRegionByName(String name) {
		try {
			return em
					.createQuery("select r from Region r where r.name=:name",
							Region.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public void deleteRegion(long regionId) throws FailedToDeleteException {
		Region region = null;
		try {
			region = getRegionById(regionId);
			if (region != null) {
				em.remove(region);
				em.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailedToDeleteException("Failed to delete region ["
					+ region.getName() + "]", e);
		}
	}
}
