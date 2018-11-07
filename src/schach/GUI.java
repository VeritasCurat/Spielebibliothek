package schach;

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



class MyCanvas extends JComponent {
	
	
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static int xSize=100;	
  public static String[][] figuren = new String[8][8];

  public void paint(Graphics g) {
	  for(int x=0; x<8;x++) {
		  for(int y=0; y<8;y++) {
			    if(x%2 != y%2) g.fillRect(x*100, y*100, 100, 100);
			    else g.drawRect(x*100, y*100, 100, 100);
		  }
	  }
  }
}

public class GUI extends JFrame{
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
		setTitle("Schachapp V0.8");
		setVisible(true);
		setResizable(false);
	}

  public static void init() throws IOException {
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    window.setBounds(0, 0, 800, 827);
		    window.setTitle("Schachapp");
		    MyCanvas.xSize = 110;
		    window.getContentPane().add(new MyCanvas());
		    window.setVisible(true);
		    
		    
		  
  }
  
  public static void darst_wK(int x, int y) throws IOException {
	  BufferedImage myPicture = ImageIO.read(new File("/home/johannes/eclipse-workspace/Schach/src/schach/Bilder/weißerKönig.png"));
	    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	    picLabel.setBounds(0, 0, 100, 100);
	    window.add(picLabel);
	    
  }
}
class ShowCanvas extends JPanel {
	
	static int main = 255; static int side = 200; static double dark_factor = 0.5;
	static Color Vorschlag_hell = new Color(side, main, side);
	static Color Vorschlag_dunkel = new Color((int) (dark_factor*side), (int) (dark_factor*main), (int) (dark_factor*side));

	static Color Markierung_hell = new Color(main, side, side);
	static Color Markierung_dunkel = new Color((int) (dark_factor*main), (int) (dark_factor*side), (int) (dark_factor*side));

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x, y;
	static int mouseX; static int mouseY;
	MediaTracker mt = new MediaTracker(this);

	BufferedImage image_wK;
	//MovableImage image_wK = new MovableImage("C:\\Users\\Johannes\\eclipse-workspace\\Schach\\src\\Bilder\\weißerKönig.png", mt, this);
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
				Controller.spieler_aktion();
			}
		});
		
		String pfad=new File("").getAbsolutePath();
		System.out.println(pfad);

		Image img_wK = getToolkit().getImage(pfad+"\\src\\Bilder\\weißerKönig.png");
		Image img_wD = getToolkit().getImage(pfad+"\\src\\Bilder\\weißeDame.png");
		Image img_wT = getToolkit().getImage(pfad+"\\src\\Bilder\\weißerTurm.png");
		Image img_wP = getToolkit().getImage(pfad+"\\src\\Bilder\\weißesPferd.png");
		Image img_wL = getToolkit().getImage(pfad+"\\src\\Bilder\\weißerLäufer.png");
		Image img_wB = getToolkit().getImage(pfad+"\\src\\Bilder\\weißerBauer.png");

		Image img_sK = getToolkit().getImage(pfad+"\\src\\Bilder\\schwarzerKönig.png");
		Image img_sD = getToolkit().getImage(pfad+"\\src\\Bilder\\schwarzeDame.png");
		Image img_sT = getToolkit().getImage(pfad+"\\src\\Bilder\\schwarzerTurm.png");
		Image img_sP = getToolkit().getImage(pfad+"\\src\\Bilder\\schwarzesPferd.png");
		Image img_sL = getToolkit().getImage(pfad+"\\src\\Bilder\\schwarzerLäufer.png");
		Image img_sB = getToolkit().getImage(pfad+"\\src\\Bilder\\schwarzerBauer.png");

		Image img_Wallpaper= getToolkit().getImage(pfad+"\\src\\Bilder\\Schach_Wallpaper.jpg");
		
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		
  		g.setColor(Color.GRAY);
		for(int a=0; a<8;a++) {
			  for(int b=0; b<8;b++) {
				  	if(Controller.markiert && Controller.auswahlX == a && Controller.auswahlY == b) {
				  	    if(a%2 != b%2)g.setColor(Markierung_dunkel);
				  	    else g.setColor(Markierung_hell);
				  		g.fillRect(a*100, b*100, 100, 100);
				  		g.setColor(Color.GRAY);
				  		continue;
				  	}
				  	if(Controller.vorschläge[a][b]) {
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
				if(Schachbrett.figuren[a][b].typ.equals("") || Schachbrett.figuren[a][b].farbe.equals("")) continue;
				String z = Schachbrett.figuren[a][b].farbe.substring(0,1)+Schachbrett.figuren[a][b].typ.substring(0,1);
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
		
		if(!Schachbrett.spiel_aktiv) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 1000);
			g2D.drawImage(image_Wallpaper, 0, 0, this);
			g.setColor(Color.red); 
			Controller.changeFarbe();
			g.setFont(new Font("Arial", Font.PLAIN, 20)); 
			g.drawString("Spiel zuende! "+Controller.farbe+" gewinnt!", 370, 400);
			g.drawString("Zum beenden klicken", 400, 500);
		}
	}

}

@SuppressWarnings("serial")
//TODO: 
class MovableImage extends JPanel{
	BufferedImage a;
	Image A;
	
	public MovableImage(String source, MediaTracker mt, JPanel S) {
		/*
		 * 	BufferedImage image_wD;
		 * 	Image img_wD = getToolkit().getImage("C:\\Users\\Johannes\\eclipse-workspace\\Schach\\src\\Bilder\\weißeDame.png")
		*/
		
		A = getToolkit().getImage(source);
		System.out.println(A.getAccelerationPriority());
		System.out.println(A.getWidth(this)); //TODO: hier liegt das Problem "this" = JPanel?
		mt.addImage(A, 1);
		
		a = new BufferedImage(A.getWidth(this), A.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D wK = ((BufferedImage) A).createGraphics();
		wK.drawImage(A, 0, 0, this);
	}
	
	public BufferedImage retImage() {
		return a;
	}
}
