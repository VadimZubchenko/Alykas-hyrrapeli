package hyrra_model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import hyrra_model.Pelaaja;


public class PelaajanTiedosto {

	/*
	 * Kirjoittaa parametrina annettuun tiedostoon paremetrina annetun listan
	 * (olio), jossa Valtio-olioita
	 */
	public static void kirjoitaTiedosto(String tiedostoNimi, List<Pelaaja> list) {

		FileOutputStream tiedosto = null;
		ObjectOutputStream os = null;
		try {
			//ArrayList<Pelaaja> list = new ArrayList<>(Arrays.asList(pelaajat));
			
			
			// Avaa FileOutputStream-virta tiedostoon
			tiedosto = new FileOutputStream(tiedostoNimi);
			// Luo objektivirta tiedostoon kirjoittamista varten
			os = new ObjectOutputStream(tiedosto);
			// Kirjoita objektivirtaan lista
			for (Pelaaja pelaajanlista : list) {
				os.writeObject(pelaajanlista);
			}
			// Sulje tietovirrat
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Tiedot tallennettu tiedostoon " + tiedostoNimi);
	}

	/*
	 * Lukee parametrina annetusta tiedostosta listan, jossa Pelaaja-olioita
	 * Palauttaa listan tai null, jos listaa ei pystytä muodostamaan
	 */
	@SuppressWarnings("unchecked")
	
	public static List<Pelaaja> lueTiedosto(String tiedostonNimi) {

		List<Pelaaja> luetut = null;
		FileInputStream tiedosto = null;
		ObjectInputStream is = null;

		try {

			// Avaa FileInputStream-virta tiedostoon
			tiedosto = new FileInputStream(tiedostonNimi);
			// Luo objektivirta tiedostosta lukemista varten
			is = new ObjectInputStream(tiedosto);
			// Lue objektivirran sisältö listaan
			luetut = new ArrayList<Pelaaja>();
			
			while (true) {
				try {
					Pelaaja luettu = (Pelaaja) is.readObject();
					
					luetut.add(luettu);
				} catch (Exception e) {
					// TODO: handle exception
					break;
				}
			
			}
			// Sulje tietovirrat
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Tiedot haettu tiedostosta " + tiedostonNimi);
		return luetut;
	}
}
