package schach;

public class Testbench {
	static Schachbrett S;
	static SchachKI2 ki1;
	static SchachKI2 ki2;
	static String farbe="weiß";
	static SchachGUI G;
	
	static int siegeK1=0; static int  siegeK2=0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		G = new SchachGUI();
		while(true) {			
			test_graphical();
		}
	}
	
	public static void test_graphical() {
		
		
		S = new Schachbrett();
		String farben[] = {"weiß","schwarz"};
		ki1 = new SchachKI2(farben[(int) Math.random()*2]); ki1.rekursionstiefe=3;
		ki2 = new SchachKI2(ki1.gegnerfarbe); ki1.rekursionstiefe=4;
		G.canvas.lichtspiel = ki1.S = ki2.S = S;
	
		SchachController.hinweise=false;

		while(S.spiel_aktiv) {
			if(farbe.equals(ki1.eigenefarbe)) {
				ki1.zug_generieren();
				S.bewegungAusführen(ki1.KI_x1, ki1.KI_y1, ki1.KI_x2, ki1.KI_y2, ki1.eigenefarbe);
			}
			if(farbe.equals(ki2.eigenefarbe)) {
				ki2.zug_generieren();
				S.bewegungAusführen(ki2.KI_x1, ki2.KI_y1, ki2.KI_x2, ki2.KI_y2, ki2.eigenefarbe);
			}
			
			G.repaint();
			
			changeFarbe();
		}
		if(S.sieger.equals(ki1.eigenefarbe))++siegeK1;
		if(S.sieger.equals(ki2.eigenefarbe))++siegeK2;

		System.out.println("Siege KI 1: "+siegeK1+" , "+"Siege KI 2: "+siegeK2);
		
	}

	public static void changeFarbe() {
		if(farbe.equals("weiß"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "weiß";
	}
}
