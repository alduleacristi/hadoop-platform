package ro.unitbv.fmi.tmis.platform.dao;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@Stateless
public class CountryDAO {
	@PersistenceContext
	private EntityManager em;

	public void service() {
		System.out.println("Service was caled...");

		if (em != null) {
			System.out.println("Persistence context was injected...");
		} else {
			System.out.println("Persistence context was not injected...");
		}
	}
}
