package com.skyscanner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

	public static void main(final String[] args) throws Exception {
		new HoenScannerApplication().run(args);
	}

	@Override
	public String getName() {
		return "hoen-scanner";
	}

	@Override
	public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {

	}

//    @Override
//    public void run(final HoenScannerConfiguration configuration, final Environment environment) {
//
//    }
	@Override
	public void run(final HoenScannerConfiguration configuration, final Environment environment) throws Exception {

		// Use Jackson to read both JSON files
		ObjectMapper mapper = new ObjectMapper();
		List<SearchResult> searchResults = new ArrayList<>();

		// Load rental cars
		InputStream rentalCarStream = getClass().getResourceAsStream("/rental_cars.json");
		List<SearchResult> rentalCars = Arrays.asList(mapper.readValue(rentalCarStream, SearchResult[].class));
		searchResults.addAll(rentalCars);

		// Load hotels
		InputStream hotelStream = getClass().getResourceAsStream("/hotels.json");
		List<SearchResult> hotels = Arrays.asList(mapper.readValue(hotelStream, SearchResult[].class));
		searchResults.addAll(hotels);

		// Register the resource and pass the combined list
		environment.jersey().register(new SearchResource(searchResults));
	}

}
