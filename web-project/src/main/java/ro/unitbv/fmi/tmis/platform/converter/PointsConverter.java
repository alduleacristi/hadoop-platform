package ro.unitbv.fmi.tmis.platform.converter;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.postgresql.util.PGobject;

import ro.unitbv.fmi.tmis.platform.model.Points;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class PointsConverter implements AttributeConverter<Points, PGobject> {

	@Override
	public PGobject convertToDatabaseColumn(Points point) {
		try {
			PGobject po = new PGobject();
			// here we tell Postgres to use JSON as type to treat our json
			po.setType("json");
			// this is Jackson already added as dependency to project, it could
			// be any JSON marshaller
			String str = (new ObjectMapper()).writeValueAsString(point);
			System.out.println("Json object -> " + str);
			po.setValue(str);
			return po;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Points convertToEntityAttribute(PGobject po) {
		try {
			if (po == null || po.getValue() == null || po.getValue().equals("")) {
				return null;
			} else {
				return (new ObjectMapper()).readValue(po.getValue(),
						Points.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
