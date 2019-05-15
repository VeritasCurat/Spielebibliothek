package Zhar;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import sun.awt.RepaintArea;

public class ZharGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int breite, hoehe;

	static JFrame window = new JFrame();
	static char lastkey = '\0';
	char currentkey = '\0';

	int MouseX, MouseY;

	ShowCanvas canvas;

	public ZharGUI(int breite, int hoehe) {
		super();
		ZharGUI.breite = breite;
		ZharGUI.hoehe = hoehe;
		Container container = getContentPane();
		canvas = new ShowCanvas(breite, hoehe);
		container.add(canvas);
		setSize(breite * 50 + 10, hoehe * 50 + 38 + 100);
		setTitle("Zhar V0.4");
		setVisible(true);
		setResizable(false);
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				if(ShowCanvas.spawnJägerFabrik || ShowCanvas.spawnSammlerFabrik) {
					MouseX = (arg0.getX() - 5) / 50;
					MouseY = (arg0.getY() - 30) / 50;
					ShowCanvas.previewX = MouseX; ShowCanvas.previewY = MouseY;
				}
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (SwingUtilities.isLeftMouseButton(arg0)) {
					MouseX = (arg0.getX() - 5) / 50;
					MouseY = (arg0.getY() - 30) / 50;
					Integer[] Mouse = { MouseX, MouseY };

					boolean contains = false;
					for(Integer[] el: ShowCanvas.drag) {
						if(el[0] == MouseX && el[1] == MouseY)contains = true;
					}
					if(!contains) {
						ShowCanvas.drag.add(Mouse);
						ZharController.repaint();		
					}
//					if (!ShowCanvas.drag.contains(Mouse)) {
//						ShowCanvas.drag.add(Mouse);
//						ZharController.repaint();						
//					}
				} else if (SwingUtilities.isRightMouseButton(arg0)) {
					MouseX = (arg0.getX() - 5) / 50;
					MouseY = (arg0.getY() - 30) / 50;
					ShowCanvas.kontextBauAuswahlX = MouseX;
					ShowCanvas.kontextBauAuswahlY = MouseY;
					ZharController.repaint();
				}
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(SwingUtilities.isLeftMouseButton(arg0)) {
					if(ShowCanvas.spawnJägerFabrik) {					
						if(ZharSpielfeld.spawn_fabrik(ShowCanvas.previewX, ShowCanvas.previewY)) {						
							ShowCanvas.spawn="Jfabrik";
							ShowCanvas.spawn();
							ShowCanvas.spawnJägerFabrik=false;
						}
						else ShowCanvas.spawnJägerFabrik=false;
					}
					if(ShowCanvas.spawnSammlerFabrik) {
						if(ZharSpielfeld.spawn_fabrik(ShowCanvas.previewX, ShowCanvas.previewY)) {						
							ShowCanvas.spawn="Sfabrik";
 
							ShowCanvas.spawnJägerFabrik=false;
						}
						else ShowCanvas.spawnSammlerFabrik=false;
					}
					
					ZharController.repaint();
					if (!ShowCanvas.spawn.equals("")) {
						if(ShowCanvas.spawn.equals("Jfabrik")) {
							ShowCanvas.spawnJägerFabrik=true;
							ZharController.repaint();
						}
						else if(ShowCanvas.spawn.equals("Sfabrik")) {
							ShowCanvas.spawnSammlerFabrik=true;
							ZharController.repaint();
						}
						else ShowCanvas.spawn();
					}else {					
							ShowCanvas.auswahl();
							try {
								ShowCanvas.bewegen();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
					}
					ShowCanvas.drag.clear();
					ShowCanvas.auswahlX = -1;
					ShowCanvas.auswahlY = -1;
					ShowCanvas.kontextBauX = -1;
					ShowCanvas.kontextBauY = -1;
				}
			
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (ShowCanvas.drag == null)
					ShowCanvas.drag = new ArrayList<Integer[]>();
				MouseX = (arg0.getX() - 5) / 50;
				MouseY = (arg0.getY() - 30) / 50;
				if (SwingUtilities.isRightMouseButton(arg0)) {
					System.out.println("blub");
					for(ZharFigur z: ZharSpielfeld.zharfiguren) {
						if(z.typ.equals("Bauer")) {								
							if(z.anfang_x == MouseX && z.anfang_y == MouseY) {
								ShowCanvas.kontextBauX = MouseX;
								ShowCanvas.kontextBauY = MouseY; 
								ShowCanvas.kontexttyp = z.typ;
							}
						}
						if(z.typ.equals("Jfabrik") || z.typ.equals("Sfabrik")) {
							for(int b=0; b<2; b++) {
								for(int h=0; h<2; h++) {									
									if(z.anfang_x+b == MouseX && z.anfang_y+h == MouseY) {
										ShowCanvas.kontextBauX = MouseX;
										ShowCanvas.kontextBauY = MouseY;  
										ShowCanvas.kontexttyp = z.typ;
									}
								}
							}
						}
					}
				} else {
					ShowCanvas.auswahlX = MouseX;
					ShowCanvas.auswahlY = MouseY;
				}
				ZharController.repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
//				if(SwingUtilities.isRightMouseButton(arg0)) {//löschen der auswahl etc.
//					ShowCanvas.auswahlX = ShowCanvas.auswahlY = ShowCanvas.previewX = ShowCanvas.previewY = ShowCanvas.kontextBauAuswahlX = ShowCanvas.kontextBauAuswahlY = -1;
//					ShowCanvas.spawnJägerFabrik  = ShowCanvas.spawnSammlerFabrik = false;
//					ShowCanvas.spawn = "";
//					ZharController.repaint();
//				}


			}
		});
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				lastkey = e.getKeyChar(); //TODO: drehen etc. ermöglichen?!
				if(lastkey=='')System.exit(0);
//				switch (lastkey) {
//					case 'w': {ZharController.bewegung(1,0,0);break;}
//					case 's': {ZharController.bewegung(-1,0,0);break;}
//					case 'a': {ZharController.bewegung(0,-1,0);break;}
//					case 'd': {ZharController.bewegung(0,1,0);break;}
//					case 'q': {ZharController.bewegung(0,0,-1);break;}
//					case 'e': {ZharController.bewegung(0,0,1);break;}
//				default:
//					break;
//				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				currentkey = '\0';
			}

			@Override
			public void keyPressed(KeyEvent e) {
				currentkey = e.getKeyChar();
			}
		});
	}

	public static void init() throws IOException {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 50 * ZharController.hoehe - 100, 50 * ZharController.breite - 100);
		window.setTitle("Zhar 0.8");
		window.setVisible(true);
	}

}

class ShowCanvas extends JPanel {
	static String Nachricht = ""; // 54 Modulo

	private static final long serialVersionUID = 1L;
	static int x, y, hoehe, breite;
	static int auswahlX;
	static int auswahlY;
	static List<Integer[]> drag = new ArrayList<Integer[]>(); // auswahl block der ausgewählt wird, drag Sequenz von Blöcken die durch mouse drag ausgewählt werden
	
	static String kontexttyp=""; //entweder Bauer, Jfabrik, oder Sfabrik
	static int kontextBauX = -1; //Koordnitate für Kontextmenü an diesem Punkte;
	static int kontextBauY = -1;
	static int kontextBauAuswahlX = -1; //Koordnitate Auswahl eines Menüpunkts im Kontextmenü
	static int kontextBauAuswahlY = -1; 
	static int previewX, previewY; //anfang der preview von fabrik
	static int spawn_fahrzeugX; 
	static int spawn_fahrzeugY;								
								
	
	static String spawn = "";
	static boolean spawnJägerFabrik=false;
	static boolean spawnSammlerFabrik=false;
	static char lastkey = '\0';

	ShowCanvas(int breite, int hoehe) {
		ShowCanvas.breite = breite;
		ShowCanvas.hoehe = hoehe;
		setBackground(Color.LIGHT_GRAY);
		setSize(breite, hoehe);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Spielfeld
		paintSpielfeld(g);
		//Figuren 
		paintFiguren(g);
		
		//auswahl 
		paintAuswahl(g);
		 
		//Scoreboard 
		paintScoreboard(g);
		  
		//kontextmenu 
		paintKontextBaummenü(g);
	 
		//infobox 
		paintInfobox(g);
		
		if(spawnSammlerFabrik)paintPreviewSfabrik(g);
		if(spawnJägerFabrik)paintPreviewJfabrik(g);

		 
	}

	private void paintInfobox(Graphics g) {

		g.setColor(Color.GRAY);
		// Rahmen
		g.fillRect(9 * 50, 18 * 50 + 7, 12 * 50, 2 * 50 - 13);

		// Schrift
		String zeile1 = Nachricht;
		String zeile2 = "";
		String zeile3 = "";
		if (Nachricht.length() > 111) {
			zeile1 = Nachricht.substring(0, 54);
			zeile2 = Nachricht.substring(55, 111);
			zeile3 = Nachricht.substring(112, Nachricht.length());
		} else if (Nachricht.length() > 54) {
			zeile1 = Nachricht.substring(0, 54);
			zeile2 = Nachricht.substring(55, Nachricht.length());
		}
		g.setColor(Color.BLACK);
		
		int Sekunden = (int) (ZharController.zeit_sekunden%60);
		int Minuten = (int) (((ZharController.zeit_sekunden - Sekunden) % 3600)/60);

		g.drawString("Zeit: " +Minuten+":"+Sekunden, 15 * 50, 18 * 50 + 22); // TODO:
																										// abhängig von
																										// breite machen

		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Infobox", 9 * 50, 18 * 50 + 22);
		if (zeile1.length() > 0)
			g.drawString(zeile1, 9 * 50, 18 * 50 + 40);
		if (zeile2.length() > 0)
			g.drawString(zeile2, 9 * 50, 18 * 50 + 60);
		if (zeile3.length() > 0)
			g.drawString(zeile3, 9 * 50, 18 * 50 + 80);

	}

	/**
	 * verschiedene Komponenten müssen dem Spieler (über GUI) nachrichten senden
	 * können (zB. ZharLevel über Auswahl)
	 * 
	 * @param s
	 */
	public static void setzeNachricht(String s) {
		Nachricht = s;
		ZharController.repaint();
	}

	/**
	 * ruft FUnktionen zum Spawnen auf
	 */
	public static void spawn() {
		if(spawn.equals("Jfabrik") || spawn.equals("Sfabrik")) {
			ZharSpielfeld.spawnFigur(spawn, previewX, previewY, ZharController.currentSpieler);
		}
		if(spawn.equalsIgnoreCase("Jäger") || spawn.equals("Sammler")) {
			ZharSpielfeld.spawnFigur(spawn, previewX, previewY, ZharController.currentSpieler);
		}
		else ZharSpielfeld.spawnFigur(spawn, kontextBauX, kontextBauY, ZharController.currentSpieler);
		spawn = "";
	}

	/**
	 * wählt Figur aus
	 */
	public static void auswahl() {
		if(auswahlX<0 || auswahlY < 0 || auswahlX > breite-1 || auswahlY > hoehe-1)return;
		ZharSpielfeld.auswahlFigur(auswahlX, auswahlY);
	}

	/**
	 * bewegt ausgewählte Figur aus
	 * 
	 * @throws InterruptedException
	 */
	public static void bewegen() throws InterruptedException {
		if (drag.size() > 0) {
			List<Integer[]> kopie = new ArrayList<Integer[]>();	for(Integer[] i: drag)kopie.add(i);
			ZharController.repaint();
			ZharSpielfeld.bewegen(kopie);
		}
	}

	private void paintSpielfeld(Graphics g) {
		g.setColor(Color.BLACK);
		for (int h = 0; h < hoehe; h++) {
			for (int b = 0; b < breite; b++) {
				 g.setColor(Color.BLACK);
				 g.fillRect(b * 50, h * 50, 50, 50);
				 g.setColor(Color.GRAY); g.fillRect( b*50+2, h*50+2,48, 48);//TODO: nur zum

				g.setColor(ZharLevel.fields[b][h].farbe);
				g.fillRect(b * 50 + 2, h * 50 + 2, 48, 48);
			}
		}
	}

	private void paintFiguren(Graphics g) {
		g.setColor(Color.BLACK);
		for (int h = 0; h < hoehe; h++) {
			for (int b = 0; b < breite; b++) {
				g.setColor(Color.BLACK);

				for (ZharFigur z: ZharSpielfeld.zharfiguren) {
					if (z.belegt(b, h)) {
						g.setColor(z.farbe);
						if(z.typ.equals("Sfabrik"))g.setColor(Color.GREEN);
						if(z.typ.equals("Jfabrik"))g.setColor(Color.RED);
						g.fillRect(b * 50 + 2, h * 50 + 2, 48, 48);
						if(z.spieler != ZharController.currentSpieler) {
							g.setColor(Color.BLACK);
							g.drawLine(b * 50 + 2, h * 50 + 2,  (b+1) * 50 + 2, (h+1) * 50 + 2);
						}
						continue;
					}
				}
			}
		}
	}

	private void paintScoreboard(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, hoehe * 50, breite * 50 + 10, 100);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(7, hoehe * 50 + 7, breite * 50 - 10, 87);

		/// highlight spieler TODO:
		g.setColor(Color.RED);
		if (ZharController.currentSpieler == 0) {
			g.fillRect(0, hoehe * 50 + 7, 10 * 50 - 10, 87);
		} else if (ZharController.currentSpieler == 1) {
			// g.fillRect(7,hoehe*50+7,breite*50-10,87);
		}

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Spieler 1: ", 10, hoehe * 50 + 30);
		g.drawString("Ressourcen: " + ZharController.spielerliste.get(0).ressourcen, 10, hoehe * 50 + 60);
		g.drawString("Punkte: " + ZharController.spielerliste.get(0).punkte, 10, hoehe * 50 + 90);

		g.drawString("Spieler 2: ", breite * 50 - 200, hoehe * 50 + 30);
		g.drawString("Ressourcen: " + ZharController.spielerliste.get(0).ressourcen, breite * 50 - 200,
				hoehe * 50 + 60);
		g.drawString("Punkte: " + ZharController.spielerliste.get(0).punkte, breite * 50 - 200, hoehe * 50 + 90);
	}

	private void paintKontextBaummenü(Graphics g) {
		if (kontextBauX != -1 && kontextBauY != -1) {
			if(kontexttyp.equals("Bauer")) {
				// Rahmen
				g.setColor(Color.BLACK);
				g.fillRect(kontextBauX * 50, kontextBauY * 50, 4   * 50, 2 * 50);

				// Flächen
				g.setColor(Color.DARK_GRAY);
				int rahmenbreite = 5;
				int abstand = 2;
				for (int i = 0; i < 2; i++) {
					g.fillRect((kontextBauX + abstand * i) * 50 + rahmenbreite, kontextBauY * 50 + rahmenbreite,
							2 * 50 - rahmenbreite * 2, 2 * 50 - rahmenbreite * 2);
				}

				// Highlighter
				int h = (kontextBauAuswahlX - kontextBauX) / 2;
				if (kontextBauAuswahlY >= kontextBauY && kontextBauAuswahlY <= kontextBauY + 1) {
					if (h >= 0 && h < 2) {
						g.setColor(Color.GRAY);
						g.fillRect((kontextBauX + abstand * h) * 50 + rahmenbreite, kontextBauY * 50 + rahmenbreite,
								2 * 50 - rahmenbreite * 2, 2 * 50 - rahmenbreite * 2);
					}
				}

				// Beschriftung
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				for (int i = 0; i < 2; i++) {
					String bezeichnung = "";
					switch (i) {
						case 0: {
							bezeichnung = "Sfabrik";
							break;
						}
						case 1: {
							bezeichnung = "Jfabrik";
							break;
						}
					}
					if (i == h) {
						spawn = bezeichnung;
						g.setFont(new Font("Arial", Font.BOLD, 25));
						g.drawString(bezeichnung, (kontextBauX + abstand * i) * 50 + rahmenbreite + 5,kontextBauY * 50 + rahmenbreite + 20);
					} else {
						g.setFont(new Font("Arial", Font.PLAIN, 20));
						g.drawString(bezeichnung, (kontextBauX + abstand * i) * 50 + rahmenbreite + 10,kontextBauY * 50 + rahmenbreite + 20);
					}
				}
			}	
			}
			if(kontexttyp.equals("Jfabrik") || (kontexttyp.equals("Sfabrik"))) {
				// Rahmen
				g.setColor(Color.BLACK);
				g.fillRect(kontextBauX * 50, kontextBauY * 50, 2   * 50, 2 * 50);
	
				// Flächen
				g.setColor(Color.DARK_GRAY);
				int rahmenbreite = 5;
				int abstand = 2;
				for (int i = 0; i < 1; i++) {
					g.fillRect((kontextBauX + abstand * i) * 50 + rahmenbreite, kontextBauY * 50 + rahmenbreite,
							2 * 50 - rahmenbreite * 2, 2 * 50 - rahmenbreite * 2);
				}
	
				// Highlighter
				int h = (kontextBauAuswahlX - kontextBauX) / 2;
				if (kontextBauAuswahlY >= kontextBauY && kontextBauAuswahlY <= kontextBauY + 1) {
					if (h >= 0 && h < 1) {
						g.setColor(Color.GRAY);
						g.fillRect((kontextBauX + abstand * h) * 50 + rahmenbreite, kontextBauY * 50 + rahmenbreite,
								2 * 50 - rahmenbreite * 2, 2 * 50 - rahmenbreite * 2);
					}
				}
	
				// Beschriftung
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				for (int i = 0; i < 2; i++) {
					String bezeichnung = "";
					switch (i) {
						case 0: {
							if(kontexttyp.equals("Jfabrik")) {
								bezeichnung = "Jäger";
							}
							if(kontexttyp.equals("Sfabrik")) {
								bezeichnung = "Sammler";
							}
							break;
						}	
					}
					if (i == h) {
						Integer[] a = Fabrik.sucheSpawnPlatz(kontextBauX, kontextBauY);
						if(a[0] != -1) {
							spawn_fahrzeugX = a[0]; spawn_fahrzeugY = a[1];
						}
						spawn = bezeichnung;
						g.setFont(new Font("Arial", Font.BOLD, 25));
						g.drawString(bezeichnung, (kontextBauX + abstand * i) * 50 + rahmenbreite + 5,kontextBauY * 50 + rahmenbreite + 20);
					} else {
						g.setFont(new Font("Arial", Font.PLAIN, 20));
						g.drawString(bezeichnung, (kontextBauX + abstand * i) * 50 + rahmenbreite + 10,kontextBauY * 50 + rahmenbreite + 20);
					}
				}
			}	
	}

	private void paintAuswahl(Graphics g) {
		Color c = new Color(250, 170, 170);
		g.setColor(c);
		if (auswahlX != -1 && auswahlY != -1) {
			if (ZharLevel.auswahlPrüfen(auswahlX, auswahlY)) {
				g.fillRect(auswahlX * 50 + 2, auswahlY * 50 + 2, 50 - 2, 50 - 2);
			}

		}
		if (drag.size() > 0) {
			for (Integer[] k : drag) {
				if (ZharLevel.auswahlPrüfen(k[0], k[1])) {
					for (int i = 0; i < drag.size(); i++) {
						g.fillRect(drag.get(i)[0] * 50 + 2, drag.get(i)[1] * 50 + 2, 50 - 2, 50 - 2);

					}
				}
			}
		}
	}
	
	private void paintPreviewJfabrik(Graphics g) {
		g.setColor(Color.RED);
		if (previewX != -1 && previewY != -1) {
				g.fillRect(previewX * 50 + 2, previewY * 50 + 2, 50 - 2, 50 - 2);
				g.fillRect((previewX+1) * 50 + 2, previewY * 50 + 2, 50 - 2, 50 - 2);
				g.fillRect(previewX * 50 + 2, (previewY+1) * 50 + 2, 50 - 2, 50 - 2);
				g.fillRect((previewX+1) * 50 + 2, (previewY+1) * 50 + 2, 50 - 2, 50 - 2);
		}
	}
	
	private void paintPreviewSfabrik(Graphics g) {
		g.setColor(Color.GREEN);
		if (previewX != -1 && previewY != -1) {
				g.fillRect(previewX * 50 + 2, previewY * 50 + 2, 50 - 2, 50 - 2);
				g.fillRect((previewX+1) * 50 + 2, previewY * 50 + 2, 50 - 2, 50 - 2);
				g.fillRect(previewX * 50 + 2, (previewY+1) * 50 + 2, 50 - 2, 50 - 2);
				g.fillRect((previewX+1) * 50 + 2, (previewY+1) * 50 + 2, 50 - 2, 50 - 2);
		}
	}
}
