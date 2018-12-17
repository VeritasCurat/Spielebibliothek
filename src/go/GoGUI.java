package go;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class GoGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JFrame window = new JFrame();

	ShowCanvas canvas;

	public GoGUI(int breite) {
		super();
		Container container = getContentPane();
		canvas = new ShowCanvas(breite);
		container.add(canvas);
		setSize(1300, 1100);
		setTitle("Go V0.2");
		setVisible(true);
		setResizable(false);
	}

	public static void init() throws IOException {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1300, 1100);
		window.setTitle("Go V0.2");
		
		window.setVisible(true);

	}

}

class ShowCanvas extends JPanel {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int x, y, breite;
	final int rand = 50;
	int skalar, offset, steineDurchmesser;


	ShowCanvas(int breite) {
		//Spielfeld ist immer quadratisch!
		
		this.breite = breite; 
		skalar = (1000-rand*2)/breite;
		steineDurchmesser = skalar; offset = steineDurchmesser/2;
		
		
		setBackground(Color.WHITE);
		setSize(1300, 1100);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				x = (arg0.getX()-rand/2)/skalar;
				y = (arg0.getY()-rand/2)/skalar;		
			
				GoController.spieler_aktion(x,y);
			}
		});

	}	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
	


		g.setColor(Color.BLACK);
		int Offset = 90;

		// Vertikale Linien malen
		for(int i=0; i<=breite; i++) {			
			g.drawLine(rand+(skalar*i),rand, rand+(skalar*i),1000-rand);
		}

		// Horizontale Linien malen
		for(int i=0; i<=breite; i++) {		
			g.drawLine(rand,rand+(skalar*i),1000-rand,rand+(skalar*i));
		}		
		//Orientierungspunkte malen

		
		int anzahl=0;
		//Spielfiguren malen
		for (int a = 0; a < breite; a++) {
			for (int b = 0; b < breite; b++) {
				if(!GoSpielfeld.figuren[a][b].farbe.equals("")) {	
					++anzahl;
					//schatten
					g.setColor(Color.DARK_GRAY);
					g.fillOval(a * skalar + offset/2, b * skalar + offset/2, steineDurchmesser, steineDurchmesser);
					//außenmarkierung
					g.setColor(Color.GRAY);
					g.fillOval(a * skalar  , b * skalar , steineDurchmesser, steineDurchmesser);
					
					if(GoSpielfeld.figuren[a][b].farbe.equals("weiß")) g.setColor(Color.WHITE);
					if(GoSpielfeld.figuren[a][b].farbe.equals("schwarz")) g.setColor(Color.BLACK);
					g.fillOval(a * skalar + steineDurchmesser/20, b * skalar + steineDurchmesser/20 , steineDurchmesser-steineDurchmesser/10, steineDurchmesser-steineDurchmesser/10);
				}
			}
		}
		
		//Punkte malen
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 20)); 

		int abstand=30;
		int initialabstand=15;
		g.drawString("Spieler Schwarz", 1000, initialabstand+rand);
		g.drawString("Steine: "+GoSpielfeld.anz_schwarz, 1000, initialabstand+rand+abstand);
		g.drawString("Gefangene: "+GoSpielfeld.gefangene_schwarz, 1000, initialabstand+rand+abstand*2);
		g.drawString("Punkte: "+GoSpielfeld.punkte_schwarz, 1000, initialabstand+rand+abstand*3);

		initialabstand=800;
		g.drawString("Spieler Weiß", 1000, initialabstand+rand);
		g.drawString("Steine: "+GoSpielfeld.anz_weiß, 1000, initialabstand+rand+abstand);
		g.drawString("Gefangene: "+GoSpielfeld.gefangene_weiß, 1000, initialabstand+rand+abstand*2);
		g.drawString("Punkte: "+GoSpielfeld.punkte_weiß, 1000, initialabstand+rand+abstand*3);
		
		if (!GoSpielfeld.spiel_aktiv) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.red);
			GoController.changeFarbe();
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Spiel zuende! " + GoController.farbe + " gewinnt!", 370, 400);
			g.drawString("Zum beenden klicken", 400, 500);
		}
	}

}
