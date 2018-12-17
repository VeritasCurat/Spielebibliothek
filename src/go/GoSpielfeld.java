package go;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GoSpielfeld {
		
	static boolean automatic = true;
	static boolean spiel_aktiv=true;
	static GoFigur[][] figuren;
	
	static int breite;
	
	static int anz_weiﬂ=0;	static int gefangene_weiﬂ=0; static int punkte_weiﬂ=0;
	static int anz_schwarz=0; static int gefangene_schwarz=0; static int punkte_schwarz=0;
	

		
	/* (XY) Koordinaten innerhalb von Ring
	 * 00 - 01 - 02
	 * |		 |
	 * 10		 12
	 * |		 |
	 * 20 - 21 - 20
	 * Dritte Koordinate Ringnummer, von auﬂen nach innen: 0,1,2
	 */
	
	
	public static void init(int breite) {
		figuren = new GoFigur[breite][breite];
		GoSpielfeld.breite = breite;
		
		for(int x=0; x<breite; x++) {
			for(int y=0; y<breite; y++) {
				figuren[x][y] = new GoFigur(x, y, "");			
			}
		}
	}
	
	
	public static boolean setFigure(int x1, int y1, String farbe) {

		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > breite || y1 > breite) {
			System.out.println("Das ist garkein Feld");
			return false;
		}	
		//Da steht schon Figur
		if(!figuren[x1][y1].farbe.equals("")) {
			System.out.println("Da steht schon eine Figur");
			return false;
		}
		figuren[x1][y1]= new GoFigur(x1, y1, farbe);
		status(x1,y1);
		kessel();
		//System.out.println("neuer Stein ["+farbe+"]:"+x1+" "+y1+", Freiheiten="+figuren[x1][y1].freiheiten+", Nachbarn="+figuren[x1][y1].nachbarn);
		if(farbe.equals("weiﬂ"))++anz_weiﬂ;
		if(farbe.equals("schwarz"))++anz_schwarz;
		return true;
	}
		
	/**
	 * @param x (Position)
	 * @param y (POsition)
	 * ermittelt die anzahl der nachbarn mit gleicher farbe und anzahl der freiheiten
	 */
	public static void status(int x, int y) {
		int freiheiten=0; int nachbarn =0;
		//links
		if(x>0) {
			if(figuren[x-1][y].farbe.equals(""))++freiheiten;
			else if(figuren[x-1][y].farbe.equals(figuren[x][y].farbe)) {
				++nachbarn;
			}
		}
	
		//rechts 
		if(y>0) {
			if(figuren[x][y-1].farbe.equals(""))++freiheiten;
			else if(figuren[x][y-1].farbe.equals(figuren[x][y].farbe)) {
				++nachbarn;
			}
		}
		
		//oben 
		if(x<breite-1) {
			if(figuren[x+1][y].farbe.equals(""))++freiheiten;
			else if(figuren[x+1][y].farbe.equals(figuren[x][y].farbe)) {
				++nachbarn;
			}
		}
		
		//unten
		if(y<breite-1) {
			if(figuren[x][y+1].farbe.equals(""))++freiheiten;
			else if(figuren[x][y+1].farbe.equals(figuren[x][y].farbe)) {
				++nachbarn;
			}
		}
		
		figuren[x][y].freiheiten = freiheiten;
		figuren[x][y].nachbarn = nachbarn;
	}
	
	
	
	/**
	 * pr¸ft f¸r Nachbarsteine von (x,y) ob sie noch Freiheiten haben
	 * @return
	 */
	public static void kessel() {
		ArrayList<GoFigur> alleWeiﬂ = new ArrayList<GoFigur>();
		ArrayList<GoFigur> alleSchwarz = new ArrayList<GoFigur>();

		//status f¸r alle Figuren ermitteln (TODO: effizienter nur f¸r (direkte und indirekte) Nachbarn von (X,Y)
		for(int x=0; x<breite; x++) {
			for(int y=0; y<breite; y++) {
				status(x,y);
				if(!figuren[x][y].farbe.equals("")) {
					if(figuren[x][y].nachbarn == 0 && figuren[x][y].freiheiten == 0) {
						figuren[x][y].farbe = "";
					}
					if(figuren[x][y].freiheiten == 0 && figuren[x][y].nachbarn > 0 ) {
						System.out.println("Stein["+x+","+y+"]: freiheiten="+figuren[x][y].freiheiten+" nachbarn="+figuren[x][y].nachbarn);						
					}
					if(figuren[x][y].farbe.equals("weiﬂ"))alleWeiﬂ.add(figuren[x][y]);
					if(figuren[x][y].farbe.equals("schwarz"))alleSchwarz.add(figuren[x][y]);
				}
			}
		}
		System.out.println();
		
		
			while(alleWeiﬂ.size() > 0) {
				ArrayList<GoFigur> zhk = new ArrayList<GoFigur>();
				//Zusammenhangkomponente finden
				int index=0;
				zhk.add(alleWeiﬂ.get(0)); alleWeiﬂ.remove(0);			
				while(index<zhk.size()) {
					System.out.println(index);
					GoFigur a = zhk.get(index);
					if(a.nachbarn > 0) {
						if(a.X > 0) {
							if(!zhk.contains(figuren[a.X-1][a.Y]) && alleWeiﬂ.contains(figuren[a.X-1][a.Y])) {
								zhk.add(figuren[a.X-1][a.Y]);
								alleWeiﬂ.remove(figuren[a.X-1][a.Y]);
							}
						}
						if(a.Y > 0) {
							if(!zhk.contains(figuren[a.X][a.Y-1]) && alleWeiﬂ.contains(figuren[a.X][a.Y-1])) {
								zhk.add(figuren[a.X][a.Y-1]);
								alleWeiﬂ.remove(figuren[a.X][a.Y-1]);
							}
						}
						if(a.X < breite-1) {
							if(!zhk.contains(figuren[a.X+1][a.Y]) && alleWeiﬂ.contains(figuren[a.X+1][a.Y])) {
								zhk.add(figuren[a.X+1][a.Y]);
								alleWeiﬂ.contains(figuren[a.X+1][a.Y]);
							}
						}
						if(a.Y < breite-1) {
							if(!zhk.contains(figuren[a.X][a.Y+1]) && alleWeiﬂ.contains(figuren[a.X][a.Y+1])) {
								zhk.add(figuren[a.X][a.Y+1]);
								alleWeiﬂ.contains(figuren[a.X][a.Y+1]);
							}
						}
						}
					++index;
					}
					System.out.println("zhk: "+zhk.size());
					//gehe durch Zusammenhangskomponente und such nach einem freien Stein!
					boolean frei = false;
					for(GoFigur g: zhk) {
						if(g.freiheiten > 0) {
							frei = true; break;
						}
					}
					if(!frei) { //lˆsche alle Steine die unfrei sind
						for(GoFigur g: zhk) {
							++gefangene_schwarz;
							figuren[g.X][g.Y].farbe = "";
							figuren[g.X][g.Y].freiheiten = 0;
							figuren[g.X][g.Y].nachbarn = 0;
						}
					}
				}
				
				while(alleSchwarz.size() > 0) {
					ArrayList<GoFigur> zhk = new ArrayList<GoFigur>();
					//Zusammenhangkomponente finden
					int index=0;
					zhk.add(alleSchwarz.get(0)); alleSchwarz.remove(0);			
					while(index<zhk.size()) {
						System.out.println(index);
						GoFigur a = zhk.get(index);
						if(a.nachbarn > 0) {
							if(a.X > 0) {
								if(!zhk.contains(figuren[a.X-1][a.Y]) && alleSchwarz.contains(figuren[a.X-1][a.Y])) {
									zhk.add(figuren[a.X-1][a.Y]);
									alleSchwarz.remove(figuren[a.X-1][a.Y]);
								}
							}
							if(a.Y > 0) {
								if(!zhk.contains(figuren[a.X][a.Y-1]) && alleSchwarz.contains(figuren[a.X][a.Y-1])) {
									zhk.add(figuren[a.X][a.Y-1]);
									alleSchwarz.remove(figuren[a.X][a.Y-1]);
								}
							}
							if(a.X < breite-1) {
								if(!zhk.contains(figuren[a.X+1][a.Y]) && alleSchwarz.contains(figuren[a.X+1][a.Y])) {
									zhk.add(figuren[a.X+1][a.Y]);
									alleSchwarz.contains(figuren[a.X+1][a.Y]);
								}
							}
							if(a.Y < breite-1) {
								if(!zhk.contains(figuren[a.X][a.Y+1]) && alleSchwarz.contains(figuren[a.X][a.Y+1])) {
									zhk.add(figuren[a.X][a.Y+1]);
									alleSchwarz.contains(figuren[a.X][a.Y+1]);
								}
							}
							}
						++index;
					}
					System.out.println("zhk: "+zhk.size());
					//gehe durch Zusammenhangskomponente und such nach einem freien Stein!
					boolean frei = false;
					for(GoFigur g: zhk) {
						if(g.freiheiten > 0) {
							frei = true; break;
						}
					}
					if(!frei) { //lˆsche alle Steine die unfrei sind
						for(GoFigur g: zhk) {
							++gefangene_weiﬂ;
							figuren[g.X][g.Y].farbe = "";
							figuren[g.X][g.Y].freiheiten = 0;
							figuren[g.X][g.Y].nachbarn = 0;
						}
					}
				}
			
		
		
			
		
		 
		 
	}
		
		
		
		
}
	
	
	
	
	
