package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.User;

@Named
@Stateless
public class UserDAO {
	private static final String GET_ALL_USERS_QUERY = "select u from user_table u";

	@PersistenceContext
	private EntityManager em;

	public List<User> getAllUsers() {
		return em.createQuery(GET_ALL_USERS_QUERY, User.class).getResultList();
	}
}
