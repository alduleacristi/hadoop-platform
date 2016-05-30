package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.Query;

@Named
@Stateless
public class QueryDAO {
	private static final String GET_ALL_QUERYS = "select q from Query q";
	private static final String GET_QUERY_BY_NAME = "select q from Query q where q.name=:name";

	@PersistenceContext
	private EntityManager em;

	public List<Query> getAllQuerys() {
		return em.createQuery(GET_ALL_QUERYS, Query.class).getResultList();
	}

	public Query getQueryByName(String name) {
		try {
			return em.createQuery(GET_QUERY_BY_NAME, Query.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
