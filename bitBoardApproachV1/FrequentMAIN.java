package bitBoardApproachV1;

import java.util.concurrent.ThreadPoolExecutor;

public class FrequentMAIN {

	
	static int wWon = 0;
	static int bWon = 0;
	
	
	public static void main(String[] args) {
		
//		BoardThread bt = new BoardThread();
		
//		bt.start();
		
		BoardThread[] array = new BoardThread[100];
		
		for (int i = 0; i < array.length; i++) {
			array[i] = new BoardThread();
		}
		
		
		for (int i = 0; i < array.length; i++) {
			
			array[i].start();
			
			while(array[i].isAlive()){
				System.out.print("");
			}
			
			if(array[i].getWinner() == 1){
				bWon++;
			}else{
				wWon++;
			}
		}
		
		
		
		System.out.println("wWon: "+wWon+", bWon: "+bWon);
	}
}
