package it.polito.tdp.seriea.model;

import java.util.List;

import it.polito.tdp.seriea.exception.SerieAException;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		List<TeamsCouple> tcList;
		try {
			tcList = model.getTeamsCoupleAndScore(2003);
			for (TeamsCouple tc : tcList) {
				System.out.println(tc.toString());
			}
		} catch (SerieAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
