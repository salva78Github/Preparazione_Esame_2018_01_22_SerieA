package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;
import it.polito.tdp.seriea.exception.SerieAException;

public class Model {

	private final static SerieADAO dao = new SerieADAO();
	private SimpleWeightedGraph<Team, DefaultWeightedEdge> graph;
	private Map<Integer, List<Team>> squadre;
	
	public Model(){
		this.squadre = new HashMap<Integer, List<Team>>();
	}
	
	/**
	 * Data la stagione, creo grafo non diretto e pesato dove i vertici sono le squadre che hanno partecipato in quel campionato
	 * e gli archi collegano due team che hanno giocato contro pesati per il numero di gol totali dei match di andata e ritorno
	 * 
	 * @param seasonId
	 * @return
	 * @throws SerieAException
	 */
	
	private SimpleWeightedGraph<Team, DefaultWeightedEdge> creaGrafo(int seasonId) throws SerieAException {
		
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Team> vertexList = listTeamsInBySeason(seasonId);
		System.out.println("<creaGrafo> numero vertici/squadre: " + vertexList.size());
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, vertexList);

		// crea gli archi del grafo 
		// faccio fare tutto il lavoro al dao
		// che mi dà la lista della coppia dei vertici
		List<Match> matches = dao.listMatchBySeason(seasonId);
		for (Match m : matches) {
			DefaultWeightedEdge dwe;
			if(this.graph.containsEdge(m.getAwayTeam(), m.getHomeTeam())){
				dwe=this.graph.getEdge(m.getAwayTeam(), m.getHomeTeam());
				int score = (int) this.graph.getEdgeWeight(dwe);
				score += (m.getFthg()+m.getFtag());
				this.graph.setEdgeWeight(dwe, score);
			}
			else{
				dwe = graph.addEdge(m.getHomeTeam(), m.getAwayTeam());
				this.graph.setEdgeWeight(dwe, m.getFthg()+m.getFtag());
			}
		}
		System.out.println("<creaGrafo> numero archi: " + this.graph.edgeSet().size());

		return this.graph;

	}

	private List<Team> listTeamsInBySeason(int seasonId) throws SerieAException {
		if (this.squadre.containsKey(seasonId)) {
			return this.squadre.get(seasonId);
		}
		List<Team> squadre = dao.listTeamsBySeason(seasonId);
		this.squadre.put(seasonId, squadre);
		return squadre;
	}


	public List<TeamsCouple> getTeamsCoupleAndScore(int seasonId) throws SerieAException{
		creaGrafo(seasonId);
		
		List<TeamsCouple> tcList = new ArrayList<TeamsCouple>();
		for(DefaultWeightedEdge dwe : this.graph.edgeSet()){
			TeamsCouple tc = new TeamsCouple(this.graph.getEdgeSource(dwe), this.graph.getEdgeTarget(dwe), (int) this.graph.getEdgeWeight(dwe));
			tcList.add(tc);
		}
		Collections.sort(tcList);
		return tcList;
	}
	
	
}
