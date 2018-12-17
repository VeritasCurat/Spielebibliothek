package Poker;

//TODO: Bug: Piek8 wird nicht angezeigt


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.MediaTracker;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import Poker.ShowCanvas;

public class PokerGUI extends JFrame{

	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		static JFrame window = new JFrame();
	    
	    ShowCanvas canvas;
	    JTextField tf;
	    

		public PokerGUI(int spieler_anz) {
			super();
			Container container = getContentPane();
			canvas = new ShowCanvas(spieler_anz);
			tf = new JTextField("12 * 3 + 2", 20);
			container.add(tf);
			container.add(canvas);
			
		
			
			setSize(1200, 800);
			setTitle("Pokerapp V0.2");
			setVisible(true);
			setResizable(false);
			setBackground(Color.GREEN.darker());
		}

	  public static void init() throws IOException {
			    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    window.setBounds(0, 0, 1200, 800);
			    window.setTitle("Pokerapp V0.2");
			    window.setVisible(true);
	  }
	  
}

class ShowCanvas extends JPanel {

	int anz_sp;
	int aktiver_spieler =-1;
	public String HandSpieler[];
	public int Budget[];
	public int Spieler[];
	public int Einsatz[];
	int mark_player = 1;

	
	public ArrayList<String> darstellungC = null;
	public ArrayList<String> darstellungS1 = null;
	public ArrayList<String> darstellungS2 = null;
	public ArrayList<String> darstellungS3 = null;
	public ArrayList<String> darstellungS4 = null;

	
	//Bilder
	BufferedImage Piek_2;
	BufferedImage Piek_3;
	BufferedImage Piek_4;
	BufferedImage Piek_5;
	BufferedImage Piek_6;
	BufferedImage Piek_7;
	BufferedImage Piek_8;
	BufferedImage Piek_9;
	BufferedImage Piek_10;
	BufferedImage Piek_Bube;
	BufferedImage Piek_Dame;
	BufferedImage Piek_König;
	BufferedImage Piek_Ass;
	
	BufferedImage Herz_2;
	BufferedImage Herz_3;
	BufferedImage Herz_4;
	BufferedImage Herz_5;
	BufferedImage Herz_6;
	BufferedImage Herz_7;
	BufferedImage Herz_8;
	BufferedImage Herz_9;
	BufferedImage Herz_10;
	BufferedImage Herz_Bube;
	BufferedImage Herz_Dame;
	BufferedImage Herz_König;
	BufferedImage Herz_Ass;
	
	BufferedImage Kreuz_2;
	BufferedImage Kreuz_3;
	BufferedImage Kreuz_4;
	BufferedImage Kreuz_5;
	BufferedImage Kreuz_6;
	BufferedImage Kreuz_7;
	BufferedImage Kreuz_8;
	BufferedImage Kreuz_9;
	BufferedImage Kreuz_10;
	BufferedImage Kreuz_Bube;
	BufferedImage Kreuz_Dame;
	BufferedImage Kreuz_König;
	BufferedImage Kreuz_Ass;
	
	BufferedImage Caro_2;
	BufferedImage Caro_3;
	BufferedImage Caro_4;
	BufferedImage Caro_5;
	BufferedImage Caro_6;
	BufferedImage Caro_7;
	BufferedImage Caro_8;
	BufferedImage Caro_9;
	BufferedImage Caro_10;
	BufferedImage Caro_Bube;
	BufferedImage Caro_Dame;
	BufferedImage Caro_König;
	BufferedImage Caro_Ass;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addToSpieler(int spieler, String darstellung) {
		if(spieler>anz_sp)return;
		switch(spieler) {
			case 0: {
				darstellungC.add(darstellung);
				break;
			}
			case 1: {
				darstellungS1.add(darstellung);
				break;
			}
			case 2: {
				darstellungS2.add(darstellung);
				break;
			}
			case 3: {
				darstellungS3.add(darstellung);
				break;
			}
			case 4: {
				darstellungS4.add(darstellung);
				break;
			}
		}
	}
	/**
	 * 
	 * @param sbi = Original
	 * @param imageType
	 * @param dWidht
	 * @param dHeight
	 * @param fWidth
	 * @param fHeight
	 * @return
	 */
	public static BufferedImage scale(BufferedImage original, int height, int width) {
		Image tmp = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, original.TYPE_INT_ARGB);
		Graphics2D g = resized.createGraphics();
		g.drawImage(tmp, 0, 0, null);
		g.dispose();
		/*
		if(original != null) {
			dbi = new BufferedImage(dWidth, dHeight, original.getType());
			Graphics2D g = dbi.createGraphics();
			double a=original.getWidth()/dWidth; double b=original.getHeight()/dHeight;
			AffineTransform at = AffineTransform.getScaleInstance(a, b);
			g.drawRenderedImage(dbi, at);
//			g.drawImage(dbi, 0, 0, dWidth, dHeight, null);
//			g.dispose();
		}
		*/
		return resized;
	}
	
	ShowCanvas(int sp) {
		HandSpieler = new String[sp];
		Budget = new int[sp];
		Einsatz = new int[sp+2];


		setBackground(Color.GREEN.darker().darker().darker());
		setSize(1200, 800);
		anz_sp = sp; 
		for(int i=0; i<=sp; i++) {
			if(i<sp)Einsatz[i] = 0;
			if(i==0)darstellungC = new ArrayList<String>();
			if(i==1)darstellungS1 = new ArrayList<String>();
			if(i==2)darstellungS2 = new ArrayList<String>();
			if(i==3)darstellungS3 = new ArrayList<String>();
			if(i==4)darstellungS4 = new ArrayList<String>();
		}
		
		int x, y;
		int mouseX; int mouseY;

		
		MediaTracker mt = new MediaTracker(this);
		
		
		String pfad = new File("").getAbsolutePath();
		System.out.println(pfad);
		if(pfad.contains("Spielebibliothek")) {
			pfad+="\\src";
		}
		Image img_p2 = getToolkit().getImage("C:\\Users\\Johannes\\GitkrakenProjects\\Spielebibliothek\\src\\sources\\Piek2.png"); mt.addImage(img_p2, 1);
		Image img_p3 = getToolkit().getImage(pfad+"\\sources\\Piek3.PNG"); mt.addImage(img_p3, 2);
		Image img_p4 = getToolkit().getImage(pfad+"\\sources\\Piek4.PNG"); mt.addImage(img_p4, 3);
		Image img_p5 = getToolkit().getImage(pfad+"\\sources\\Piek5.PNG"); mt.addImage(img_p5, 4);
		Image img_p6 = getToolkit().getImage(pfad+"\\sources\\Piek6.PNG"); mt.addImage(img_p6, 5);
		Image img_p7 = getToolkit().getImage(pfad+"\\sources\\Piek7.PNG"); mt.addImage(img_p7, 6);
		Image img_p8 = getToolkit().getImage(pfad+"\\sources\\Piek8.PNG"); mt.addImage(img_p8, 7);
		Image img_p9 = getToolkit().getImage(pfad+"\\sources\\Piek9.PNG"); mt.addImage(img_p9, 8);
		Image img_p10 = getToolkit().getImage(pfad+"\\sources\\Piek10.PNG"); mt.addImage(img_p10, 9);
		Image img_pB = getToolkit().getImage(pfad+"\\sources\\PiekBube.PNG"); mt.addImage(img_pB, 10);
		Image img_pD = getToolkit().getImage(pfad+"\\sources\\PiekDame.PNG"); mt.addImage(img_pD, 11);
		Image img_pK = getToolkit().getImage(pfad+"\\sources\\PiekKönig.PNG"); mt.addImage(img_pK, 12);
		Image img_pA = getToolkit().getImage(pfad+"\\sources\\PiekAss.PNG"); mt.addImage(img_pA, 13);
		
		Image img_c2 = getToolkit().getImage(pfad+"\\sources\\Caro2.PNG"); mt.addImage(img_c2, 14);
		Image img_c3 = getToolkit().getImage(pfad+"\\sources\\Caro3.PNG"); mt.addImage(img_c3, 15);
		Image img_c4 = getToolkit().getImage(pfad+"\\sources\\Caro4.PNG"); mt.addImage(img_c4, 16);
		Image img_c5 = getToolkit().getImage(pfad+"\\sources\\Caro5.PNG"); mt.addImage(img_c5, 17);
		Image img_c6 = getToolkit().getImage(pfad+"\\sources\\Caro6.PNG"); mt.addImage(img_c6, 18);
		Image img_c7 = getToolkit().getImage(pfad+"\\sources\\Caro7.PNG"); mt.addImage(img_c7, 19);
		Image img_c8 = getToolkit().getImage(pfad+"\\sources\\Caro8.PNG"); mt.addImage(img_c8, 20);
		Image img_c9 = getToolkit().getImage(pfad+"\\sources\\Caro9.PNG"); mt.addImage(img_c9, 21);
		Image img_c10 = getToolkit().getImage(pfad+"\\sources\\Caro10.PNG"); mt.addImage(img_c10, 22);
		Image img_cB = getToolkit().getImage(pfad+"\\sources\\CaroBube.PNG"); mt.addImage(img_cB, 23);
		Image img_cD = getToolkit().getImage(pfad+"\\sources\\CaroDame.PNG"); mt.addImage(img_cD, 24);
		Image img_cK = getToolkit().getImage(pfad+"\\sources\\CaroKönig.PNG"); mt.addImage(img_cK, 25);
		Image img_cA = getToolkit().getImage(pfad+"\\sources\\CaroAss.PNG"); mt.addImage(img_cA, 26);
		
		Image img_h2 = getToolkit().getImage(pfad+"\\sources\\Herz2.PNG"); mt.addImage(img_h2, 27);
		Image img_h3 = getToolkit().getImage(pfad+"\\sources\\Herz3.PNG"); mt.addImage(img_h3, 28);
		Image img_h4 = getToolkit().getImage(pfad+"\\sources\\Herz4.PNG"); mt.addImage(img_h4, 29);
		Image img_h5 = getToolkit().getImage(pfad+"\\sources\\Herz5.PNG"); mt.addImage(img_h5, 30);
		Image img_h6 = getToolkit().getImage(pfad+"\\sources\\Herz6.PNG"); mt.addImage(img_h6, 31);
		Image img_h7 = getToolkit().getImage(pfad+"\\sources\\Herz7.PNG"); mt.addImage(img_h7, 32);
		Image img_h8 = getToolkit().getImage(pfad+"\\sources\\Herz8.PNG"); mt.addImage(img_h8, 33);
		Image img_h9 = getToolkit().getImage(pfad+"\\sources\\Herz9.PNG"); mt.addImage(img_h9, 34);
		Image img_h10 = getToolkit().getImage(pfad+"\\sources\\Herz10.PNG"); mt.addImage(img_h10, 35);
		Image img_hB = getToolkit().getImage(pfad+"\\sources\\HerzBube.PNG"); mt.addImage(img_hB, 36);
		Image img_hD = getToolkit().getImage(pfad+"\\sources\\HerzDame.PNG"); mt.addImage(img_hD, 37);
		Image img_hK = getToolkit().getImage(pfad+"\\sources\\HerzKönig.PNG"); mt.addImage(img_hK, 38);
		Image img_hA = getToolkit().getImage(pfad+"\\sources\\HerzAss.PNG"); mt.addImage(img_hA, 39);
		
		Image img_k2 = getToolkit().getImage(pfad+"\\sources\\Kreuz2.PNG"); mt.addImage(img_k2, 40);
		Image img_k3 = getToolkit().getImage(pfad+"\\sources\\Kreuz3.PNG"); mt.addImage(img_k3, 41);
		Image img_k4 = getToolkit().getImage(pfad+"\\sources\\Kreuz4.PNG"); mt.addImage(img_k4, 42);
		Image img_k5 = getToolkit().getImage(pfad+"\\sources\\Kreuz5.PNG"); mt.addImage(img_k5, 43);
		Image img_k6 = getToolkit().getImage(pfad+"\\sources\\Kreuz6.PNG"); mt.addImage(img_k6, 44);
		Image img_k7 = getToolkit().getImage(pfad+"\\sources\\Kreuz7.PNG"); mt.addImage(img_k7, 45);
		Image img_k8 = getToolkit().getImage(pfad+"\\sources\\Kreuz8.PNG"); mt.addImage(img_k8, 46);
		Image img_k9 = getToolkit().getImage(pfad+"\\sources\\Kreuz9.PNG"); mt.addImage(img_k9, 47);
		Image img_k10 = getToolkit().getImage(pfad+"\\sources\\Kreuz10.PNG"); mt.addImage(img_k10, 48);
		Image img_kB = getToolkit().getImage(pfad+"\\sources\\KreuzBube.PNG"); mt.addImage(img_kB, 49);
		Image img_kD = getToolkit().getImage(pfad+"\\sources\\KreuzDame.PNG"); mt.addImage(img_kD, 50);
		Image img_kK = getToolkit().getImage(pfad+"\\sources\\KreuzKönig.PNG"); mt.addImage(img_kK, 51);
		Image img_kA = getToolkit().getImage(pfad+"\\sources\\KreuzAss.PNG"); mt.addImage(img_kA, 52);
		
		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out.println("Image not found.");
		}
		
		
		Piek_2 = new BufferedImage(img_p2.getWidth(this), img_p2.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p2 = Piek_2.createGraphics();
		p2.drawImage(img_p2, 0, 0, this);

		Piek_3 = new BufferedImage(img_p3.getWidth(this), img_p3.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p3 = Piek_3.createGraphics();
		p3.drawImage(img_p3, 0, 0, this);

		
		Piek_4 = new BufferedImage(img_p4.getWidth(this), img_p4.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p4 = Piek_4.createGraphics();
		p4.drawImage(img_p4, 0, 0, this);

		Piek_5 = new BufferedImage(img_p5.getWidth(this), img_p5.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p5 = Piek_5.createGraphics();
		p5.drawImage(img_p5, 0, 0, this);

		Piek_6 = new BufferedImage(img_p6.getWidth(this), img_p6.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p6 = Piek_6.createGraphics();
		p6.drawImage(img_p6, 0, 0, this);
		
		Piek_7 = new BufferedImage(img_p7.getWidth(this), img_p7.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p7 = Piek_7.createGraphics();
		p7.drawImage(img_p7, 0, 0, this);
		
		Piek_8 = new BufferedImage(img_p8.getWidth(this), img_p8.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p8 = Piek_8.createGraphics();
		p8.drawImage(Piek_8, 0, 0, this);
		
		Piek_9 = new BufferedImage(img_p9.getWidth(this), img_p9.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p9 = Piek_9.createGraphics();
		p9.drawImage(img_p9, 0, 0, this);
		
		Piek_10 = new BufferedImage(img_p10.getWidth(this), img_p10.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D p10 = Piek_10.createGraphics();
		p10.drawImage(img_p10, 0, 0, this);

		Piek_Bube = new BufferedImage(img_pB.getWidth(this), img_pB.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D pB = Piek_Bube.createGraphics();
		pB.drawImage(img_pB, 0, 0, this);

		Piek_Dame = new BufferedImage(img_pD.getWidth(this), img_pD.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D pD = Piek_Dame.createGraphics();
		pD.drawImage(img_pD, 0, 0, this);

		Piek_König = new BufferedImage(img_pK.getWidth(this), img_pK.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D pK = Piek_König.createGraphics();
		pK.drawImage(img_pK, 0, 0, this);

		Piek_Ass = new BufferedImage(img_pA.getWidth(this), img_pA.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D pA = Piek_Ass.createGraphics();
		pA.drawImage(img_pA, 0, 0, this);
		
		Caro_2 = new BufferedImage(img_c2.getWidth(this), img_c2.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c2 = Caro_2.createGraphics();
		c2.drawImage(img_c2, 0, 0, this);

		Caro_3 = new BufferedImage(img_c3.getWidth(this), img_c3.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c3 = Caro_3.createGraphics();
		c3.drawImage(img_c3, 0, 0, this);

		Caro_4 = new BufferedImage(img_c4.getWidth(this), img_c4.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c4 = Caro_4.createGraphics();
		c4.drawImage(img_c4, 0, 0, this);

		Caro_5 = new BufferedImage(img_c5.getWidth(this), img_c5.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c5 = Caro_5.createGraphics();
		c5.drawImage(img_c5, 0, 0, this);

		Caro_6 = new BufferedImage(img_c6.getWidth(this), img_c6.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c6 = Caro_6.createGraphics();
		c6.drawImage(img_c6, 0, 0, this);

		Caro_7 = new BufferedImage(img_c7.getWidth(this), img_c7.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c7 = Caro_7.createGraphics();
		c7.drawImage(img_c7, 0, 0, this);

		Caro_8 = new BufferedImage(img_c8.getWidth(this), img_c8.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c8 = Caro_8.createGraphics();		
		c8.drawImage(img_c8, 0, 0, this);

		Caro_9 = new BufferedImage(img_c9.getWidth(this), img_c9.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c9 = Caro_9.createGraphics();
		c9.drawImage(img_c9, 0, 0, this);

		Caro_10 = new BufferedImage(img_c10.getWidth(this), img_c10.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D c10 = Caro_10.createGraphics();
		c10.drawImage(img_c10, 0, 0, this);

		Caro_Bube = new BufferedImage(img_cB.getWidth(this), img_cB.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D cB = Caro_Bube.createGraphics();
		cB.drawImage(img_cB, 0, 0, this);

		Caro_Dame = new BufferedImage(img_cD.getWidth(this), img_cD.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D cD = Caro_Dame.createGraphics();
		cD.drawImage(img_cD, 0, 0, this);

		Caro_König = new BufferedImage(img_cK.getWidth(this), img_cK.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D cK = Caro_König.createGraphics();
		cK.drawImage(img_cK, 0, 0, this);

		Caro_Ass = new BufferedImage(img_cA.getWidth(this), img_cA.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D cA = Caro_Ass.createGraphics();
		cA.drawImage(img_cA, 0, 0, this);

		
		Herz_2 = new BufferedImage(img_h2.getWidth(this), img_h2.getHeight(this),	BufferedImage.TYPE_INT_ARGB);		
		Graphics2D h2 = Herz_2.createGraphics();
		h2.drawImage(img_h2, 0, 0, this);

		Herz_3 = new BufferedImage(img_h3.getWidth(this), img_h3.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h3 = Herz_3.createGraphics();
		h3.drawImage(img_h3, 0, 0, this);

		Herz_4 = new BufferedImage(img_h4.getWidth(this), img_h4.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h4 = Herz_4.createGraphics();		
		h4.drawImage(img_h4, 0, 0, this);

		Herz_5 = new BufferedImage(img_h5.getWidth(this), img_h5.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h5 = Herz_5.createGraphics();
		h5.drawImage(img_h5, 0, 0, this);

		Herz_6 = new BufferedImage(img_h6.getWidth(this), img_h6.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h6 = Herz_6.createGraphics();
		h6.drawImage(img_h6, 0, 0, this);

		Herz_7 = new BufferedImage(img_h7.getWidth(this), img_h7.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h7 = Herz_7.createGraphics();
		h7.drawImage(img_h7, 0, 0, this);

		Herz_8 = new BufferedImage(img_h8.getWidth(this), img_h8.getHeight(this),	BufferedImage.TYPE_INT_ARGB);		
		Graphics2D h8 = Herz_8.createGraphics();
		h8.drawImage(img_h8, 0, 0, this);

		Herz_9 = new BufferedImage(img_h9.getWidth(this), img_h9.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h9 = Herz_9.createGraphics();
		h9.drawImage(img_h9, 0, 0, this);

		Herz_10 = new BufferedImage(img_h10.getWidth(this), img_h10.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D h10 = Herz_10.createGraphics();
		h10.drawImage(img_h10, 0, 0, this);

		Herz_Bube = new BufferedImage(img_hB.getWidth(this), img_hB.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D hB = Herz_Bube.createGraphics();
		hB.drawImage(img_hB, 0, 0, this);

		Herz_Dame = new BufferedImage(img_hD.getWidth(this), img_hD.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D hD = Herz_Dame.createGraphics();
		hD.drawImage(img_hD, 0, 0, this);

		Herz_König = new BufferedImage(img_hK.getWidth(this), img_hK.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D hK = Herz_König.createGraphics();
		hK.drawImage(img_hK, 0, 0, this);

		Herz_Ass = new BufferedImage(img_hA.getWidth(this), img_hA.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D hA = Herz_Ass.createGraphics();
		hA.drawImage(img_hA, 0, 0, this);

		
		Kreuz_2 = new BufferedImage(img_k2.getWidth(this), img_k2.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k2 = Kreuz_2.createGraphics();
		k2.drawImage(img_k2, 0, 0, this);

		Kreuz_3 = new BufferedImage(img_k3.getWidth(this), img_k3.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k3 = Kreuz_3.createGraphics();
		k3.drawImage(img_k3, 0, 0, this);

		Kreuz_4 = new BufferedImage(img_k4.getWidth(this), img_k4.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k4 = Kreuz_4.createGraphics();
		k4.drawImage(img_k4, 0, 0, this);

		Kreuz_5 = new BufferedImage(img_k5.getWidth(this), img_k5.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k5 = Kreuz_5.createGraphics();
		k5.drawImage(img_k5, 0, 0, this);

		Kreuz_6 = new BufferedImage(img_k6.getWidth(this), img_k6.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k6 = Kreuz_6.createGraphics();
		k6.drawImage(img_k6, 0, 0, this);

		Kreuz_7 = new BufferedImage(img_k7.getWidth(this), img_k7.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k7 = Kreuz_7.createGraphics();
		k7.drawImage(img_k7, 0, 0, this);

		Kreuz_8 = new BufferedImage(img_k8.getWidth(this), img_k8.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k8 = Kreuz_8.createGraphics();
		k8.drawImage(img_k8, 0, 0, this);

		Kreuz_9 = new BufferedImage(img_k9.getWidth(this), img_k9.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k9 = Kreuz_9.createGraphics();
		k9.drawImage(img_k9, 0, 0, this);

		Kreuz_10 = new BufferedImage(img_k10.getWidth(this), img_k10.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D k10 = Kreuz_10.createGraphics();
		k10.drawImage(img_k10, 0, 0, this);

		Kreuz_Bube = new BufferedImage(img_kB.getWidth(this), img_kB.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D kB = Kreuz_Bube.createGraphics();
		kB.drawImage(img_kB, 0, 0, this);

		Kreuz_Dame = new BufferedImage(img_kD.getWidth(this), img_kD.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D kD = Kreuz_Dame.createGraphics();
		kD.drawImage(img_kD, 0, 0, this);

		Kreuz_König = new BufferedImage(img_kK.getWidth(this), img_kK.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D kK = Kreuz_König.createGraphics();
		kK.drawImage(img_kK, 0, 0, this);

		Kreuz_Ass = new BufferedImage(img_kA.getWidth(this), img_kA.getHeight(this),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D kA = Kreuz_Ass.createGraphics();
		kA.drawImage(img_kA, 0, 0, this);

		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}	
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		//Spieler malen
		g.setFont(new Font("Arial", Font.PLAIN, 20)); 

	int x_absatz=300;
	//Community
		g.setColor((Color.GREEN).darker().darker().darker().darker());
		g.fillRect(x_absatz+80, 290, 350, 200);
		g.setColor(Color.WHITE);
		g.drawString("Communitykarten:", x_absatz+100, 290+30);
		g.drawString("Einsatz: "+Einsatz[0], x_absatz+100, 290+200-30);
		
	//Spieler 1
			g.setColor((Color.GREEN).darker().darker().darker().darker());
			g.fillRect(x_absatz+80, 40, 350, 200);
			if(mark_player==1) {
				g.setColor(Color.red);
				g.drawRect(x_absatz+80, 40, 350, 200);
			}
			g.setColor(Color.WHITE);
			g.drawString("Spieler 1: "+HandSpieler[0], x_absatz+100, 70);
			g.drawString("Budget: "+Budget[0], x_absatz+100, 230);
			g.drawString("Einsatz: "+Einsatz[0], x_absatz+100+200, 230);

	
	//Spieler 2
			g.setColor((Color.GREEN).darker().darker().darker().darker());
			g.fillRect(x_absatz+80, 540, 350, 200);
			if(mark_player==2) {
				g.setColor(Color.red);
				g.drawRect(x_absatz+80, 540, 350, 200);
			}
			g.setColor(Color.WHITE);
			g.drawString("Spieler 2: "+HandSpieler[1], x_absatz+100, 570);
			g.drawString("Budget: "+Budget[0], x_absatz+100, 720);
			g.drawString("Einsatz: "+Einsatz[1], x_absatz+100+200, 720);
			
	//Spieler 3
			g.setColor((Color.GREEN).darker().darker().darker().darker());
			g.fillRect(0, 290, 350, 200);
	if(anz_sp>=3) {
		if(mark_player==2) {
			g.setColor(Color.red);
			g.drawRect(0, 290, 350, 200);
		}
		g.setColor(Color.WHITE);
		g.drawString("Spieler 3: "+HandSpieler[2], 20, 290+30);
		g.drawString("Einsatz: "+Einsatz[1], 20, 290+200-30);		
	}
	
	//Spieler 4
			g.setColor((Color.GREEN).darker().darker().darker().darker());
			g.fillRect((int) (2.5*x_absatz)+15, 290, 350, 200);
	if(anz_sp>=4) {
		if(mark_player==2) {
			g.setColor(Color.red);
			g.drawRect((int) (2.5*x_absatz)+15, 290, 350, 200);
		}
		g.setColor(Color.WHITE);
		g.drawString("Spieler 4: "+HandSpieler[2], 20, 290+30);
		g.drawString("Einsatz: "+Einsatz[1], 20, 290+200-30);		
	}
		
			
		
			
		String dar = "";
		
		for(int s=0; s<=anz_sp; s++) {
			int max = 0;
			if(s==0) max = darstellungC.size();
			if(s==1) max = darstellungS1.size();
			if(s==2) max = darstellungS2.size();
			if(s==3) max = darstellungS3.size();
			if(s==4) max = darstellungS4.size();
			
			for(int k=0; k<max;k++) {
				if(k==1 || k==0 || k==2) x_absatz = 300;
				if(s==0) dar = darstellungC.get(k);
				if(s==1) dar = darstellungS1.get(k);
				if(s==2) dar = darstellungS2.get(k);
				if(s==3) dar = darstellungS3.get(k);
				if(s==4) dar = darstellungS4.get(k);
								
				int x=(k*(56+20))+100; int y=0;
				if(s==0) {
					y=330;
				}
				if(s==1)y=100;
				if(s==2)y=600;
				
				if(dar.equals("p2")) {g2D.drawImage(Piek_2, x+x_absatz,y, this); continue;}
				if(dar.equals("p3")) {g2D.drawImage(Piek_3, x+x_absatz,y, this); continue;}
				if(dar.equals("p4")) {g2D.drawImage(Piek_4, x+x_absatz,y, this); continue;}
				if(dar.equals("p5")) {g2D.drawImage(Piek_5, x+x_absatz,y, this); continue;}
				if(dar.equals("p6")) {g2D.drawImage(Piek_6, x+x_absatz,y, this); continue;}
				if(dar.equals("p7")) {g2D.drawImage(Piek_7, x+x_absatz,y, this); continue;}
				if(dar.equals("p8")) {g2D.drawImage(Piek_8, x+x_absatz,y, this); continue;}
				if(dar.equals("p9")) {g2D.drawImage(Piek_9, x+x_absatz,y, this); continue;}
				if(dar.equals("p10")) {g2D.drawImage(Piek_10, x+x_absatz,y, this); continue;}
				if(dar.equals("pB")) {g2D.drawImage(Piek_Bube, x+x_absatz,y, this); continue;}
				if(dar.equals("pD")) {g2D.drawImage(Piek_Dame, x+x_absatz,y, this); continue;}
				if(dar.equals("pK")) {g2D.drawImage(Piek_König, x+x_absatz,y, this); continue;}
				if(dar.equals("pA")) {g2D.drawImage(Piek_Ass, x+x_absatz,y, this); continue;}
				
				if(dar.equals("c2")) {g2D.drawImage(Caro_2, x+x_absatz,y, this); continue;}
				if(dar.equals("c3")) {g2D.drawImage(Caro_3, x+x_absatz,y, this); continue;}
				if(dar.equals("c4")) {g2D.drawImage(Caro_4, x+x_absatz,y, this); continue;}
				if(dar.equals("c5")) {g2D.drawImage(Caro_5, x+x_absatz,y, this); continue;}
				if(dar.equals("c6")) {g2D.drawImage(Caro_6, x+x_absatz,y, this); continue;}
				if(dar.equals("c7")) {g2D.drawImage(Caro_7, x+x_absatz,y, this); continue;}
				if(dar.equals("c8")) {g2D.drawImage(Caro_8, x+x_absatz,y, this); continue;}
				if(dar.equals("c9")) {g2D.drawImage(Caro_9, x+x_absatz,y, this); continue;}
				if(dar.equals("c10")) {g2D.drawImage(Caro_10, x+x_absatz,y, this); continue;}
				if(dar.equals("cB")) {g2D.drawImage(Caro_Bube, x+x_absatz,y, this); continue;}
				if(dar.equals("cD")) {g2D.drawImage(Caro_Dame, x+x_absatz,y, this); continue;}
				if(dar.equals("cK")) {g2D.drawImage(Caro_König, x+x_absatz,y, this); continue;}
				if(dar.equals("cA")) {g2D.drawImage(Caro_Ass, x+x_absatz,y, this); continue;}

				if(dar.equals("h2")) {g2D.drawImage(Herz_2, x+x_absatz,y, this); continue;}
				if(dar.equals("h3")) {g2D.drawImage(Herz_3, x+x_absatz,y, this); continue;}
				if(dar.equals("h4")) {g2D.drawImage(Herz_4, x+x_absatz,y, this); continue;}
				if(dar.equals("h5")) {g2D.drawImage(Herz_5, x+x_absatz,y, this); continue;}
				if(dar.equals("h6")) {g2D.drawImage(Herz_6, x+x_absatz,y, this); continue;}
				if(dar.equals("h7")) {g2D.drawImage(Herz_7, x+x_absatz,y, this); continue;}
				if(dar.equals("h8")) {g2D.drawImage(Herz_8, x+x_absatz,y, this); continue;}
				if(dar.equals("h9")) {g2D.drawImage(Herz_9, x+x_absatz,y, this); continue;}
				if(dar.equals("h10")) {g2D.drawImage(Herz_10, x+x_absatz,y, this); continue;}
				if(dar.equals("hB")) {g2D.drawImage(Herz_Bube, x+x_absatz,y, this); continue;}
				if(dar.equals("hD")) {g2D.drawImage(Herz_Dame, x+x_absatz,y, this); continue;}
				if(dar.equals("hK")) {g2D.drawImage(Herz_König, x+x_absatz,y, this); continue;}
				if(dar.equals("hA")) {g2D.drawImage(Herz_Ass, x+x_absatz,y, this); continue;}
				
				if(dar.equals("k2")) {g2D.drawImage(Kreuz_2, x+x_absatz,y, this); continue;}
				if(dar.equals("k3")) {g2D.drawImage(Kreuz_3, x+x_absatz,y, this); continue;}
				if(dar.equals("k4")) {g2D.drawImage(Kreuz_4, x+x_absatz,y, this); continue;}
				if(dar.equals("k5")) {g2D.drawImage(Kreuz_5, x+x_absatz,y, this); continue;}
				if(dar.equals("k6")) {g2D.drawImage(Kreuz_6, x+x_absatz,y, this); continue;}
				if(dar.equals("k7")) {g2D.drawImage(Kreuz_7, x+x_absatz,y, this); continue;}
				if(dar.equals("k8")) {g2D.drawImage(Kreuz_8, x+x_absatz,y, this); continue;}
				if(dar.equals("k9")) {g2D.drawImage(Kreuz_9, x+x_absatz,y, this); continue;}
				if(dar.equals("k10")) {g2D.drawImage(Kreuz_10, x+x_absatz,y, this); continue;}
				if(dar.equals("kB")) {g2D.drawImage(Kreuz_Bube, x+x_absatz,y, this); continue;}
				if(dar.equals("kD")) {g2D.drawImage(Kreuz_Dame, x+x_absatz,y, this); continue;}
				if(dar.equals("kK")) {g2D.drawImage(Kreuz_König, x+x_absatz,y, this); continue;}
				if(dar.equals("kA")) {g2D.drawImage(Kreuz_Ass, x+x_absatz,y, this); continue;}
				++k;
			}
			
			
		}
		
		
				

				
	}
}