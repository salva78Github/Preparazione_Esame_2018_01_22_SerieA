package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class Match {

	private final int id;
	private final Season season;
	private final String div;
	private final LocalDate date;
	private final Team homeTeam;
	private final Team awayTeam;
	private final int fthg; // full time home goals
	private final int ftag; // full time away goals
	private final String ftr; // full time result (H, A, D)
	private final int hthg; // half time home goals
	private final int htag; // half time away goals
	private final String htr; // half time result (H, A, D)

	/**
	 * @param id
	 * @param season
	 * @param div
	 * @param date
	 * @param homeTeam
	 * @param awayTeam
	 * @param fthg
	 * @param ftag
	 * @param ftr
	 * @param hthg
	 * @param htag
	 * @param htr
	 */
	public Match(int id, Season season, String div, LocalDate date, Team homeTeam, Team awayTeam, int fthg, int ftag,
			String ftr, int hthg, int htag, String htr) {
		super();
		this.id = id;
		this.season = season;
		this.div = div;
		this.date = date;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.fthg = fthg;
		this.ftag = ftag;
		this.ftr = ftr;
		this.hthg = hthg;
		this.htag = htag;
		this.htr = htr;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the season
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * @return the div
	 */
	public String getDiv() {
		return div;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return the homeTeam
	 */
	public Team getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public Team getAwayTeam() {
		return awayTeam;
	}

	/**
	 * @return the fthg
	 */
	public int getFthg() {
		return fthg;
	}

	/**
	 * @return the ftag
	 */
	public int getFtag() {
		return ftag;
	}

	/**
	 * @return the ftr
	 */
	public String getFtr() {
		return ftr;
	}

	/**
	 * @return the hthg
	 */
	public int getHthg() {
		return hthg;
	}

	/**
	 * @return the htag
	 */
	public int getHtag() {
		return htag;
	}

	/**
	 * @return the htr
	 */
	public String getHtr() {
		return htr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Match [id=" + id + ", season=" + season + ", div=" + div + ", date=" + date + ", homeTeam=" + homeTeam
				+ ", awayTeam=" + awayTeam + ", fthg=" + fthg + ", ftag=" + ftag + ", ftr=" + ftr + ", hthg=" + hthg
				+ ", htag=" + htag + ", htr=" + htr + "]";
	}

}
