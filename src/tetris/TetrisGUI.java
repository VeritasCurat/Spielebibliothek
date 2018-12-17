package tetris;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class TetrisGUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int hoehe, breite;

	static JFrame window = new JFrame();
	static char lastkey='\0';
	static char currentkey='\0';

    
    ShowCanvas canvas;

	public TetrisGUI(int hoehe, int breite) {
		super();
		TetrisGUI.hoehe = hoehe; TetrisGUI.breite = breite;
		Container container = getContentPane();
		canvas = new ShowCanvas(hoehe, breite);
		container.add(canvas);
		setSize(breite*50+10,hoehe*50+38 + 100);
		setTitle("Tetris V0.8");
		setVisible(true);
		setResizable(false);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				lastkey = e.getKeyChar();
				switch (lastkey) {
					case 's': {TetrisController.bewegung(-1,0,0);break;}
					case 'a': {TetrisController.bewegung(0,-1,0);break;}
					case 'd': {TetrisController.bewegung(0,1,0);break;}
					case 'q': {TetrisController.bewegung(0,0,-1);break;}
					case 'e': {TetrisController.bewegung(0,0,1);break;}

				default:
					break;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				currentkey = e.getKeyChar();
			}
		});
	}

  public static void init() throws IOException {
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    window.setBounds(0, 0, 50*TetrisController.hoehe-100, 50*TetrisController.breite-100);
		    window.setTitle("Tetris 0.8");
		    window.setVisible(true);
  }
  
}

class ShowCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	static int x, y, hoehe, breite;
	static char lastkey = '\0';
	

	ShowCanvas(int hoehe, int breite) {
		ShowCanvas.hoehe = hoehe; ShowCanvas.breite = breite;
		setBackground(Color.LIGHT_GRAY);
		setSize(breite, hoehe);
	}	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		
		//Feld mit tetraunits
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				g.setColor(Color.BLACK);
				g.fillRect(b*50, h*50, 50, 50);
				g.setColor(Color.GRAY); g.fillRect(b*50+2, h*50+2, 48, 48);//TODO: nur zum debugen => entfernen
					
				if(TetrisFigur.form[h][b]) {
					g.setColor(TetrisFigur.farbe);
					g.fillRect(b*50+2, h*50+2, 48, 48);
					continue;
				}
				if(TetrisSpielfeld.spielfeld_belegung[h][b]) {
					Color br = TetrisSpielfeld.spielfeld_farben[h][b].brighter();
					g.setColor(br);
					g.fillRect(b*50+2, h*50+2, 48, 48);
					continue;
				}
				else {
					g.setColor(Color.WHITE);
					g.fillRect(b*50+2, h*50+2, 48, 48);
				}
				
			}
		}
		
		//Scoreboard
		g.setColor(Color.BLACK);
		g.fillRect(0,hoehe*50,breite*50+10, 100);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(7,hoehe*50+7,breite*50-10,87);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 20)); 
		g.drawString("Schwierigkeit: "+TetrisController.schwierigkeitNamen[TetrisController.schwierigkeit], 10,hoehe*50+30);
		g.drawString("Score: "+TetrisController.score, 10,hoehe*50+60);
		
		//nextfigure
		g.drawString("Next Figure:", breite*50-220,hoehe*50+60);
		
		for(int zeile=0; zeile<3; zeile++) {
			for(int spalte=0; spalte<4; spalte++) {
				if(TetrisSpielfeld.nextdarstellung[zeile][spalte]) {
					g.setColor(TetrisSpielfeld.nextColor);
					g.fillRect(breite*50-250+spalte*20+160,hoehe*50+30+zeile*20-10,20,20);
				}
				else continue;
			}
		}

		
	}
}
