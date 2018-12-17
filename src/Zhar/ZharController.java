package Zhar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ZharController  implements Runnable{
	
	
	static int anzahl_spieler = 1;
	static int schwierigkeit=2;
		static String schwierigkeitNamen[] = {"sehr einfach", "einfach", "mittel", "schwer", "sehr schwer"};

	static double zeit_sekunden = 0;
	static double zeit_milli = 0;
		static int score_unit = 3*(schwierigkeit+1);
	
	
	static boolean spiel_modus_KI= false;
	static boolean farbe_KI_schwarz = true;
	static int hoehe; static int breite; //6,12
	
	static ZharGUI G;	
	static List<ZharSpieler> spielerliste;
	static int currentSpieler = 0;
	static int KISpieler = 1;

	
	static double lasttick = System.currentTimeMillis();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stubsss
		
		neuesSpiel();
	}
	
	
	public static void neuesSpiel() throws InterruptedException {
		//init der Komponenten
		spielerliste = new ArrayList<ZharSpieler>(); 
		ZharSpieler a = new ZharSpieler("Spieler 1", 5000);
		ZharSpieler b = new ZharSpieler("Spieler 2", 5000);

		spielerliste.add(a); spielerliste.add(b);
		ZharLevel.init(1); 
		breite = ZharLevel.breite; hoehe = ZharLevel.hoehe;		
		currentSpieler = 0;
		System.out.println(ZharController.spielerliste.get(ZharController.currentSpieler).ressourcen);
		ZharSpielfeld.init(breite,hoehe);
		
		G = new ZharGUI(breite, hoehe);
		
		
			//TODO: currentSpieler nach 30 Sek. ändern
			//G.repaint();
		
		Thread t1 = new Thread(new ZharController());
		t1.start();
	}
	
	/**
	 * lässt bei Inputereignis (z.B: Mousedrag) GUI, die Funktion aufrufen und damit neuzeichnen
	 */
	public static void repaint() {
		G.repaint();
	}
	
	public static void updatePaint() {
		G.update(G.getGraphics());
	}

	@Override
	public void run() {
		//das alte tick(): 	TODO stößt in regelmäßgigen abständen alle zharfiguren an (zB um sie zu bewegen)
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(System.currentTimeMillis()-lasttick > 100) {
				lasttick = System.currentTimeMillis();
				zeit_milli += 100;
				if(zeit_milli%1000==0)zeit_sekunden++;
				
				List<Integer> löschen = new ArrayList<Integer>();
				for(int i=0; i<ZharSpielfeld.zharfiguren.size(); i++) {
					if(ZharSpielfeld.zharfiguren.get(i).hp < 0) {
						löschen.add(i);
					}
					if(ZharSpielfeld.zharfiguren.get(i) instanceof Fahrzeug) ((Fahrzeug) ZharSpielfeld.zharfiguren.get(i)).weitermachen();
				}
				for(Integer g: löschen) {
					ZharSpielfeld.zharfiguren.remove(g);
				}
					

			}	
			repaint(); 
		}
			
	}

}




		