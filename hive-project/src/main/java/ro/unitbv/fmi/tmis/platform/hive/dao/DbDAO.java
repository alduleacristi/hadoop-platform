package ro.unitbv.fmi.tmis.platform.hive.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import ro.unitbv.fmi.tmis.platform.hive.exceptions.FailedToCreateException;
import ro.unitbv.fmi.tmis.platform.hive.utils.HiveConnection;

@Named
@Stateless
public class DbDAO {
	@Inject
	private HiveConnection hiveConnection;

	public void createDb(String dbName) throws FailedToCreateException {
		try {
			Connection con = hiveConnection.getConnection();

			Statement createDb = con.createStatement();
			createDb.execute("CREATE DATABASE " + dbName);
			con.close();
		} catch (SQLException e) {
			FailedToCreateException exc = new FailedToCreateException(
					"Failed to create database", e);
			throw exc;
		}
	}
}
