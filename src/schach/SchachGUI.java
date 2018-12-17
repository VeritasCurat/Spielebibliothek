package schach;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SchachGUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JFrame window = new JFrame();
    
    static ShowCanvas canvas;
    

	public SchachGUI() {
		super();
		Container container = getContentPane();
		canvas = new ShowCanvas();
		container.add(canvas);
		setSize(800, 835);
		setTitle("Schachapp V0.9");
		setVisible(true);
		setResizable(false);
	}

  public static void init() throws IOException {
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    window.setBounds(0, 0, 800, 827);
		    window.setTitle("Schachapp");
		    window.setVisible(true);
  }
  
  
public static void figurenauswahl(int x2, int y2,String farbe) {
	// TODO Auto-generated method stub
	canvas.figurenauswahl(x2, y2, farbe);
}


}
class ShowCanvas extends JPanel {
	
	Schachbrett lichtspiel;
	
	static int main = 255; static int side = 200; static double dark_factor = 0.5;
	static Color Vorschlag_hell = new Color(side, main, side);
	static Color Vorschlag_dunkel = new Color((int) (dark_factor*side), (int) (dark_factor*main), (int) (dark_factor*side));

	static Color Markierung_hell = new Color(main, side, side);
	static Color Markierung_dunkel = new Color((int) (dark_factor*main), (int) (dark_factor*side), (int) (dark_factor*side));

    public static boolean figurenauswahl=false; int X, Y, x1, x2, y1, y2; //x1,y1 linke, obere Ecke, x2,y2 rechte, untere Ecke des Fensters zur Figurenauswahl, 
    String farbe="";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x, y;
	static int mouseX; static int mouseY;
	MediaTracker mt = new MediaTracker(this);

	BufferedImage image_wK;
	BufferedImage image_wD;
	BufferedImage image_wT;
	BufferedImage image_wP;
	BufferedImage image_wL;
	BufferedImage image_wB;

	BufferedImage image_sK;
	BufferedImage image_sD;
	BufferedImage image_sT;
	BufferedImage image_sP;
	BufferedImage image_sL;
	BufferedImage image_sB;
	
	BufferedImage image_Wallpaper;


	ShowCanvas() {
		setBackground(Color.white);
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
				mouseX = arg0.getX(); mouseY = arg0.getY();
			
				if(figurenauswahl) {
				
					if(mouseX>x1 && mouseX < x2 && mouseY > y1 && mouseY < y2) {
						int begrenzungDame = x1+image_wD.getWidth(); 
						int begrenzungTurm = x1+image_wD.getWidth()+image_wT.getWidth()+5;
						int begrenzungPferd = x1+image_wD.getWidth()+image_wT.getWidth()+image_wP.getWidth()+5;
						System.out.println(begrenzungDame+" "+begrenzungTurm+" "+begrenzungPferd);
						if(mouseX < begrenzungDame) {
							x1 = x2 = y1 = y2 = -1; farbe = ""; figurenauswahl = false;
							SchachController.bauerntausch(X, Y, "Dame", farbe);
						}
						else if(mouseX > begrenzungDame && mouseX < begrenzungTurm) {
							x1 = x2 = y1 = y2 = -1; farbe = ""; figurenauswahl = false;
							SchachController.bauerntausch(X, Y, "Turm", farbe);
						}
						else if(mouseX > begrenzungTurm && mouseX < begrenzungPferd) {
							x1 = x2 = y1 = y2 = -1; farbe = ""; figurenauswahl = false;
							SchachController.bauerntausch(X, Y, "Pferd", farbe);
						}
						else if(mouseX > begrenzungPferd) {
							x1 = x2 = y1 = y2 = -1; farbe = ""; figurenauswahl = false;
							SchachController.bauerntausch(X, Y, "L‰ufer", farbe);
						}
					}
					return; 
				}
				SchachController.spieler_aktion();
			}
		});
		
		String pfad = new File("").getAbsolutePath();
		System.out.println(pfad);
		if(pfad.contains("Spielebibliothek")) {
			pfad+="\\src\\schach";
		}


		Image img_wK = getToolkit().getImage(pfad+"\\sources\\weiﬂerKˆnig.png");
		Image img_wD = getToolkit().getImage(pfad+"\\sources\\weiﬂeDame.png");
		Image img_wT = getToolkit().getImage(pfad+"\\sources\\weiﬂerTurm.png");
		Image img_wP = getToolkit().getImage(pfad+"\\sources\\weiﬂesPferd.png");
		Image img_wL = getToolkit().getImage(pfad+"\\sources\\weiﬂerL‰ufer.png");
		Image img_wB = getToolkit().getImage(pfad+"\\sources\\weiﬂerBauer.png");

		Image img_sK = getToolkit().getImage(pfad+"\\sources\\schwarzerKˆnig.png");
		Image img_sD = getToolkit().getImage(pfad+"\\sources\\schwarzeDame.png");
		Image img_sT = getToolkit().getImage(pfad+"\\sources\\schwarzerTurm.png");
		Image img_sP = getToolkit().getImage(pfad+"\\sources\\schwarzesPferd.png");
		Image img_sL = getToolkit().getImage(pfad+"\\sources\\schwarzerL‰ufer.png");
		Image img_sB = getToolkit().getImage(pfad+"\\sources\\schwarzerBauer.png");

		Image img_Wallpaper= getToolkit().getImage(pfad+"\\sources\\Schach_Wallpaper.jpg");
		
		mt.addImage(img_wK, 1);
		mt.addImage(img_wD, 2);
		mt.addImage(img_wT, 3);
		mt.addImage(img_wP, 4);
		mt.addImage(img_wL, 5);
		mt.addImage(img_wB, 6);

		mt.addImage(img_sK, 7);
		mt.addImage(img_sD, 8);
		mt.addImage(img_sT, 9);
		mt.addImage(img_sP, 10);
		mt.addImage(img_sL, 11);
		mt.addImage(img_sB, 12);
		
		mt.addImage(img_Wallpaper, 13);

		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out.println("Image not found.");
		}
		image_wK = new BufferedImage(img_wK.getWidth(this), img_wK.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D wK = image_wK.createGraphics();
		wK.drawImage(img_wK, 0, 0, this);
		
		image_wD = new BufferedImage(img_wD.getWidth(this), img_wD.getHeight(this), BufferedImage.TYPE_INT_ARGB);
		Graphics2D wD = image_wD.createGraphics();
		wD.drawImage(img_wD, 0, 0, this);
		
		image_wT = new BufferedImage(img_wT.getWidth(this), img_wT.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D wT = image_wT.createGraphics();
		wT.drawImage(img_wT, 0, 0, this);
		
		image_wP = new BufferedImage(img_wP.getWidth(this), img_wP.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D wP = image_wP.createGraphics();
		wP.drawImage(img_wP, 0, 0, this);

		image_wL = new BufferedImage(img_wL.getWidth(this), img_wL.getHeight(this), BufferedImage.TYPE_INT_ARGB);
		Graphics2D wL = image_wL.createGraphics();
		wL.drawImage(img_wL, 0, 0, this);
		
		image_wB = new BufferedImage(img_wB.getWidth(this), img_wB.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D wB = image_wB.createGraphics();
		wB.drawImage(img_wB, 0, 0, this);
		
		image_sK = new BufferedImage(img_sK.getWidth(this), img_sK.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D sK = image_sK.createGraphics();
		sK.drawImage(img_sK, 0, 0, this);
		
		image_sD = new BufferedImage(img_sD.getWidth(this), img_sD.getHeight(this), BufferedImage.TYPE_INT_ARGB);
		Graphics2D sD = image_sD.createGraphics();
		sD.drawImage(img_sD, 0, 0, this);
		
		image_sT = new BufferedImage(img_sT.getWidth(this), img_sT.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D sT = image_sT.createGraphics();
		sT.drawImage(img_sT, 0, 0, this);
		
		image_sP = new BufferedImage(img_sP.getWidth(this), img_sP.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D sP = image_sP.createGraphics();
		sP.drawImage(img_sP, 0, 0, this);

		image_sL = new BufferedImage(img_sL.getWidth(this), img_sL.getHeight(this), BufferedImage.TYPE_INT_ARGB);
		Graphics2D sL = image_sL.createGraphics();
		sL.drawImage(img_sL, 0, 0, this);
		
		image_sB = new BufferedImage(img_sB.getWidth(this), img_sB.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D sB = image_sB.createGraphics();
		sB.drawImage(img_sB, 0, 0, this);
		
		image_Wallpaper = new BufferedImage(img_Wallpaper.getWidth(this), img_Wallpaper.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D wp = image_Wallpaper.createGraphics();
		wp.drawImage(image_Wallpaper, 0, 0, this); //TODO 
	}
	
	
	public void figurenauswahl(int X2, int Y2, String Farbe) {
		figurenauswahl = true;
		farbe = Farbe;
		X = X2; Y = Y2;
		x2 = X2; y2 = Y2;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		
		
		
  		g.setColor(Color.GRAY);
		for(int a=0; a<8;a++) {
			  for(int b=0; b<8;b++) {
				  	if(SchachController.markiert && SchachController.auswahlX == a && SchachController.auswahlY == b) {
				  	    if(a%2 != b%2)g.setColor(Markierung_dunkel);
				  	    else g.setColor(Markierung_hell);
				  		g.fillRect(a*100, b*100, 100, 100);
				  		g.setColor(Color.GRAY);
				  		continue;
				  	}
				  	if(SchachController.vorschl‰ge[a][b]) {
				  		if(a%2 != b%2)g.setColor(Vorschlag_dunkel);
				  	    else g.setColor(Vorschlag_hell);
				  	    g.fillRect(a*100+2, b*100+2, 98, 98);
				  		g.setColor(Color.GRAY);
				  		continue;
				  	}
				    if(a%2 != b%2) g.fillRect(a*100, b*100, 100, 100);
				    else g.drawRect(a*100, b*100, 100, 100);
			  }
		}
		
				
		for(int b=0; b<8; b++) {
			for(int a=0; a<8; a++) {
				if(lichtspiel.figuren[a][b].typ.equals("") || lichtspiel.figuren[a][b].farbe.equals("")) continue;
				String z = lichtspiel.figuren[a][b].farbe.substring(0,1)+lichtspiel.figuren[a][b].typ.substring(0,1);
				if(z != null && z.equals("wK")) {			g2D.drawImage(image_wK, a*100, b*100, this); continue;}
				else if(z != null && z.equals("wD")) {	g2D.drawImage(image_wD, a*100, b*100, this); continue;}
				else if(z != null && z.equals("wB")) {	g2D.drawImage(image_wB, a*100, b*100, this); continue;}
				else if(z != null && z.equals("wL")) 	g2D.drawImage(image_wL, a*100, b*100, this);
				else if(z != null && z.equals("wP")) 	g2D.drawImage(image_wP, a*100, b*100, this);
				else if(z != null && z.equals("wT")) 	g2D.drawImage(image_wT, a*100, b*100, this);
				else if(z != null && z.equals("sK"))	g2D.drawImage(image_sK, a*100, b*100, this);
				else if(z != null && z.equals("sD"))	g2D.drawImage(image_sD, a*100, b*100, this);
				else if(z != null && z.equals("sB"))	g2D.drawImage(image_sB, a*100, b*100, this);
				else if(z != null && z.equals("sL"))	g2D.drawImage(image_sL, a*100, b*100, this);
				else if(z != null && z.equals("sP"))	g2D.drawImage(image_sP, a*100, b*100, this);
				else if(z != null && z.equals("sT"))	g2D.drawImage(image_sT, a*100, b*100, this);
			}
		}	
		
		if(figurenauswahl) {
			System.out.println("FIgurenauswahl:"+x2+" "+y2);
			int breite=0, hoehe=0;
			if(SchachController.farbe.equals("weiﬂ")){
				breite = image_wD.getWidth()+image_wL.getWidth()+image_wT.getWidth()+image_wP.getWidth()+(5*4)+5; hoehe = Math.max(Math.max(image_wD.getHeight(),image_wL.getHeight()), Math.max(image_wT.getHeight(),image_wP.getHeight()))+10;
			}
			else if(SchachController.farbe.equals("schwarz")) {
				breite = image_sD.getWidth()+image_sL.getWidth()+image_sT.getWidth()+image_sP.getWidth()+(5*4)+5; hoehe = Math.max(Math.max(image_sD.getHeight(),image_sL.getHeight()), Math.max(image_sT.getHeight(),image_sP.getHeight()))+10;
			}
			if(x2<4) {
				x1 = x2*100+50; 
			}
			else if(x2>=4) {
				x1 = x2*100-breite+50;
			}
			if(y2 == 7) {
				y1=(y2*100)-50;
			}
			else if(y2 == 0) {
				y1=(y2*100)+50;
			}
			y2=y1+hoehe;
			x2 = x1+breite; 
			System.out.println("x1:"+x1+", y1:"+y1+", x2: "+x2+" , y2: "+y2);
			System.out.println("TESTUS 777");
			g.setColor(Color.black);
			g.fillRect(x1, y1, breite,hoehe);
			
			g.setColor(Color.gray);
			System.out.println(SchachController.farbe);
			if(farbe.equals("weiﬂ")){
				g.fillRect(x1+5, y1+5, image_wD.getWidth(), image_wD.getWidth());
				g2D.drawImage(image_wD, x1+5, y1+5, this);
				x1+=image_wD.getWidth()+5;
				
				g.fillRect(x1+5, y1+5, image_wT.getWidth(), image_wT.getWidth());
				g2D.drawImage(image_wT, x1+5, y1+5, this);
				x1+=image_wT.getWidth()+5;
				
				g.fillRect(x1+5, y1+5, image_wP.getWidth(), image_wP.getWidth());
				g2D.drawImage(image_wP, x1+5, y1+5, this);
				x1+=image_wP.getWidth()+5;
				
				g.fillRect(x1+5, y1+5, image_wL.getWidth(), image_wL.getWidth());
				g2D.drawImage(image_wL, x1+5, y1+5, this);
				x1+=image_wL.getWidth()+5;
			}
			else if(farbe.equals("schwarz")){
				g.fillRect(x1+5, y1+5, image_sD.getWidth(), image_sD.getWidth());
				g2D.drawImage(image_sD, x1+5, y1+5, this);
				x1+=image_sD.getWidth()+5;
				
				g.fillRect(x1+5, y1+5, image_sT.getWidth(), image_sT.getWidth());
				g2D.drawImage(image_sT, x1+5, y1+5, this);
				x1+=image_sT.getWidth()+5;
				
				g.fillRect(x1+5, y1+5, image_sP.getWidth(), image_sP.getWidth());
				g2D.drawImage(image_sP, x1+5, y1+5, this);
				x1+=image_sP.getWidth()+5;
				
				g.fillRect(x1+5, y1+5, image_sL.getWidth(), image_sL.getWidth());
				g2D.drawImage(image_sL, x1+5, y1+5, this);
				x1+=image_sL.getWidth()+5;
			}
			x1-=breite+5;

		}
	
		if(!lichtspiel.spiel_aktiv) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 1000);
			g2D.drawImage(image_Wallpaper, 0, 0, this);
			g.setColor(Color.red); 
			SchachController.changeFarbe();
			g.setFont(new Font("Arial", Font.PLAIN, 20)); 
			g.drawString("Spiel zuende! "+SchachController.farbe+" gewinnt!", 370, 400);
			g.drawString("Zum beenden klicken", 400, 500);
		}
	}

}


