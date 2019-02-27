package hyrra_model;

import java.io.Serializable;

/**
 * @author Oma nimesi
 */

public class Pelaaja implements Serializable {

	private String nimi;
	private int voitot, aika;
	

	public Pelaaja() {
		super();
	}
	
	public Pelaaja(String nimi, int voitot, int aika) {
		super();
		this.nimi = nimi;
		this.voitot = voitot;
		this.aika = aika;
	}

	/**
	 * @return the nimi
	 */
	public String getNimi() {
		return nimi;
	}

	/**
	 * @return the voitot
	 */
	public int getVoitot() {
		return voitot;
	}

	/**
	 * @return the aika
	 */
	public int getAika() {
		return aika;
	}

	/**
	 * @param nimi the nimi to set
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	/**
	 * @param voitot the voitot to set
	 */
	public void setVoitot(int voitot) {
		this.voitot = voitot;
	}

	/**
	 * @param aika the aika to set
	 */
	public void setAika(int aika) {
		this.aika = aika;
	}

	public String toString() {
		return String.format("%-10s %-10s %-10d", nimi, voitot, aika);
	}

}