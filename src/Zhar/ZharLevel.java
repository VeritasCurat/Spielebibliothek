package Zhar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ZharLevel {
	/**
	 * Alle Objekte die spezifisch für das Level sind
	 */
	
	
	static int hoehe; static int breite;
	static ZharField[][] fields;
	


	public static void init(int level) {
		if(level==1) {
			breite = 30; hoehe = 18; 
			fields = new ZharField[30][18];

			for(int b=0; b<breite; b++) {
				for(int h=0; h<hoehe; h++) {
					fields[b][h] = new ZharField();					
				}
			}
			
			for(int[] l: Landefläche.landefläche_klein) {
				fields[l[0]][l[1]] = new Landefläche(ZharController.spielerliste.get(0).name);		
			}
			
			for(int[] l: Landefläche.landefläche_klein) {
				fields[27+l[0]][15+l[1]] = new Landefläche(ZharController.spielerliste.get(1).name);		
			}
			
			for(int[] r: Stein.rock1) {
				fields[4+r[0]][3+r[1]] = new Stein();		
			}
			
			for(int[] r: Taramit.tar_dia) {
				fields[12+r[0]][6+r[1]] = new Taramit();		
			}
			
			
		}
	}
	

	
	public static boolean auswahlPrüfen(int x, int y) {
		//Prüfe auf Kollision mit Steinen
		for(int b=0; b<breite; b++) {
			for(int h=0; h<hoehe;h++) {
				if(fields[b][h] instanceof Stein) {
					if(x-1== b && y+1 == h) { //TODO: eigentlich Schwachsinn
						ShowCanvas.setzeNachricht("Kollision mit Stein an: "+b+" "+h);
						return false;
					}
				}
				else continue;
			}
		}
		return true;
	}
}
