package ro.unitbv.fmi.tmis.platform.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ro.unitbv.fmi.tmis.platform.model.User;

@Named
@Stateless
public class UserDAO {
	private static final String GET_ALL_USERS_QUERY = "select u from user_table u";
	private static final String GET_USER_BY_EMAIL_QUERY = "select u from user_table u where u.email=:email";

	@PersistenceContext
	private EntityManager em;

	public List<User> getAllUsers() {
		return em.createQuery(GET_ALL_USERS_QUERY, User.class).getResultList();
	}

	public User getUserByEmail(String email) {
		try {
			return em.createQuery(GET_USER_BY_EMAIL_QUERY, User.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
