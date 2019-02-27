package hyrra_view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;

public class aloitusruutuTEXT {
	
	public static void main(String[] args) {
		
		String komento;
		String vastaus;
		
		Scanner scanner = new Scanner(System.in);
		
		Socket s = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		
		try {
			s = new Socket("10.0.1.1", 1111);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//Komentolista
		//LOCK
		//UNLOCK
		//BATTLE_START
		//BATTLE_END
		//SET_SPEED
		//SET_VOLUME
		//PLAY_MUSIC
		//STOP
		while (true) {
			try {
				komento = scanner.nextLine();
				out.writeUTF(komento);

				switch (komento) {	
					case "LOCK": {
			            //Lukitse hyrrät paikoilleen
						
						//Odota vastausta
						vastaus = in.readUTF();
						if (vastaus == "OK") {
							
						}
						break;
			        }
					case "UNLOCK": {
						//Lukitse hyrrät paikoilleen
						
						//Odota vastausta
						vastaus = in.readUTF();
						if (vastaus == "OK") {
							
						}
						break;
			        }
					case "BATTLE_START": {
			            //Tähän voi laittaa musiikin tms.
						//Pyöritä hyrrämoottoreita 4 sekuntia
						//Laske hyrrät radalle
						//Hetken päästä
						//Nosta teline ylös ja pysäytä hyrrämoottorit
						break;
			        }
			        case "BATTLE_END": {
			            //Laske teline alas
			        	break;
			        }
			        case "SET_SPEED": {
			        	//Lähetä uusi nopeus robotille
			        	komento = scanner.nextLine();
			        	out.write(Integer.parseInt(komento)); //getNopeus()
			            //Hyrrämoottoreiden nopeus
			            break;
			        }
			        
			        case "SET_VOLUME": {
			            //Lähetä uusi äänenvoimakkuus robotille
			        	komento = scanner.nextLine();
			        	out.write(Integer.parseInt(komento)); //getAanenvoimakkuus()
			        	//Hiljennä musiikki/Poista musiikin hiljennys
			            break;
			        }
			        case "PLAY_MUSIC": {
						//Käske robottia soittamaan kappale
						break;
					}
			        case "STOP": {
			            //Pysäytä robotti
			        	s.close();
			        	System.exit(0);
			            break;
			        }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
