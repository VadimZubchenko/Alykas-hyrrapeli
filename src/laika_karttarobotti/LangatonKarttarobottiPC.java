package laika_karttarobotti;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Waypoint;

public class LangatonKarttarobottiPC {
	
	public static void main(String[] args) {
		
		Socket s;
		
		try {
			s = new Socket("10.0.1.1", 1111);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			out.writeUTF("Aloitetaan vaarallisten tilojen tarkistaminen.");
			out.flush();
			
			//Luodaan kartta
			Rectangle suorakulmio = new Rectangle(0,0,120,94);
			
			Line[] janat = new Line[10];
			// rajaavan suorakulmion sivut
			janat[0] = new Line(0,0,120,0);
			janat[1] = new Line(120,0,120,94);
			janat[2] = new Line(0,94,120,94);
			janat[3] = new Line(0,0,0,94);
			// pystysuora estejanat, suorakulmio
			janat[4] = new Line(70,0,70,50);
			//ED-pullo este
			janat[5] = new Line(39,70,39,80);
			janat[6] = new Line(39,80,46,80);
			janat[7] = new Line(46,80,46,70);
			janat[8] = new Line(46,70,39,70);
			//Seinä
			janat[9] = new Line(0,50,15,50);
			
			LineMap kartta = new LineMap(janat, suorakulmio);
			
			//Lähetä kartta robotille
			kartta.dumpObject(out);
			out.flush();
			
			Waypoint[] tutkimusmatka = new Waypoint[4];
			tutkimusmatka[0] = new Waypoint(40,30);
			tutkimusmatka[1] = new Waypoint(105,40);
			tutkimusmatka[2] = new Waypoint(15,80);
			tutkimusmatka[3] = new Waypoint(5,10);
			
			//Lähetä Waypointit robotille
			tutkimusmatka[0].dumpObject(out); //= new Waypoint(40,30);
			tutkimusmatka[1].dumpObject(out);
			tutkimusmatka[2].dumpObject(out);
			tutkimusmatka[3].dumpObject(out);
			
			//System.out.println(in.readUTF());
			
			String string_temp;
			
			while (true) {
				
				string_temp = in.readUTF();
				
				if (string_temp.length() == 0) {
					break;
				} else {
					System.out.println(string_temp);
				}
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

