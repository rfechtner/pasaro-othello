package bitBoardApproachV1;

import java.io.IOException;

public class BoardThread extends Thread{

	int ein;
	
	public BoardThread() {
		super();
	}
	
	public int getWinner(){
		return ein;
	}
	
	@Override
	public void run() {
//		super.run();
		Board b = new Board();		
		
		try {
			b.startGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ein = b.winner;
	}
}
