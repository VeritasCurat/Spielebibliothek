package m�hle;

import java.util.ArrayList;
import java.util.List;

import tetris.Figur;

public class M�hleSpielfeld {
		
	boolean automatic = false;
	boolean spiel_aktiv=true;
	M�hleFigur[][][] figuren = new M�hleFigur[3][3][3];
	
	List<Integer[][]> m�hlen_schwarz = new ArrayList<Integer[][]>();	
	List<Integer[][]> m�hlen_wei� = new ArrayList<Integer[][]>();

	int wegnehmer_wei�=0;
	int wegnehmer_schwarz=0;

	
	 int anz_wei�=0;
	 int anz_schwarz=0;
		
	/* (XY) Koordinaten innerhalb von Ring
	 * 00 - 01 - 02
	 * |		 |
	 * 10		 12
	 * |		 |
	 * 20 - 21 - 20
	 * Dritte Koordinate Ringnummer, von au�en nach innen: 0,1,2
	 */
	
	private boolean contains(List<Integer[][]> list, Integer[][] a) {
		f: for(int i=0; i<list.size(); i++) {
			Integer[][] b = list.get(i);
			for(int p=0; p<b.length; p++) {
				for(int q=0; q<b[0].length; q++) {
					if(b[p][q] != a[p][q])continue f;
				}
			}
			return true;
		}
		return false;
	}
	
	public void init() {
		for(int x=0; x<3; x++) {
			for(int y=0; y<3; y++) {
				for(int z=0; z<3; z++) {
					figuren[x][y][z] = new M�hleFigur(x, y, z, "NULL");			
				}
			}
		}
	}
	
	public boolean setFigure(int x1, int y1, int ring, String farbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 2 || y1 > 2) {
			System.out.println("Das ist garkein Feld");
			return false;
		}	
		//das ist nicht ihre Figur
		if(!(figuren[x1][y1][ring].farbe.equals("NULL") || figuren[x1][y1][ring].farbe.equals(""))) {
			System.out.println("Hier steht schon eine Figur von: "+figuren[x1][y1][ring].farbe);
			return false;
		}
		figuren[x1][y1][ring] = new L�ufer(x1, y1, ring, farbe);
		if(farbe.equals("wei�"))++anz_wei�;
		else if(farbe.equals("schwarz"))++anz_schwarz;
		return true;
	}
	
	public boolean deleteFigure(int x1, int y1, int ring, String gegnerfarbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 2 || y1 > 2) {
			System.out.println("Das ist garkein Feld");
			return false;
		}	
		//das ist nicht ihre Figur
		if(!figuren[x1][y1][ring].farbe.equals(gegnerfarbe)) {
			System.out.println("Hier steht keine Gegnerfigur: "+figuren[x1][y1][ring].farbe);
			return false;
		}
		figuren[x1][y1][ring].typ="NULL"; figuren[x1][y1][ring].farbe="";
		return true;
	}
	
	public boolean bewegungPr�fen(int x1,int y1, int z1, int x2, int y2, int z2, String farbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 7 || y1 > 7) {
			if(!automatic)System.out.println("Das ist garkein Feld");
			return false;
		}	
		//auf dem Feld steht keine Figur
		if(figuren[x1][y1][z1].typ.equals("")) {
			if(!automatic)System.out.println("auf dem Feld steht keine Figur");
			return false;
		}
		//das ist nicht ihre Figur
		if(!figuren[x1][y1][z1].farbe.equals(farbe)) {
			if(!automatic)System.out.println("das ist nicht ihre Figur: "+x1+", "+y1);
			return false;
		}
		//Hier steht schon eine Figur
		if(!(figuren[x1][y1][z1].farbe.equals("") || !figuren[x1][y1][z1].farbe.equals("NULL"))) {
			if(!automatic)System.out.println("Hier steht schon eine Figure: "+figuren[x2][y2][z2].typ);
			return false;
		}
		if(figuren[x1][y1][z1] instanceof L�ufer)return ((L�ufer)figuren[x1][y1][z1]).move(x2, y2,z2);
		if(figuren[x1][y1][z1] instanceof Springer)return ((Springer)figuren[x1][y1][z1]).move(x2, y2,z2);
		System.out.println("FEHLER!!");
		return false;			
	}
	
	//wechselt f�r den Spieler der farbe f: L�ufer => Springer
	public void tausch(String f) {
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				for(int z=0; z<3; z++) {
					if(figuren[x][y][z].farbe.equals(f))figuren[x][y][z] = new L�ufer(x, y, z, f);			
				}
			}
		}
	}
	
	public boolean bewegungAusf�hren(int x1,int y1, int z1, int x2, int y2, int z2, String farbe){
		if(bewegungPr�fen(x1,y1,z1,x2,y2,z2,farbe)){
			//Spielende
			if(anz_wei� == 0||anz_schwarz==0)spiel_aktiv = false;
			//Zug G�ltig
			//int z2 = z + (Math.abs(x1+y1) % 2)*(1-(x1+y1+dx+dy)); 

			switch(figuren[x1][y1][z1].typ) {
				case "L�ufer": { figuren[x2][y2][z2] = new L�ufer(x2, y2, z2, farbe); break;}
				case "Springer": {figuren[x2][y2][z2] = new Springer(x2, y2, z2, farbe); break;}
			}
			
			figuren[x1][y1][z1] = new M�hleFigur(x1, y1, z1, "NULL");
			return true;
		}
		return false;
	}
	
	/**
	 * Gibt die Anzahl der M�hlen f�r Spieler mit der Farbe zur�ck.
	 * @param farbe
	 * @return
	 */
	public void m�hle_erkennen(String farbe) {
		String a = "";
		String b = "";
		String c = "";
		
		System.out.println("M�hle erkennen");
		
		//interring m�hlen
		for(int ring=0; ring<3; ring++) { //TODO: k�rzer schreiben
			a = figuren[0][0][ring].farbe;
			b = figuren[0][1][ring].farbe;
			c = figuren[0][2][ring].farbe;
			if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
				Integer[][] m�hle = {{0,0,ring},{0,1,ring},{0,2,ring}};
				if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
					++wegnehmer_wei�;
					m�hlen_wei�.add(m�hle);
				}
				if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
					++wegnehmer_schwarz;
					m�hlen_schwarz.add(m�hle);
				}
			};
			
			a = figuren[2][0][ring].farbe;
			b = figuren[2][1][ring].farbe;
			c = figuren[2][2][ring].farbe;
			if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
				Integer[][] m�hle = {{2,0,ring},{2,1,ring},{2,2,ring}};
				if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
					++wegnehmer_wei�;
					m�hlen_wei�.add(m�hle);
				}
				if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
					++wegnehmer_schwarz;
					m�hlen_schwarz.add(m�hle);
				}
			};
			
			a = figuren[0][0][ring].farbe;
			b = figuren[1][0][ring].farbe;
			c = figuren[2][0][ring].farbe;
			if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
				Integer[][] m�hle = {{0,0,ring},{1,0,ring},{2,0,ring}};
				if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
					++wegnehmer_wei�;
					m�hlen_wei�.add(m�hle);
				}
				if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
					++wegnehmer_schwarz;
					m�hlen_schwarz.add(m�hle);
				}
			};
			
			a = figuren[0][2][ring].farbe;
			b = figuren[1][2][ring].farbe;
			c = figuren[2][2][ring].farbe;
			if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
				Integer[][] m�hle = {{0,2,ring},{1,2,ring},{2,2,ring}};
				if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
					++wegnehmer_wei�;
					m�hlen_wei�.add(m�hle);
				}
				if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
					++wegnehmer_schwarz;
					m�hlen_schwarz.add(m�hle);
				}
			}
		}
		//extraring m�hlen
		a = figuren[0][1][0].farbe;
		b = figuren[0][1][1].farbe;
		c = figuren[0][1][2].farbe;
		if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
			Integer[][] m�hle = {{0,1,0},{0,1,1},{0,1,2}};
			if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
				++wegnehmer_wei�;
				m�hlen_wei�.add(m�hle);
			}
			if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
				++wegnehmer_schwarz;
				m�hlen_schwarz.add(m�hle);
			}
		};
		
		a = figuren[2][1][0].farbe;
		b = figuren[2][1][1].farbe;
		c = figuren[2][1][2].farbe;
		if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
			Integer[][] m�hle = {{2,1,0},{2,1,1},{2,1,2}};
			if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
				++wegnehmer_wei�;
				m�hlen_wei�.add(m�hle);
			}
			if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
				++wegnehmer_schwarz;
				m�hlen_schwarz.add(m�hle);
			}
		};
		
		a = figuren[1][0][0].farbe;
		b = figuren[1][0][1].farbe;
		c = figuren[1][0][2].farbe;
		if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
			Integer[][] m�hle = {{1,0,0},{1,0,1},{1,0,2}};
			if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
				++wegnehmer_wei�;
				m�hlen_wei�.add(m�hle);
			}
			if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
				++wegnehmer_schwarz;
				m�hlen_schwarz.add(m�hle);
			}
		};
		
		a = figuren[1][2][0].farbe;
		b = figuren[1][2][1].farbe;
		c = figuren[1][2][2].farbe;
		if(a.equals(b) && b.equals(c) && c.equals(a) && a.equals(farbe)) {
			Integer[][] m�hle = {{1,2,0},{1,2,1},{1,2,2}};
			if(farbe.equals("wei�") && !contains(m�hlen_wei�, m�hle)) {
				++wegnehmer_wei�;
				m�hlen_wei�.add(m�hle);
			}
			if(farbe.equals("schwarz") && !contains(m�hlen_schwarz, m�hle)) {
				++wegnehmer_schwarz;
				m�hlen_schwarz.add(m�hle);
			}
		};
		
	}
	
	/**
	 * 
	 * @param farbe
	 * @return ob spieler mit farbe einen Stein hat der nicht in M�hle ist
	 */
	public boolean beklaubar(String farbe) {
		for(int ring=0; ring<2; ring++) {
			for(int x=0; x<3; x++) {
				for(int y=0; y<3; y++) {
					if(stein_nehmbar(x, y, ring, farbe)){//ist der Stein in M�hle
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean stein_nehmen(int x, int y, int ring, String farbe) {
		//nehme (wenn m�glich) Stein vom Gegner		
		if(farbe.equals("wei�") && !stein_nehmbar(x, y, ring, "schwarz"))return false;
		if(farbe.equals("schwarz") && !stein_nehmbar(x, y, ring, "wei�"))return false;
		else {			
			if(farbe.equals("wei�"))--wegnehmer_wei�;
			else if(farbe.equals("schwarz"))--wegnehmer_schwarz;
			return true;
		}
	}
	
	/**
	 *	ist der Stein in M�hle?
	 * @param x
	 * @param y
	 * @param ring
	 * @param farbe
	 * @return
	 */
	private boolean stein_nehmbar(int x, int y, int ring, String farbe) {
		if(farbe.equals("schwarz")) {
			for(int mindex=0; mindex<m�hlen_schwarz.size(); mindex++) {
				l: for(int k=0; k<3; k++) {
					if(m�hlen_schwarz.get(mindex)[k][0] == x && m�hlen_schwarz.get(mindex)[k][1] == y && m�hlen_schwarz.get(mindex)[k][2] == ring)return false;
					continue l;
				}
			}
		}
		if(farbe.equals("wei�")) {			
			for(int mindex=0; mindex<m�hlen_wei�.size(); mindex++) {
				l: for(int k=0; k<3; k++) {
					if(m�hlen_wei�.get(mindex)[k][0] == x && m�hlen_wei�.get(mindex)[k][1] == y && m�hlen_wei�.get(mindex)[k][2] == ring)return false;
					continue l;
				}
			}
		}
		return true;
	}
	

	public void write_sf(M�hleSpielfeld s) {
		for(int i=0; i<s.figuren.length; i++) {
			for(int j=0; j<s.figuren[0].length; j++) {
				figuren[i][j] = s.figuren[i][j];
			}
		}
	}
	
}