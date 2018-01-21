package it.polito.tdp.seriea.model;

import java.util.List;
import java.util.Map;

public class SimulationStatistics {

	private int steps;
	private List<Team> countries;
	
	
	/**
	 * @param steps
	 * @param people
	 */
	public SimulationStatistics(int steps, List<Team> countries) {
		super();
		this.steps = steps;
		this.countries = countries;
	}
	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}
	/**
	 * @return the people
	 */
	public List<Team> getCountries() {
		return countries;
	}


}
