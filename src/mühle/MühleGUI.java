package mühle;

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



public class MühleGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JFrame window = new JFrame();

	ShowCanvas canvas;

	public MühleGUI(MühleSpielfeld s) {
		super();
		Container container = getContentPane();
		canvas = new ShowCanvas(s);
		
		container.add(canvas);
		setSize(800, 835);
		setTitle("Mühle V0.55");
		setVisible(true);
		setResizable(false);
	}

	public static void init() throws IOException {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 800, 800);
		window.setTitle("Mühle");
		
		window.setVisible(true);

	}

}

class ShowCanvas extends JPanel {
	MühleSpielfeld lichtfeld;
	
	static int main = 255;
	static int side = 200;
	static double dark_factor = 0.5;

	int markierungX=-1; int markierungY=-1; int markierungRing=-1;
	static Color Markierung = new Color(main, side, side);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int x, y;

	ShowCanvas(MühleSpielfeld s) {
		lichtfeld = s;
		setBackground(Color.WHITE);
		setSize(450, 400);
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
				x = (arg0.getX()-90);
				y = (arg0.getY()-90);
				if((Math.abs((x%100)) < 25 || Math.abs((x%100)) > 75) && (Math.abs(y%100) < 25 || Math.abs((y%100)) > 75)) {
					x+=25; y+=25;
					x/=100; y/=100;
				}
				else {
					x = -1; y = -1;
				}
				MühleController.spieler_aktion();
			}
		});

	}	
	
	public int ringkoordinateInkoordinate(int a, int ring) {
		return ring+(a*(3-ring));
	}
	
	public int koordinate_ring(int x, int y) {
		return (Math.abs(3-x));//TODO
				
				
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
	


		g.setColor(Color.BLACK);
		int Offset = 90;

		// Vertikale Linien malen
		g.drawLine(0 * 100 + Offset, 0 * 100 + Offset, 0 * 100 + Offset, 6 * 100 + Offset);
		g.drawLine(1 * 100 + Offset, 1 * 100 + Offset, 1 * 100 + Offset, 5 * 100 + Offset);
		g.drawLine(2 * 100 + Offset, 2 * 100 + Offset, 2 * 100 + Offset, 4 * 100 + Offset);
		g.drawLine(4 * 100 + Offset, 2 * 100 + Offset, 4 * 100 + Offset, 4 * 100 + Offset);
		g.drawLine(5 * 100 + Offset, 1 * 100 + Offset, 5 * 100 + Offset, 5 * 100 + Offset);
		g.drawLine(6 * 100 + Offset, 0 * 100 + Offset, 6 * 100 + Offset, 6 * 100 + Offset);
		
		// Horizontale Linien malen
		g.drawLine(0 * 100 + Offset, 0 * 100 + Offset, 6 * 100 + Offset, 0 * 100 + Offset);
		g.drawLine(1 * 100 + Offset, 1 * 100 + Offset, 5 * 100 + Offset, 1 * 100 + Offset);
		g.drawLine(2 * 100 + Offset, 2 * 100 + Offset, 4 * 100 + Offset, 2 * 100 + Offset);
		g.drawLine(2 * 100 + Offset, 4 * 100 + Offset, 4 * 100 + Offset, 4 * 100 + Offset);
		g.drawLine(1 * 100 + Offset, 5 * 100 + Offset, 5 * 100 + Offset, 5 * 100 + Offset);
		g.drawLine(0 * 100 + Offset, 6 * 100 + Offset, 6 * 100 + Offset, 6 * 100 + Offset);

		//Vertikale Verbindungslinien malen
		g.drawLine(3 * 100 + Offset, 0 * 100 + Offset, 3 * 100 + Offset, 2 * 100 + Offset);
		g.drawLine(3 * 100 + Offset, 4 * 100 + Offset, 3 * 100 + Offset, 6 * 100 + Offset);

		//Horizontale Verbindungslinien malen
		g.drawLine(0 * 100 + Offset, 3 * 100 + Offset, 2 * 100 + Offset, 3 * 100 + Offset);
		g.drawLine(4 * 100 + Offset, 3 * 100 + Offset, 6 * 100 + Offset, 3 * 100 + Offset);

		// Punkte malen
		int x=ringkoordinateInkoordinate(MühleController.auswahlX, MühleController.auswahlRing);
		int y=ringkoordinateInkoordinate(MühleController.auswahlY, MühleController.auswahlRing);
		for (int a = 0; a < 7; a++) {
			for (int b = 0; b < 7; b++) {
				if (a == 3 && b == 3)
					continue;
				if (a == 3 || b == 3 || a == b || (a + b) == 6) {
					if (MühleController.markiert && x == a && y == b) {
						g.setColor(Markierung);
						g.fillOval(a * 100 + Offset-25, b * 100 + Offset-25, 50, 50);
						g.setColor(Color.GRAY);
						continue;
					}
					g.setColor(Color.BLACK);
					g.fillOval(a * 100 + Offset-25, b * 100 + Offset-25, 50, 50);
					
					g.setColor(Color.lightGray);
					g.fillOval(a * 100 + Offset-20, b * 100 + Offset-20, 40, 40);

				}

			}
		}

		
		
		//Spielfiguren malen
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 3; b++) {
				for (int ring = 0; ring < 3; ring++) {
					boolean markierung= (a==markierungX && b==markierungY && ring==markierungRing);
					//System.out.println(a+" "+b+" "+ring+": "+Spielfeld.figuren[a][b][ring].typ);
					if(lichtfeld.figuren[a][b][ring].typ.equals("NULL"))continue;
					x=ringkoordinateInkoordinate(a, ring);
					y=ringkoordinateInkoordinate(b, ring);

					if(lichtfeld.figuren[a][b][ring].farbe.equals("weiß")) g.setColor(Color.WHITE);
					if(lichtfeld.figuren[a][b][ring].farbe.equals("schwarz")) g.setColor(Color.BLACK);
					g.fillOval(x * 100 + Offset - 20, y * 100 + Offset - 20, 40, 40);
					
					if(markierung) {
						g.setColor(Color.RED.darker().darker().darker());
						g.fillOval(a * 100 + Offset-25, b * 100 + Offset-25, 50, 50);
					}
				}
			}
		}

		if (!lichtfeld.spiel_aktiv) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.red);
			MühleController.changeFarbe();
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Spiel zuende! " + MühleController.farbe + " gewinnt!", 370, 400);
			g.drawString("Zum beenden klicken", 400, 500);
		}
	}

}
