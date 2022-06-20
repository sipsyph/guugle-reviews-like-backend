package com.sip.chillhub.main.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.Place;
import com.sip.chillhub.main.business.repository.PlaceRepository;

@Service
public class PlaceWriteServiceImpl implements PlaceWriteService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	public Long createPlace(Place place) {
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatement.append("INSERT into place(address,name,coordinates_x,coordinates_y) ");
		sqlStatement.append("SELECT :address,:name,:coordinatesX,:coordinatesY ");
		sqlStatement.append("RETURNING id ");
		sqlParams.addValue("address", place.getAddress());
		sqlParams.addValue("name", place.getName());
		sqlParams.addValue("coordinatesX", place.getCoordinatesX());
		sqlParams.addValue("coordinatesY", place.getCoordinatesY());
		System.out.println(sqlStatement);
		
		return placeRepository.createPlace(sqlStatement.toString(), sqlParams);
	}

}
