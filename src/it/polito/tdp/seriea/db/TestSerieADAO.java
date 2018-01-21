package it.polito.tdp.seriea.db;

import java.util.List;

import it.polito.tdp.seriea.exception.SerieAException;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class TestSerieADAO {

	public static void main(String[] args) {
		SerieADAO dao = new SerieADAO() ;
		
		List<Season> seasons;
		try {
			seasons = dao.listSeasons();
			System.out.println("<stagioni> " + seasons.size());
			System.out.println(seasons);
			
			List<Team> teams = dao.listTeams() ;
			System.out.println("<squadre> " + teams.size());
			System.out.println(teams);
			
			List<Match> matchesBySeason = dao.listMatchBySeason(2006) ;
			System.out.println("<match per stagione> " + matchesBySeason.size());
			System.out.println(matchesBySeason);
			
			List<Team> teamsBySeason = dao.listTeamsBySeason(2014);
			System.out.println("<team per stagione> " + teamsBySeason.size());
			System.out.println(teamsBySeason);
			
			List<Match> matchesByTeam = dao.listMatchByTeam(new Team("Juventus")) ;
			System.out.println("<match per squadre> " + matchesByTeam.size());
			System.out.println(matchesByTeam);

			
			
		} catch (SerieAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
