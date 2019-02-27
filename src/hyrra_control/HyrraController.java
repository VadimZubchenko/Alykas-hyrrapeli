package hyrra_control;

import java.util.Timer;
import java.util.TimerTask;

public class HyrraController {
	
	Timer timer = new Timer();
	TimerTask task;

	//Käynnistä painikkeen koodi
	public void Kaynnista_Koodi() {
		
		//Tarkista että hyrrät on lukittu
		
		//Lähetä robotille "BATTLE_START" komento
		
		//Vaihda Tab2 sivulle ja odota vastausta robotilta
		
		//Käynnistä Timer ja focus stop painike (jotta myös ENTER voisi painaa stop painiketta)
		
	}
	
	//Mute painikkeen koodi
	public void Hiljenna_Musiikki() {
		//Lähetä robotille "SET_MUSIC" komento
		
		//Lähetä robotille haluttu int volume
		
	}
	
	//Lukitus painikkeen koodi
	public void Lukitse_Hyrrat() {
		//Jos telineitä ei ole lukittu
			//Lähetä robotille "LOCK" komento
		//Else
			//Lähetä robotille "UNLOCK" komento
	}
	
	//Pysäytä timer
	public void Pysayta_Timer() {
		//Jos ottelu on käynnissä
			//Lähetä robotille komento "BATTLE_END"
		
	}
	
	//Tallenna voittaja painike
	public void Tallenna_Voittaja() {
		//Voittajan tallentamisen toimenpiteet
		
	}
	
	//Sulje ohjelma painike
	public void Sulje_Ohjelma() {
		//Lähetä robotille "STOP" komento
		
	}
	
}
