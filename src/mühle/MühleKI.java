package m�hle;

public class M�hleKI {

	int x1, y1, ring1, x2, y2, ring2;
	int rekursionstiefe=3;
	
	M�hleSpielfeld schattenfeld;
	
	public M�hleKI(M�hleSpielfeld S) {
		// TODO Auto-generated constructor stub
		schattenfeld = new M�hleSpielfeld();
		
	}
	
	/**
	 * erzeugt neuen zug f�r KI.
	 * @param s aktuelles Spielfeld
	 */
	public void newzug(M�hleSpielfeld s) {
		schattenfeld.write_sf(s);
		Integer[][] zug = minimax(rekursionstiefe);
	}
	
	/**
	 * 
	 * @param tiefe
	 * @return
	 */
	private Integer[][] minimax(int tiefe){
		
		
	}
}
