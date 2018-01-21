package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {

	// Simulation parameters
	private static final int NUMERO_MIGRANTI = 1000;

	// World model
	private UndirectedGraph<Team, DefaultEdge> graph;

	// Measures of Interest
	private int steps = 0;
	private Map<Team, Integer> people;

	// Event queue
	private PriorityQueue<Event> queue;

	public Simulator(UndirectedGraph<Team, DefaultEdge> graph) {
		this.queue = new PriorityQueue<>();
		this.graph = graph;
		this.people = new HashMap<Team, Integer>();

		//inizializza a 0 il numero di stanziali per ogni nazione
		for(Team c : this.graph.vertexSet()){
			this.people.put(c, 0);
		}
	}

	public void load(Team c) {
		Event event = new Event(1, Event.EventType.STABILIZZAZIONE, c, NUMERO_MIGRANTI / 2);
		this.queue.add(event);
		event = new Event(1, Event.EventType.MIGRAZIONE, c, NUMERO_MIGRANTI / 2);
		this.queue.add(event);
		this.steps++;

	}

	public void run() {
		while (!queue.isEmpty()) {
			Event e = queue.poll();
			System.out.println(e);

			switch (e.getEventType()) {

			case STABILIZZAZIONE:
				Integer initialNumberOfPeople = this.people.containsKey(e.getCountry()) ? this.people.get(e.getCountry()) : 0;
				this.people.put(e.getCountry(), initialNumberOfPeople + e.getNumeroMigranti());
				break;

			case MIGRAZIONE:
				this.steps++;
				List<Team> borderCountries = Graphs.neighborListOf(this.graph, e.getCountry());
				Event event = new Event(this.steps, Event.EventType.STABILIZZAZIONE, e.getCountry(),
						e.getNumeroMigranti() / 2 + ((e.getNumeroMigranti() / 2) % borderCountries.size()));
				this.queue.add(event);

					int flussoMigrazione = (e.getNumeroMigranti() / 2) / borderCountries.size();
					if (flussoMigrazione > 0) {
						for (Team c : borderCountries) {
						event = new Event(this.steps, Event.EventType.MIGRAZIONE, c, flussoMigrazione);
						this.queue.add(event);
					}
				}
				break;

			}

		}
	}

	public SimulationStatistics collectStatistics() {
		/**
		 * Per ogni stato in cui vi sia almeno una persona stanziale, stampare
		 * il nome dello stato (abbreviazione + nome) ed il numero di persone
		 * presenti. L’elenco deve essere in ordine decrescente di numero di
		 * persone.
		 */
		List<Team> countries = new ArrayList<Team>();
		for (Team c : this.people.keySet()) {
			if (this.people.get(c) != 0) {
				//todo
			}
		}
		Collections.sort(countries, new CountryPeopleComparator());
		return new SimulationStatistics(steps, countries);
	}

	public class CountryPeopleComparator implements Comparator<Team> {

		@Override
		public int compare(Team o1, Team o2) {
			// TODO Auto-generated method stub
			return 1;
		}

	}
	
	/**
	 * 
	 * 	
	 public SimulationStatistics doSimulation(Country c) {
		Simulator sim = new Simulator(this.graph);
		sim.load(c);
		sim.run();
		SimulationStatistics ss = sim.collectStatistics();
		return ss;

	}
	 * 
	 * 
	 */

}
