package it.polito.tdp.seriea.model;

public class TeamsCouple implements Comparable<TeamsCouple> {

	private final Team t1;
	private final Team t2;
	private final int score;

	/**
	 * @param t1
	 * @param t2
	 * @param score
	 */
	public TeamsCouple(Team t1, Team t2, int score) {
		this.t1 = t1;
		this.t2 = t2;
		this.score = score;
	}

	/**
	 * @return the t1
	 */
	public Team getT1() {
		return t1;
	}

	/**
	 * @return the t2
	 */
	public Team getT2() {
		return t2;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Numero totale di gol tra %s - %s: %d", getT1(), getT2(), getScore());
	}

	@Override
	public int compareTo(TeamsCouple tc) {
		// TODO Auto-generated method stub
		return tc.getScore() - getScore();
	}

}
