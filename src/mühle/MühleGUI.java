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



public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JFrame window = new JFrame();

	ShowCanvas canvas;

	public GUI() {
		super();
		Container container = getContentPane();
		canvas = new ShowCanvas();
		container.add(canvas);
		setSize(800, 835);
		setTitle("Mühle V0.5");
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

	static int main = 255;
	static int side = 200;
	static double dark_factor = 0.5;

	static Color Markierung = new Color(main, side, side);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int x, y;

	ShowCanvas() {
		setBackground(Color.LIGHT_GRAY);
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
				Controller.spieler_aktion();
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
		// Punkte malen
		int x=ringkoordinateInkoordinate(Controller.auswahlX, Controller.auswahlRing);
		int y=ringkoordinateInkoordinate(Controller.auswahlY, Controller.auswahlRing);
		for (int a = 0; a < 7; a++) {
			for (int b = 0; b < 7; b++) {
				if (a == 3 && b == 3)
					continue;
				if (a == 3 || b == 3 || a == b || (a + b) == 6) {
					if (Controller.markiert && x == a && y == b) {
						g.setColor(Markierung);
						g.fillOval(a * 100 + Offset-15, b * 100 + Offset-15, 30, 30);
						g.setColor(Color.GRAY);
						continue;
					}
					g.fillOval(a * 100 + Offset-15, b * 100 + Offset-15, 30, 30);
				}

			}
		}

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
		
		//Spielfiguren malen
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 3; b++) {
				for (int ring = 0; ring < 3; ring++) {
					//System.out.println(a+" "+b+" "+ring+": "+Spielfeld.figuren[a][b][ring].typ);
					if(Spielfeld.figuren[a][b][ring].typ.equals("NULL"))continue;
					x=ringkoordinateInkoordinate(a, ring);
					y=ringkoordinateInkoordinate(b, ring);

					if(Spielfeld.figuren[a][b][ring].typ.equals("weiß")) g.setColor(Color.WHITE);
					if(Spielfeld.figuren[a][b][ring].typ.equals("schwarz")) g.setColor(Color.BLACK);
					g.fillOval(x * 100 + Offset, y * 100 + Offset, 50, 50);
				}
			}
		}

		if (!Spielfeld.spiel_aktiv) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.red);
			Controller.changeFarbe();
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Spiel zuende! " + Controller.farbe + " gewinnt!", 370, 400);
			g.drawString("Zum beenden klicken", 400, 500);
		}
	}

}
