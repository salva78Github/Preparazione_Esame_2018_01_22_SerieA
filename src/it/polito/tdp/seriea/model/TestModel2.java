package it.polito.tdp.seriea.model;

import java.util.List;

import it.polito.tdp.seriea.exception.SerieAException;

public class TestModel2 {

	public static void main(String[] args) {

		Model2 model = new Model2();

		try {
			Season s = new Season(2006, "2005/2006");
			List<Team> classifica = model.getClassifica(s);
			System.out.println(String.format("Classifica campionato %s", s.getSeason()));
			for (Team t : classifica) {
				System.out.println(t.toString());
			}

		} catch (SerieAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
