package bitBoardApproachV1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Board {
	int currentTurn; // 1 = player 2 = A.I.
	boolean finished;
	long playerChips;
	long aiChips;
	
	int scoreSpieler1;
	int scoreSpieler2;
	
	Human h;
	AI_Greedy a, a2;
	AI_Random r;
	
	public void initGame(){
		
		System.out.println("GIT Version");
		
//		h = new Human(ChipType.WHITE);
//		a = new AI_Greedy(ChipType.BLACK);
		
		r = new AI_Random(ChipType.BLACK);
		a = new AI_Greedy(ChipType.WHITE);
		
//		playerChips = h.getOwnChips();
		aiChips = a.getOwnChips();
		playerChips = r.getOwnChips();
		
		currentTurn = 1 + (int)(Math.random() * ((2 - 1) + 1));
	}
	
	public void startGame() throws IOException{
		initGame();
		HashMap<Integer, ArrayList<Integer>> toTurn = new HashMap<Integer, ArrayList<Integer>>();
		
		while(!finished){
			if(currentTurn == 1){
				
				/*
				 * Spieler 
				 */
//				h.setOwnChips(playerChips);
//				h.setOtherChips(aiChips);
//				toTurn = h.possibleMoves(playerChips, aiChips);
//				h.spielfeldAusgeben(aiChips, playerChips);
//				
//				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//				System.out.print("Geben Sie ihren Zug ein (w): ");
//				String s = in.readLine();
//				
//				h.makeMove(Integer.parseInt(s), toTurn);
//				
//				playerChips = h.getOwnChips();
//				aiChips = h.getOtherChips();
//				
//				currentTurn = 2;
				
				/*
				 * AI_Greedy
				 */
				
//				a2.setOwnChips(playerChips);
//				a2.setOtherChips(aiChips);
//				toTurn = a2.possibleMoves(playerChips, aiChips);
//				a2.spielfeldAusgeben(aiChips, playerChips);
//				
//				boolean flag = true;
//				for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
//				    ArrayList<Integer> value = entry.getValue();
//				    
//				    if(!value.isEmpty()){
//				    	flag = false;
//				    }
//				    
//				}
//				
//				if(flag){
//					finished = true;
//					break;
//				}
//				
//				a2.makeMoveAI(toTurn);
//				
//				aiChips = a2.getOtherChips();
//				playerChips = a2.getOwnChips();
//				scoreSpieler1 = a2.getOwnScore(playerChips, aiChips);
//				scoreSpieler2 = a2.getOtherScore(playerChips, aiChips);
//				
//				if(a2.isFilled(scoreSpieler1, scoreSpieler2)){
//					finished = true;
//					break;
//				}
//				
//				currentTurn = 2;
				
				/*
				 * AI_Random
				 */
				
				r.setOwnChips(playerChips);
				r.setOtherChips(aiChips);
				toTurn = r.possibleMoves(playerChips, aiChips);
				r.spielfeldAusgeben(aiChips, playerChips);
				
				boolean flag = true;
				for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
				    ArrayList<Integer> value = entry.getValue();
				    
				    if(!value.isEmpty()){
				    	flag = false;
				    }
				    
				}
				
				if(flag){
					finished = true;
					System.out.println("\t\t\t\t\tKeine Zuege fuer w");
					break;
				}
				
				r.makeMoveAI(toTurn);
				
				aiChips = r.getOtherChips();
				playerChips = r.getOwnChips();
				scoreSpieler1 = r.getOwnScore(playerChips, aiChips);
				scoreSpieler2 = r.getOtherScore(playerChips, aiChips);
				
				if(r.isFilled(scoreSpieler1, scoreSpieler2)){
					finished = true;
					break;
				}
				
				currentTurn = 2;
				
			}else{
				a.setOwnChips(aiChips);
				a.setOtherChips(playerChips);
				toTurn = a.possibleMoves(aiChips, playerChips);
				
				a.spielfeldAusgeben(aiChips, playerChips);
				
				boolean flag = true;
				for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
				    ArrayList<Integer> value = entry.getValue();
				    
				    if(!value.isEmpty()){
				    	flag = false;
				    }
				    
				}
				
				if(flag ){
					finished = true;
					System.out.println("\t\t\t\t\tKeine Zuege fuer s");
					break;
				}
				
				a.makeMoveAI(toTurn);
				
				aiChips = a.getOwnChips();
				playerChips = a.getOtherChips();
				
				scoreSpieler2 = a.getOwnScore(aiChips, playerChips);
				scoreSpieler1 = a.getOtherScore(aiChips, playerChips);
				
				if(a.isFilled(scoreSpieler1, scoreSpieler2)){
					finished = true;
					break;
				}
				
				currentTurn = 1;
				
			}
		}
		
		System.out.println("------------------------------------");
//		a2.spielfeldAusgeben(aiChips, playerChips);
		System.out.println("------------------------------------");
		System.out.println("Spiel beendet!");
		System.out.println("Score (w): "+scoreSpieler1);
		System.out.println("Score (s): "+scoreSpieler2);
	}
	
	public boolean isBeendet(long player, long enemy){
		
		if((player | enemy) == -1){
			return true;
		}else{
			return false;			
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		Board b = new Board();
		
		b.startGame();
	}
	
}
