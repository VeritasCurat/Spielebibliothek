package Zhar;

import java.util.ArrayList;
import java.util.List;

public class ZharKI {
	int breite = ZharController.breite; int hoehe = ZharController.hoehe;
	ZharField[][] level;
	static ArrayList<ZharFigur> zharschatten = new ArrayList<ZharFigur>();
	ZharSpieler KI;
	
	String schwierigkeit;
	double aggressivität = 0.1;
	int scaninterval = 0;
	int tick=0;
	
	List<Integer[]> sammler_koordinaten = new ArrayList<Integer[]>();
	List<Integer[]> panzer_koordinaten = new ArrayList<Integer[]>();

	
	public ZharKI(String schwierigkeit) {
		breite = ZharController.breite; hoehe = ZharController.hoehe;
		level = new ZharField[breite][hoehe];

		this.schwierigkeit = schwierigkeit;
		setSchwierigkeit();
	}
	
	private void setSchwierigkeit() {
		if(schwierigkeit.equals("einfach")) {
			scaninterval = 300;
		}
	}
	
	public void weitermachen() {
		if(tick%scaninterval==0) {
			scan();
		}
		++tick;
	}
	
	private void react() {
		react_krieger();
		react_sammler();
		act_economy();
	}
	
	private void react_krieger() {
		
	}
	
	private void react_sammler() {
		
	}
	
	private void act_economy() {
		//sammler erzeugen
		
		//
	}
	
	private Integer[] get_jäger_spawn() {
		return null;
		//return {0,0};
	}
	private Integer[] get_sammler_spawn() {
		return null;
		
		//return {0,0};
	}
	
	private void scan() {
		//ZharLevel
		for(int b=0; b<breite; b++) {
			for(int h=0; b<hoehe; h++) {
				level[b][h] = ZharLevel.fields[b][h];
			}
		}
		//ZharFiguren
		zharschatten = ZharSpielfeld.zharfiguren;
	}
}
