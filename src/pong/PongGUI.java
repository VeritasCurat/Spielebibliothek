package pong;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tetris.TetrisGUI;

public class PongGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	static int hoehe, breite;
    ShowCanvas canvas;
	static JFrame window = new JFrame();
	static int mouseX, mouseY;
	
	
	public PongGUI(int hoehe, int breite) {
		super();
		PongGUI.hoehe = hoehe; PongGUI.breite = breite;
		Container container = getContentPane();
		canvas = new ShowCanvas(hoehe, breite);
		container.add(canvas);
		setSize(breite,hoehe);
		setTitle("Pong V0.8");
		setVisible(true);
		setResizable(false);
		
	}
}

class ShowCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	static int x, y, hoehe, breite;
	static char lastkey = '\0';
	
	static int mouseX;

	ShowCanvas(int hoehe, int breite) {
		ShowCanvas.hoehe = hoehe; ShowCanvas.breite = breite;
		setBackground(Color.WHITE);
		setSize(breite, hoehe);
		
	
	
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				x = arg0.getX();
				y = arg0.getY();
				
				mouseX = x;
				PongKelle.moveX(x);
				
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(!PongController.hauptmenü && PongKelle.ball_klebt) {
					PongKelle.ball_klebt=false;
					PongBall.init(PongKelle.xm,PongKelle.y-PongKelle.sizeY,250,-250);
				}
				if(PongController.hauptmenü) {
					for(int l=0; l<PongLevel.anzLvl; l++) {
						int x = l%10;
						y = (int) x/10;
						
						if(arg0.getX() >= 200 + x*100 + x*20 && arg0.getX() <= 200 + x*100 + x*20+100 && arg0.getY() >= 200 + y*50 + y*20 && arg0.getY() <= 200 + y*50 + y*20+50) {
							PongController.level = l+1;
							PongController.hauptmenü = false;
							break;
						}
							
					}
				}
				
				
			}
		});
		
		
	}	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(PongController.hauptmenü) {
			g.setColor(Color.red);
			g.setFont(new Font("Arial", Font.PLAIN, 100)); 
			g.drawString("Hauptmenü", breite/2 - 250, 100);
	
			
			g.setFont(new Font("Arial", Font.PLAIN, 25)); 
			for(int l=0; l<PongLevel.anzLvl; l++) {
				int x = l%10;
				y = (int) x/10;
				
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(200 + x*100 + x*20, 200 + y*50 + y*20, 100, 50);
				
				g.setColor(Color.BLACK);
				if(l==3) {
					g.drawString("Zufall", 200 + x*100 + x*20+10, 200 + y*50 + y*20+30);
				}
				else g.drawString("Level: "+(l+1), 200 + x*100 + x*20+10, 200 + y*50 + y*20+30);

			}
			
		}
		else {
			//BLUR von Ball
//				int blurentfernungX=(int) -(PongBall.size*0.5*PongBall.vx/1000);
//				int blurentfernungY=(int) -(PongBall.size*0.5*PongBall.vy/1000);
//				for(int i=PongBall.Fade.length-1; i>0; i--) {
//					g.setColor(PongBall.Fade[i]);
//					g.fillOval((int) PongBall.x+blurentfernungX*i, (int) PongBall.y+blurentfernungY*i, PongBall.size*(1-(i*i)/16), PongBall.size*(1-(i*i)/16));
//				}
			
			//Ball
				g.setColor(PongBall.Main);
				g.fillOval((int) PongBall.x, (int) PongBall.y, PongBall.size, PongBall.size);
				g.setColor(Color.blue);
				g.drawRect((int) PongBall.x, (int) PongBall.y, 2, 2);
			
			//Kelle
				int rahmen=2;
				g.setColor(Color.GRAY);
				g.fillRoundRect((int) PongKelle.xm-(int) PongKelle.sizeX/2, (int) PongKelle.y, (int) PongKelle.sizeX,(int) PongKelle.sizeY,1,1);
				g.setColor(Color.BLACK);
				g.fillRoundRect((int) PongKelle.xm+rahmen-(int) PongKelle.sizeX/2, (int) PongKelle.y+rahmen, (int) PongKelle.sizeX-rahmen*2,(int) PongKelle.sizeY-rahmen*2,1,1);
			
			//Blöcke zeichnen
				rahmen = 2;
				int alive = 0;
				for(int i=0; i<PongLevel.sizeX; i++) {
					for(int j=0; j<PongLevel.sizeY; j++) {
						if(!PongLevel.b[i][j].alive)continue;
						++alive;
						g.setColor(Color.black);
						g.fillRect(PongLevel.b[i][j].x,PongLevel.b[i][j].y,PongLevel.b[i][j].sizeX, PongLevel.b[i][j].sizeY);
						
						g.setColor(PongLevel.b[i][j].c);
						g.fillRect(PongLevel.b[i][j].x+rahmen,PongLevel.b[i][j].y+rahmen,PongLevel.b[i][j].sizeX-rahmen*2, PongLevel.b[i][j].sizeY-rahmen*2);
					}
				}
				
				if(alive == 0) {
					PongController.level = -1;
					PongController.change_level=true;;	
				}
		}
		
	}
	
	
}

