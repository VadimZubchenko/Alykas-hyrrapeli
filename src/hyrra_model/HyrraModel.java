package hyrra_model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HyrraModel {
	
	Socket s;
	DataInputStream in;
	DataOutputStream out;
	
	boolean hyrraLukittu = false;
	boolean hyrraMykistettu = false;
	
	public HyrraModel() {
		//Luo yhteys robottiin
		try {
			s = new Socket("10.0.1.1", 1111);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			System.out.println("Yhteys luotu");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Yhteyttä ei luotu");
		}
	}
	
	public void LukitseTelineet(boolean lukitse) {
		try {
			if (lukitse == true) {
				out.writeUTF("LOCK");
			} else {
				out.writeUTF("UNLOCK");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void HyrraSetSpeed(int speed) {
		try {
			//Lähetä käsky
			out.writeUTF("SET_SPEED");
			out.flush();
			//Lähetä uusi speed
			out.write(speed);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void HyrraMusicMuted(boolean muted) {
		try {
			//Lähetä käsky
			out.writeUTF("MUSIC");
			out.flush();
			
			if (hyrraMykistettu = true) {
				out.write(1);
			} else {
				out.write(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void KaynnistaOttelu() {
		String vastaus;
		try {
			//Lähetä käsky
			out.writeUTF("BATTLE_START");
			out.flush();
			//Odota vastausta
			vastaus = in.readUTF();
			if (vastaus == "OK") {
				//Käynnistä timer
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LopetaOttelu() {
		String vastaus;
		try {
			//Lähetä käsky
			out.writeUTF("BATTLE_END");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SuljeRobotti() {
		try {
			//Lähetä käsky
			out.writeUTF("STOP");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
