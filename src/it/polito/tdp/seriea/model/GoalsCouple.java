package it.polito.tdp.seriea.model;

public class GoalsCouple {

	private final int goal1;
	private final int goal2;
	private final int numberOfResult;

	/**
	 * @param goal1
	 * @param goal2
	 * @param numberOfResult
	 */
	public GoalsCouple(int goal1, int goal2, int numberOfResult) {
		super();
		this.goal1 = goal1;
		this.goal2 = goal2;
		this.numberOfResult = numberOfResult;
	}

	/**
	 * @return the goal1
	 */
	public int getGoal1() {
		return goal1;
	}

	/**
	 * @return the goal2
	 */
	public int getGoal2() {
		return goal2;
	}

	/**
	 * @return the numberOfResult
	 */
	public int getNumberOfResult() {
		return numberOfResult;
	}

}
