package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;
import it.polito.tdp.seriea.exception.SerieAException;

public class Model2 {

	private final static SerieADAO dao = new SerieADAO();
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> graph;
	private Map<Integer, List<Team>> squadre;
	private Map<String, Team> teamsMap; // Identity Map per i Team
	
	// Variabili utilizzate dalla ricorsione
	private List<Team> bestDomino ;
	private Set<DefaultWeightedEdge> usedEdges ;
	



	public Model2() {
		this.squadre = new HashMap<Integer, List<Team>>();
		this.teamsMap = new HashMap<>();
	}

	/**
	 * Creare un grafo che rappresenti i risultati delle partite giocate nella
	 * stagione selezionata dall’utente. Il grafo dovrà essere orientato e
	 * pesato, con i vertici che rappresentino l’insieme di squadre che ha
	 * giocato nella stagione indicata, e gli archi che rappresentino il
	 * risultato della partita. Il peso dell’arco tra TeamA e TeamB deve valere
	 * +1 se TeamA ha battuto TeamB, 0 se hanno pareggiato, -1 se TeamA ha
	 * perso.
	 * 
	 * @param seasonId
	 * @return
	 * @throws SerieAException
	 */
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> creaGrafo(int seasonId) throws SerieAException {

		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		List<Team> vertexList = listTeamsInBySeason(seasonId);
		System.out.println("<creaGrafo> numero vertici/squadre: " + vertexList.size());
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, vertexList);

		// crea gli archi del grafo
		// faccio fare tutto il lavoro al dao
		List<Match> matches = dao.listMatchBySeason(seasonId, teamsMap);
		for (Match m : matches) {
			DefaultWeightedEdge dwe = graph.addEdge(m.getHomeTeam(), m.getAwayTeam());
			this.graph.setEdgeWeight(dwe, "H".equals(m.getFtr()) ? 1 : ("A".equals(m.getFtr()) ? -1 : 0));
		}
		System.out.println("<creaGrafo> numero archi: " + this.graph.edgeSet().size());

		return this.graph;

	}

	private List<Team> listTeamsInBySeason(int seasonId) throws SerieAException {
		if (this.squadre.containsKey(seasonId)) {
			return this.squadre.get(seasonId);
		}
		List<Team> squadre = dao.listTeamsBySeason(seasonId, teamsMap);
		this.squadre.put(seasonId, squadre);
		return squadre;
	}

	public List<Team> getClassifica(Season s) throws SerieAException {
		creaGrafo(s.getSeason());

		// azzero i punteggi
		for (Team t : graph.vertexSet())
			t.addPunti(0);

		for (DefaultWeightedEdge dwe : this.graph.edgeSet()) {
			Team home = this.graph.getEdgeSource(dwe);
			Team away = this.graph.getEdgeTarget(dwe);
			int weight = (int) this.graph.getEdgeWeight(dwe);
			switch (weight) {
			case 1:
				home.addPunti(3);
				break;
			case -1:
				away.addPunti(3);
				break;
			case 0:
				home.addPunti(1);
				away.addPunti(1);
				break;
			}
		}

		List<Team> classifica = new ArrayList<Team>(graph.vertexSet());
		Collections.sort(classifica, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				return -(o1.getPunti() - o2.getPunti());
			}
		});

		return classifica;
		
	}

	
	
	public List<Team> calcolaDomino() {
		this.bestDomino = new ArrayList<>() ;
		this.usedEdges = new HashSet<>() ;
		
		List<Team> path = new ArrayList<>() ;
		
		/***ATTENZIONE***/
		/**
		 * Elimina dei vertici dal grafo per renderlo
		 * gestibile dalla ricorsione.
		 * Nella soluzione "vera" questa istruzione va rimossa
		 * (però l'algoritmo non termina in tempi umani).
		 */
		this.riduciGrafo(8);
		
		for(Team initial : graph.vertexSet()) {
			path.add(initial) ;
			dominoRecursive(1, initial, path) ;
			path.remove(initial) ;
		}
		
		return this.bestDomino ;
	}

	private void dominoRecursive(int step, Team t1, List<Team> path) {
				
		// controlla se ho migliorato il cammino "best"
		if(path.size() > this.bestDomino.size()) {
			// aggiorna il "best"
			this.bestDomino.clear();
			this.bestDomino.addAll(path) ;
			// oppure this.bestDomino = new ArrayList<>(path)
			// ma NON this.bestDomino = path

			//System.out.format("%2d %s\n", path.size(), path.toString());
		}
		
		// cerca di aggiungere un nuovo vertice
		for(DefaultWeightedEdge e: this.graph.outgoingEdgesOf(t1)) {
			Team t2 = graph.getEdgeTarget(e) ;
			
			// verifico che l'arco sia relativo ad una partita vinta
			// e che non sia ancora stato utilizzato
			if(graph.getEdgeWeight(e) == +1 && !this.usedEdges.contains(e)) {
				// provo ad attraversare l'arco
				path.add(t2) ;
				usedEdges.add(e) ;
				dominoRecursive(step+1, t2, path);
				usedEdges.remove(e) ;
				path.remove(path.size()-1) ; // togli l'ultimo aggiunto
				// Attenzione: path.remove(t2) ; non funziona perché t2 può comparire più di una volta
			}
		}
	}
	
	/**
	 * cancella dei vertici dal grafo in modo che la sua dimensione
	 * sia solamente pari a {@code dim} vertici
	 * @param dim
	 */
	private void riduciGrafo(int dim) {
		Set<Team> togliere = new HashSet<>() ;
		
		Iterator<Team> iter = graph.vertexSet().iterator() ;
		for(int i=0; i<graph.vertexSet().size()-dim; i++) {
			togliere.add(iter.next()) ;
		}
		graph.removeAllVertices(togliere) ;
		System.err.println("Attenzione: cancello dei vertici dal grafo");
		System.err.println("Vertici rimasti: "+graph.vertexSet().size()+"\n");
	}
	
	
	
}
