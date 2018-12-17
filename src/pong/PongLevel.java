package pong;


public class PongLevel {
	public static PongBlock[][] b;
	static int sizeX, sizeY;
	
	static int anzLvl = 4;

	public static void init(int a, int windowX, int windowY) {
		if(a==2) {
			sizeX = sizeY = 5;
			b = new PongBlock[sizeX][sizeY];
			for(int i=0; i<sizeX; i++) {
				for(int j=0; j<sizeY; j++) {
					if(i>=1 && i<=3) b[i][j] = new PongBlock(300 + i*100, 300 + j*100, 50, 50, "hart");
					else  b[i][j] = new PongBlock(300 + i*100, 300 + j*100, 50, 50, "normal");

				}
			}
		}
		if(a==1) {
			sizeX = sizeY = 2;
			b = new PongBlock[sizeX][sizeY];
			for(int i=0; i<sizeX; i++) {
				for(int j=0; j<sizeY; j++) {
					if(i==0 &&j==0 || i==1 && j==1)b[i][j] = new PongBlock(300 + i*100, 300 + j*100, 50, 50, "spezial");
					else if(i==0 && j==1)b[i][j] = new PongBlock(300 + i*100, 300 + j*100, 50, 50, "hart");
					else b[i][j] = new PongBlock(300 + i*100, 300 + j*100, 50, 50, "normal");					
				}
			}
		}
		if(a==3) {
			sizeX = 20; sizeY = 8;
			b = new PongBlock[sizeX][sizeY];
			for(int i=0; i<sizeX; i++) {
				for(int j=0; j<sizeY; j++) {
					if(j==7 || j==0) {
						b[i][j] = new PongBlock(i*50, 300 + j*50, 50, 50, "hart");
						continue;
					}
					if(j>=3 && j<=6 && ((i>=3 && i<=6) || (i>=12 && i<=15)) || (j>=4 && j<=5 && (i>=7 && i<=11))){
						b[i][j] = new PongBlock(i*50, 300 + j*50, 50, 50, "explosiv");
						continue;
					}
					else if(i== 2 && j == 1 || i==3 && j==2) b[i][j] = new PongBlock( i*50, 300 + j*50, 50, 50, "spezial");
					else {
						b[i][j] = new PongBlock( i*50, 300 + j*50, 50, 50, "normal");
					}
				}
			}
		}
		if(a==4) {
			sizeX =10; sizeY = 8;
			b = new PongBlock[sizeX][sizeY];
			for(int i=0; i<sizeX; i++) {
				for(int j=0; j<sizeY; j++) {
					double zufall = Math.random();
					if(zufall < 0.4) {
						b[i][j] = new PongBlock(100 + i*70, 100 + j*70, 50, 50, "normal");
						b[i][j].alive=false;
					}
					if(zufall>=0.4 && zufall<=0.7) {
						b[i][j] =  new PongBlock(100 + i*70, 100 + j*70, 50, 50, "normal");
					}
					else if(zufall>=0.7 && zufall<0.9) {
						b[i][j] =  new PongBlock(100 + i*70, 100 + j*70, 50, 50, "hart");
					}
					else if(zufall>=0.9)b[i][j] =  new PongBlock(100 + i*70, 100 + j*70, 50, 50, "spezial");
				}
			}
		}
	}
}
