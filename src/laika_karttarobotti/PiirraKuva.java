package laika_karttarobotti;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

@SuppressWarnings("serial")
public class PiirraKuva extends JPanel {
	
	static int MAX_Y = 94;
	static int MAX_X = 120;
	static boolean kartta_piirretty = false;
	
	static int piirra_x;
	static int piirra_y;
	
	static int MITTAKAAVA = 2;
	
	public void paintComponent(Graphics g) {
		
		if (kartta_piirretty == false) {
		
			//Piirrä seinät
			g.setColor(Color.black);
			g.drawLine(MITTAKAAVA * (0), MITTAKAAVA * (MAX_Y - 0), MITTAKAAVA * (120), MITTAKAAVA * (MAX_Y - 0));
			g.drawLine(MITTAKAAVA * (120), MITTAKAAVA * (MAX_Y - 0), MITTAKAAVA * (120), MITTAKAAVA * (MAX_Y - 94));
			g.drawLine(MITTAKAAVA * (0), MITTAKAAVA * (MAX_Y - 94), MITTAKAAVA * (120), MITTAKAAVA * (MAX_Y - 94));
			g.drawLine(MITTAKAAVA * (0), MITTAKAAVA * (MAX_Y - 0), MITTAKAAVA * (0), MITTAKAAVA * (MAX_Y - 94));
		    
			//Piirrä esteet
			g.drawLine(MITTAKAAVA * (70), MITTAKAAVA * (MAX_Y - 0), MITTAKAAVA * (70), MITTAKAAVA * (MAX_Y - 50));
			
			g.drawLine(MITTAKAAVA * (39), MITTAKAAVA * (MAX_Y - 70), MITTAKAAVA * (39), MITTAKAAVA * (MAX_Y - 80));
			g.drawLine(MITTAKAAVA * (39), MITTAKAAVA * (MAX_Y - 80), MITTAKAAVA * (46), MITTAKAAVA * (MAX_Y - 80));
			g.drawLine(MITTAKAAVA * (46), MITTAKAAVA * (MAX_Y - 80), MITTAKAAVA * (46), MITTAKAAVA * (MAX_Y - 70));
			g.drawLine(MITTAKAAVA * (46), MITTAKAAVA * (MAX_Y - 70), MITTAKAAVA * (39), MITTAKAAVA * (MAX_Y - 70));
			
			g.drawLine(MITTAKAAVA * (0), MITTAKAAVA * (MAX_Y - 50), MITTAKAAVA * (15), MITTAKAAVA * (MAX_Y - 50)); 
			
			//Piirrä mittauspisteet
			g.setColor(Color.RED);
			g.drawOval(MITTAKAAVA * (40), MITTAKAAVA * (MAX_Y - 30), MITTAKAAVA * (5), MITTAKAAVA * (5));
			g.drawOval(MITTAKAAVA * (105), MITTAKAAVA * (MAX_Y - 40), MITTAKAAVA * (5), MITTAKAAVA * (5));
			g.drawOval(MITTAKAAVA * (15), MITTAKAAVA * (MAX_Y - 80), MITTAKAAVA * (5),  MITTAKAAVA * (5));
			g.drawOval(MITTAKAAVA * (5), MITTAKAAVA * (MAX_Y - 10), MITTAKAAVA * (5), MITTAKAAVA * (5));	
			
			kartta_piirretty = true;
		} else {
			//Piirrä yksi piste
			g.setColor(Color.BLACK);
			g.drawOval(MITTAKAAVA * (piirra_x), MITTAKAAVA * (MAX_Y - piirra_y), MITTAKAAVA * (5), MITTAKAAVA * (5));	
		}
	}
	
	public static void main(String[] args) {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Draw Line");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBackground(Color.white);
	    frame.setSize(MITTAKAAVA * (150), MITTAKAAVA * (150));
	    
	    PiirraKuva panel = new PiirraKuva();
	    frame.add(panel);
	    
	    frame.setVisible(true);
	    
	    Socket s;
		String string_temp;
		
		try {
			s = new Socket("10.0.1.1", 1111);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeUTF("Aloitetaan vaarallisten tilojen tarkistaminen.");
			out.flush();
			
			while (true) {
				//Ota vastaan waypoint objekti
				//wp.loadObject(in);
				
				string_temp = in.readUTF();
				System.out.println(string_temp);
				
				if (string_temp == "Piste") {
					piirra_x = in.readInt();
					piirra_y = in.readInt();
					
					//Piirrä piste kartalle
					panel = new PiirraKuva();
				    frame.add(panel);
				    
				    frame.repaint();
				} else if (string_temp == "Waypoint") {
					//Printataan PC:llä waypointin sijainti
					System.out.println(in.readUTF());	
				} else if (string_temp == "Lopeta") {
					break;
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