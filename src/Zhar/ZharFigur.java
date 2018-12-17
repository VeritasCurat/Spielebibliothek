package Zhar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ZharFigur {
	int anfang_x; int anfang_y; //base point: obere linke Ecke
	int ende_x; int ende_y; //end point: untere rechte ecke
	String typ;
	
	boolean form[][]; //overlay: true, dieser Block gehört zur Figur, false sonst
	static int breite; static int hoehe; 
	Color farbe; static Color farbenkasten[] = {Color.BLUE, Color.GREEN, Color.CYAN, Color.ORANGE, Color.RED, Color.YELLOW};
	boolean ausgewählt = false;
	
	//zeitamanagement
	long zeitschritt=0; 
	int hp=0;
	
	int spieler;
	public static int preis=0;
	/*
	 * f00 f01 f02 f03
	 * ...
	 * f30 f31 f32 f33
	 */
	
	/**
	 * setzt statische Parameter für alle ZharFiguren
	 * @param breite
	 * @param hoehe
	 */
	public static void init(int Breite, int Hoehe) {
		breite = Breite; hoehe = Hoehe;
	}


	public boolean auswahl(int x, int y) {
		if(form[x][y])ausgewählt=!ausgewählt;
		return false;
	}
	
	public boolean belegt(int x, int y) {
		return form[x][y];
	}
	
	public ZharFigur(String typ, int anfang_x, int anfang_y, int Spieler) {
		this.typ = typ;
		this.anfang_x = anfang_x; 
		this.anfang_y = anfang_y;
		form = new boolean[breite][hoehe];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				form[b][h] = false;
			}
		}
		this.spieler = Spieler;
	}
	
	public boolean valid() {//TODO: modellieren
		return true;
	}


	public void weitermachen() {
		++zeitschritt;
	}
	
	public void hp_abziehen(int punkte) {
		hp-=punkte;
	}
}

class Fahrzeug extends ZharFigur {
	public Fahrzeug(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		ende_x = anfang_x; ende_y = anfang_y;
	}


	List<Integer[]> bewegen = new ArrayList<Integer[]>(); int bewegungs_schritt=0;
	int geschwindigkeit = 5; //pro sekunde
	
	public void neueBewegung(List<Integer[]> bewegung) {
		bewegen = bewegung;
	}
	
	public void weiterbewegen() {
		if(bewegungs_schritt == bewegen.size()) {//fertig
			bewegungs_schritt = 0;
			bewegen =  new ArrayList<Integer[]>();  
			return;
		}
		
		form[anfang_x][anfang_y] = false;
		anfang_x = bewegen.get(bewegungs_schritt)[0]; anfang_y = bewegen.get(bewegungs_schritt)[1]; 
		form[anfang_x][anfang_y] = true;
	
		++bewegungs_schritt;
		ZharController.repaint();

	}
	
	public void weitermachen() {
		++zeitschritt;
		if(zeitschritt%geschwindigkeit==0 && bewegen.size()>0) {
			System.out.println(bewegen.size());
			weiterbewegen();
		}
	}


}

class Jäger extends Fahrzeug{
	
	
	
	public Jäger(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		geschwindigkeit = 7;
		ZharFigur.preis = 5000;
		farbe = Color.BLACK;
		hp = 100;
		form[anfang_x][anfang_y] = true;
	}
	
	public void weitersuchen() {
		//hier
		for(ZharFigur z: ZharSpielfeld.zharfiguren) {
			if(z.spieler != spieler) {
				
				List<Integer[]> bewegungsplan = new ArrayList<Integer[]>();
				if(z.anfang_x == anfang_x && z.anfang_y == anfang_y) {
					//gefangen
					weiterzerstören(z);
				}

				if(Math.abs(z.anfang_x - anfang_x) + Math.abs(z.anfang_y - anfang_y) <= 4) {
					System.out.println("Gegner in Reichweite: "+z);

					int difX = z.anfang_x - anfang_x;
					int difY = z.anfang_y - anfang_y;
									
					int i=0;
					while(anfang_y+i != z.anfang_y) {
						i+=Math.signum(difY);
						Integer[] b = {anfang_x,anfang_y+i};
						bewegungsplan.add(b);
					}
					
					int j=0;
					while(anfang_x+j != z.anfang_x) {
						j+=Math.signum(difX);
						Integer[] b = {anfang_x+j,z.anfang_y};
						bewegungsplan.add(b);
					}
					
					neueBewegung(bewegungsplan);
				}	
			}
			
		}
		
		
			
			
	}
	
	/**
	 * Sammelroboter sitzt auf Taramit
	 * @throws InterruptedException 
	 */
	public void weiterzerstören(ZharFigur z){//TODO:nicht durch weitersuchen aufrufen
		
		if(z.spieler != spieler || !(z instanceof Fahrzeug))return;
		
		ZharController.repaint();
		z.farbe.brighter().brighter();//TODO
		z.hp_abziehen(20);
	}
	
	public void weitermachen() {
		++zeitschritt;
		if(zeitschritt%geschwindigkeit==0) {
			if(bewegen.size()>0)weiterbewegen();
			else weitersuchen();
		}
	}
	
}

class Sammler extends Fahrzeug{
	int sammelgeschwindigkeit=10;
	int sammelwert = 500;
	
	int bewegungsradius=2; //kann von 2 Ressourcen sehen
	
	public Sammler(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		System.out.println("spawne an "+anfang_x+" "+anfang_y);	
		geschwindigkeit = 5;
		ZharFigur.preis = 2500;
		farbe = Color.BLUE;
		hp = 60;
		form[anfang_x][anfang_y] = true;
	}
	
	
	public void weitersuchen() {
		//hier
		if(ZharLevel.fields[anfang_x][anfang_y] instanceof Taramit) {
			if(((Taramit) ZharLevel.fields[anfang_x][anfang_y]).units > 0){						
			}
		}
		
		boolean gefunden=false;
		List<Integer[]> bewegungsplan = new ArrayList<Integer[]>();
		
		u: for(int breite=0; breite<ZharLevel.breite; breite++) {
			for(int hoehe=0; hoehe<ZharLevel.hoehe; hoehe++) {
				if(ZharLevel.fields[breite][hoehe] instanceof Taramit && ((Taramit) ZharLevel.fields[breite][hoehe]).units > 0) {
					for(int abstand=1; abstand<=3; abstand++) {
						if(Math.abs(breite- anfang_x) + Math.abs(hoehe - anfang_y) <= abstand) {
							

							int difX = breite - anfang_x;
							int difY = hoehe - anfang_y;
											
							int i=0; 
							while(anfang_y+i != hoehe) {
								i+=Math.signum(difY);
								Integer[] b = {anfang_x,anfang_y+i};
								bewegungsplan.add(b);
							}
							
							int j=0;
							while(anfang_x+j != breite) {
								j+=Math.signum(difX);
								Integer[] b = {anfang_x+j,hoehe};
								bewegungsplan.add(b);
							}
							
							neueBewegung(bewegungsplan);
							break u;
						}
					}
				}
				
			}
		}
		
		
		/*
		//Bewegung von 1
			Integer[] a = {anfang_x,anfang_y};
			Integer[] b = {0,0};

			//rechts
			if(ZharLevel.fields[anfang_x+1][anfang_y] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x+1][anfang_y]).units > 0){					
					b[0]=anfang_x+1; b[1]=anfang_y; gefunden=true;
				}
			}
			//links
			else if(ZharLevel.fields[anfang_x-1][anfang_y] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x-1][anfang_y]).units > 0){	
					b[0]=anfang_x-1; b[1]=anfang_y;	gefunden=true;		
				}
			}
			//oben
			else if(ZharLevel.fields[anfang_x][anfang_y+1] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x][anfang_y+1]).units > 0){						
					b[0]=anfang_x; b[1]=anfang_y+1;gefunden=true;	
				}
			}
			//unten
			else if(ZharLevel.fields[anfang_x][anfang_y-1] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x][anfang_y+1]).units > 0){						
					b[0]=anfang_x; b[1]=anfang_y+1;gefunden=true;	
				}
			}
		//Bewegung von 2
			//oben rechts
			if(ZharLevel.fields[anfang_x+1][anfang_y-1] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x+1][anfang_y-1]).units > 0){					
					a[0]=anfang_x+1; a[1]=anfang_y; 
					b[0]=anfang_x+1; b[1]=anfang_y-1; 
					gefunden=true;
				}
			}
			//oben links
			else if(ZharLevel.fields[anfang_x-1][anfang_y-1] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x-1][anfang_y-1]).units > 0){	
					a[0]=anfang_x-1; a[1]=anfang_y; 
					b[0]=anfang_x-1; b[1]=anfang_y-1; 
					gefunden=true;		
				}
			}
			//unten rechts
			else if(ZharLevel.fields[anfang_x+1][anfang_y+1] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x+1][anfang_y+1]).units > 0){						
					a[0]=anfang_x+1; a[1]=anfang_y; 
					b[0]=anfang_x+1; b[1]=anfang_y+1; 
					gefunden=true;	
				}
			}
			//unten links
			else if(ZharLevel.fields[anfang_x-1][anfang_y+1] instanceof Taramit) {
				if(((Taramit) ZharLevel.fields[anfang_x-1][anfang_y+1]).units > 0){						
					a[0]=anfang_x-1; a[1]=anfang_y; 
					b[0]=anfang_x-1; b[1]=anfang_y+1; 
					gefunden=true;		
				}
			}
			if(gefunden==true) {
				bewegungsplan.add(a);
				bewegungsplan.add(b);

				neueBewegung(bewegungsplan);
			}
			*/
			
	}
	
	/**
	 * Sammelroboter sitzt auf Taramit
	 * @throws InterruptedException 
	 */
	public void weitersammeln(){
		if(!(ZharLevel.fields[anfang_x][anfang_y] instanceof Taramit))return;
		
		ZharController.repaint();
		ZharLevel.fields[anfang_x][anfang_y].farbe.brighter().brighter();
				
		
		((Taramit)ZharLevel.fields[anfang_x][anfang_y]).einsammeln(500);
		for(int i=0; i<ZharController.spielerliste.size(); i++) {
			if(ZharController.spielerliste.get(i).name.equals(spieler)) {
				ZharController.spielerliste.get(i).ressourcen+=500;
			}
		}	
	}
	
	public void weitermachen() {
		++zeitschritt;
		if(zeitschritt%geschwindigkeit==0) {
			if(bewegen.size()>0)weiterbewegen();
			else weitersuchen();
		}
		if(zeitschritt%sammelgeschwindigkeit==0) {
			weitersammeln();
		}
	}
	
}

class Bauer extends Fahrzeug{
	/*
	 * X
	 */ 
	int stufe = 1;
	int baumenüX, baumenüY;
	
	
	public Bauer(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		farbe = Color.CYAN;
		form[anfang_x][anfang_y] = true;
		baumenüX = baumenüY = -1;
		geschwindigkeit = 10;
		hp = 200;
		ZharFigur.preis = 500; preis = 500;
	}
}

class Fabrik extends ZharFigur{
	/*
	 * XX
	 * XX
	 */
	Integer[] spawn;
	int zeitzähler=0;
	int BauDauer = 5; //Anzahl der nötigen BauFortschritteTODO: vergrößern
	int BauInterval=5; //Alle 5 Aufrufe wird 1 Fortschritt gemacht
	int BauFortschritt = 0;
	boolean gebaut=false;
	boolean produziert = false;
	int ProduzierDauer = 3; //Anzahl der nötigen BauFortschritteTODO: vergrößern
	int ProduzierInterval= 3; //Alle 5 Aufrufe wird 1 Fortschritt gemacht
	int ProduzierFortschritt = 0;
	
	Color farbe;
	String Produktionstyp;
	
	public Fabrik(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		form[anfang_x][anfang_y] = true;
		form[anfang_x+1][anfang_y] = true;
		form[anfang_x][anfang_y+1] = true;
		form[anfang_x+1][anfang_y+1] = true;
	}
	
	public static Integer[] sucheSpawnPlatz(int x, int y) {
		//suche schneckartig um (x,y) nach freier Stelle
		/*  //Beispiel: Start:1 ; richtung: rechts, oben, links, unten, rechts ...; alle drei Schritte wird lastn erhöht ...
		 *  4437
	   	 *	5127
       	 *  5667	
		 */
		int schrittn=1; 
		int schritt=0;
		
		int richtungX = 1;
		int richtungY = 0;
		while(true) { 
			x += richtungX;
			y += richtungY;
			boolean belegt = false;
			if(x<0 || y<0 || x>=breite-1 || y>=breite-1)belegt=true;
			if(!belegt && !(ZharLevel.fields[x][y] instanceof Stein))belegt = true;
			if(!belegt && !(ZharLevel.fields[x][y] instanceof Taramit))belegt = true;
			if(!belegt) {				
				for(ZharFigur z: ZharSpielfeld.zharfiguren) {
					if(z.anfang_x==x && z.anfang_y==y)belegt=true;
					if(z instanceof JägerFabrik || z instanceof SammlerFabrik) {
						if(z.anfang_x+1==x && z.anfang_y==y)belegt=true;
						if(z.anfang_x==x && z.anfang_y+1==y)belegt=true;
						if(z.anfang_x+1==x && z.anfang_y+1==y)belegt=true;
					}
				}
			}
			if(!belegt) {
				Integer[] a = {x,y};
				System.out.println(x+" "+y);
				return a;
			}
			
			
			++schritt;
			if(schritt == schrittn) {
				++schrittn;
				schritt=0;
				//richtungswechsel
				if(richtungX==1 && richtungY==0) {
					richtungX=0; richtungY=-1;
				}
				else if(richtungX==0 && richtungY==-1) {
					richtungX=-1; richtungY=0;
				}
				else if(richtungX==-1 && richtungY==0) {
					richtungX=0; richtungY=1;
				}
				else if(richtungX==0 && richtungY==1) {
					richtungX=1; richtungY=0;
				}
			}
		}
	}

	public void weiterproduzieren() {
		++ProduzierFortschritt;
		if(BauFortschritt == BauDauer) {
			ZharSpielfeld.spawnFigur(Produktionstyp, spieler, 0, 0);
			gebaut = true;
		}
	}
	
	public void weiterbauen() {
		farbe.darker().darker();
		ZharController.repaint();
		++BauFortschritt;
		if(BauFortschritt == BauDauer) {
			gebaut = true;
		}
	}
	
	public void weitermachen() {
		if(!gebaut && zeitschritt%BauInterval==0 ) {
			weiterbauen();
		}
		else {
			if(produziert && zeitschritt%ProduzierInterval==0) {
				weiterproduzieren();
			}
		}
	}
}

class JägerFabrik extends Fabrik{

	public JägerFabrik(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		farbe = Color.RED;
		farbe.brighter().brighter().brighter().brighter().brighter().brighter();
		ZharFigur.preis = 8000;
		Produktionstyp = "Jäger";
	}
	
}

class SammlerFabrik extends Fabrik{

	public SammlerFabrik(String typ, int anfang_x, int anfang_y, int Spieler) {
		super(typ, anfang_x, anfang_y, Spieler);
		// TODO Auto-generated constructor stub
		farbe = Color.GREEN;
		farbe.brighter().brighter().brighter().brighter().brighter().brighter();
		ZharFigur.preis = 4000;
		Produktionstyp = "Sammler";
	}
	

	
}

