package mühle;

public class MühleKI {

	int x1, y1, ring1, x2, y2, ring2;
	int rekursionstiefe=3;
	
	MühleSpielfeld schattenfeld;
	
	public MühleKI(MühleSpielfeld S) {
		// TODO Auto-generated constructor stub
		schattenfeld = new MühleSpielfeld();
		
	}
	
	/**
	 * erzeugt neuen zug für KI.
	 * @param s aktuelles Spielfeld
	 */
	public void newzug(MühleSpielfeld s) {
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
