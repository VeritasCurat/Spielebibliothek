package m�hle;

import java.io.IOException;
import javax.swing.JPanel;

public class M�hleController extends JPanel{
	static M�hleGUI G;
	
	//Variablen f�r KI
		static boolean spiel_modus_KI= false;
		static boolean farbe_KI_schwarz = true;
		
	private static final long serialVersionUID = 1L;
	static M�hleSpielfeld S = new M�hleSpielfeld();
	static String farbe="wei�";
	static String mark=""; 
	static int auswahlX = -1; static int auswahlY=-1; static int auswahlRing = -1;
	static int markX = -1; static int markY=-1; static int markRing = -1;
	static boolean markiert = false;
	static String wegnehmen_spieler = "";
	
	static int phaseWei�=0; static int phaseSchwarz=0;//0: setzen, 1: 4-9 Figuren, 2: 1-3 Figuren
	
	static int anz_wei�=0;
	static int anz_schwarz=0;
	
	public static void changeFarbe() {
		if(farbe.equals("wei�"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "wei�";
	}
	
	
	private static int koordinateInRing(int x, int y) {
		if(x==3 && y==3)return -1;
		else if(x==0 || y==0 || x==6 || y==6) {
			if(x==1 || x==2 || x==4 || x==5 || y==1 || y==2 || y==4 || y==5)return -1;
			else return 0;
		}
		else if(x==1 || x==5 || y==1 || y==5) {
			if(x==2 || x==4 || y==2 || y==4)return -1;
			else return 1;
		}
		else if(x==2 || y==2 || x==4 || y==4) {
			return 2;
		}

		else return -1;
	}
	
	private static int[] koordinateInRingKoordinate(int x, int y) {
		int[] k = new int[3];		
		k[2] = koordinateInRing(x, y);
		if(k[2] == -1)return null;
		if(k[2] == 0) {
			k[0] = x/3; k[1] = y/3;
		}
		else if(k[2] == 1) {
			k[0] = (x-1)/2; k[1] = (y-1)/2;
		}
		else if(k[2] == 2) {
			k[0] = x-2; k[1] = y-2;
		}
		return k;
	}
	
	public static String markFigure(int x, int y) {
		
		int[] k = koordinateInRingKoordinate(x, y);
		if(k[0]==-1 || k[1]==-1 || k[2]==-1)return "";
		
		String f = S.figuren[k[0]][k[1]][k[2]].farbe.substring(0,1);
		String t = S.figuren[k[0]][k[1]][k[2]].typ.substring(0,1);
		System.out.println(x+" "+y+" "+k[2]+": "+f+t);

		if(!f.equals(farbe.substring(0,1))) {
			System.out.println("falsche Farbe: "+f+"!="+farbe.substring(0,1));
			return "";
		}

		mark = f+t; auswahlX = k[0]; auswahlY =k[1]; auswahlRing = k[2];
		return f+t;
	}
	
	public static M�hleFigur getFigur(int x, int y, int ring) {
		return S.figuren[x][y][ring];
	}
	
	

	public static void setFigure(int x, int y, int ring, String farbe) {
		//System.out.println("GUI bewegung: "+mark+": "+markX+" "+markY+" "+markRing+" => "+x+" "+y+" "+ring);
		if(!S.bewegungAusf�hren(markX, markY, markRing, x, y, ring, farbe))return;	

		
		//wenn KI eingeschaltet reagiert jetzt die KI
//				if(spiel_modus_KI) {
//					Spielfeld.automatic = true;
//					SimpleKI.ziehen();
//					if(!SimpleKI.FEHLER)Spielfeld.bewegungAusf�hren(SimpleKI.KI_x1, SimpleKI.KI_y1, SimpleKI.KI_x2, SimpleKI.KI_y2, SimpleKI.farbe);
//					Spielfeld.automatic = false;
//					changeFarbe();
//
//				}
		
		mark = ""; auswahlX = -1; auswahlY = -1; auswahlRing =-1;
		markiert = false;
		changeFarbe();
	}
	
	public static void main(String[] args) throws IOException {	
		if(spiel_modus_KI)spiel_gegen_KI();
		else spiel_gegen_menschen();
	}
	
	public static void spieler_aktion() {
		if(ShowCanvas.x==-1 || ShowCanvas.y==-1)return;
		int[] k = koordinateInRingKoordinate(ShowCanvas.x, ShowCanvas.y);
		if(k==null)return;
		auswahlX = k[0]; auswahlY = k[1]; auswahlRing = k[2];
		if(!S.spiel_aktiv) {

			System.exit(0);
		}
		if(farbe.equals("wei�") && wegnehmen_spieler.equals("schwarz")) {
			if(!S.stein_nehmen(auswahlX, auswahlY, auswahlRing, "wei�"))return;
			if(!S.deleteFigure(auswahlX, auswahlY, auswahlRing, "schwarz")) return;
			auswahlRing = auswahlX = auswahlY = -1;
			wegnehmen_spieler="";
			G.repaint();
			changeFarbe();
			return;
		}
		if(farbe.equals("schwarz") && wegnehmen_spieler.equals("wei�")) {
			if(!S.stein_nehmen(auswahlX, auswahlY, auswahlRing, "schwarz"))return;
			if(!S.deleteFigure(auswahlX, auswahlY, auswahlRing, "wei�")) return;
			auswahlRing = auswahlX = auswahlY = -1;
			wegnehmen_spieler="";
			G.repaint();
			changeFarbe();
			return;
		}
		if(farbe.equals("wei�") && phaseWei�==0) {
			if(!S.setFigure(auswahlX, auswahlY, auswahlRing, farbe))return;
			auswahlRing = auswahlX = auswahlY = -1;
			G.repaint();
			S.m�hle_erkennen("wei�");
			if(S.wegnehmer_wei�> 0 && S.beklaubar("schwarz")) {
				wegnehmen_spieler="schwarz";
				return;
			}
			if(S.anz_wei�==9)phaseWei�=1;
			changeFarbe();
			return;
		}
		if(farbe.equals("schwarz") && phaseSchwarz==0) {
			if(!S.setFigure(auswahlX, auswahlY, auswahlRing, farbe))return;
			auswahlRing = auswahlX = auswahlY = -1;
			G.repaint();
			S.m�hle_erkennen("schwarz");
			if(S.wegnehmer_schwarz > 0 && S.beklaubar("wei�")) {
				wegnehmen_spieler="wei�";
				return;
			}
			if(S.anz_schwarz==9)phaseSchwarz=1;
			changeFarbe();
			return;
		}
		if(((farbe.equals("schwarz") && phaseSchwarz>0) || (farbe.equals("wei�") && phaseWei�>0) ) && !M�hleController.markiert) {
			M�hleController.mark = M�hleController.markFigure((int) ShowCanvas.x, (int) (ShowCanvas.y));
			if(M�hleController.mark.equals(""))return;
  
			M�hleController.markiert = true;
			System.out.println(M�hleController.mark);
			G.repaint();
		}
		else if(M�hleController.markiert) {
			M�hleController.markiert =false;
			M�hleController.setFigure(auswahlX,auswahlY,auswahlRing,farbe);
			M�hleController.markX = M�hleController.markY = M�hleController.markRing=-1; 
			M�hleController.mark = "";

			G.repaint();
			
			if(farbe.equals("schwarz")) {
				S.m�hle_erkennen("schwarz");
				System.out.println(S.wegnehmer_schwarz);
				if(S.wegnehmer_schwarz > 0 && S.beklaubar("wei�")) {
					wegnehmen_spieler="wei�";
					return;
				}				
			}
			else if(farbe.equals("wei�")) {
				S.m�hle_erkennen("wei�");
				System.out.println(S.wegnehmer_wei�);
				if(S.wegnehmer_wei� > 0 && S.beklaubar("schwarz")) {
					wegnehmen_spieler="schwarz";
					return;
				}
			}
		}	
		
		return;
	}
	
	
	public static void spiel_gegen_menschen() {
		S.init();
		
		G = new m�hle.M�hleGUI(S);
		
		G.repaint();
		test();
	}
	
	public static void spiel_gegen_KI() {
		S.init();

		G = new m�hle.M�hleGUI(S);
		G.repaint();	
	}
	
	private static void test() {
		phaseWei� = phaseSchwarz = 1; 
		S.anz_schwarz = S.anz_wei� = 9;
		S.figuren[0][0][0] = new L�ufer(0, 0, 0, "wei�");
		S.figuren[2][0][0] = new L�ufer(0, 0, 0, "wei�");
		S.figuren[1][0][1] = new L�ufer(0, 0, 0, "wei�"); //offene M�hle wei�
		
		S.figuren[0][2][0] = new L�ufer(0, 0, 0, "schwarz");
		S.figuren[2][2][0] = new L�ufer(0, 0, 0, "schwarz");
		S.figuren[1][2][1] = new L�ufer(0, 0, 0, "schwarz"); //offene M�hle schwarz
		
		
	}
	
}
