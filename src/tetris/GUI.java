package tetris;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mühle.MühleController;
import sun.security.provider.ConfigFile.Spi;


public class GUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int hoehe, breite;

	static JFrame window = new JFrame();
	static char lastkey='\0';
    
    ShowCanvas canvas;

	public GUI(int hoehe, int breite) {
		super();
		GUI.hoehe = hoehe; GUI.breite = breite;
		Container container = getContentPane();
		canvas = new ShowCanvas(hoehe, breite);
		container.add(canvas);
		setSize(breite*50+breite*2,hoehe*50+hoehe*2+12);
		setTitle("Tetris V0.4");
		setVisible(true);
		setResizable(false);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				lastkey = e.getKeyChar();
				switch (lastkey) {
					case 's': {ControllerT.bewegung(-1,0,0);break;}
					case 'a': {ControllerT.bewegung(0,-1,0);break;}
					case 'd': {ControllerT.bewegung(0,1,0);break;}
					case 'q': {ControllerT.bewegung(0,0,1);break;}
					case 'e': {ControllerT.bewegung(0,0,-1);break;}

				default:
					break;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

  public static void init() throws IOException {
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    window.setBounds(0, 0, 800, 827);
		    window.setTitle("Schachapp");
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
		System.out.println(Spielfeld.f.valid());

		g.setColor(Color.BLACK);
		
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				g.setColor(Color.BLACK);
				g.fillRect(b*50, h*50, 50, 50);
				g.setColor(Color.GRAY); g.fillRect(b*50+2, h*50+2, 48, 48);//TODO: nur zum debugen => entfernen
					
				if(Spielfeld.f.form[h][b]) {
					g.setColor(Spielfeld.f.farbe);
					g.fillRect(b*50+2, h*50+2, 48, 48);
					continue;
				}
				if(Spielfeld.spielfeld_belegung[h][b]) {
					g.setColor(Spielfeld.spielfeld_farben[h][b]);
					g.fillRect(b*50+2, h*50+2, 48, 48);
					continue;
				}
				else {
					g.setColor(Color.WHITE);
					g.fillRect(b*50+2, h*50+2, 48, 48);
				}
				
			}
		}
		
		
	}
}
