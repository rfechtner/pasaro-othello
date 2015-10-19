package bitBoardApproachV1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import BitboardApproach.Spieler;

public class Board {
	int currentTurn; // 1 = player 2 = A.I.
	boolean finished;
	long playerChips;
	long aiChips;
	boolean beendet;
	
	Human h;
	AI_Greedy a;
	
	public void initGame(){
		
		System.out.println("GIT Version");
		
		h = new Human(ChipType.WHITE);
		a = new AI_Greedy(ChipType.BLACK);
		
		playerChips = h.getOwnChips();
		aiChips = a.getOwnChips();
		
		currentTurn = 1 + (int)(Math.random() * ((2 - 1) + 1));
	}
	
	public void startGame() throws IOException{
		initGame();
		HashMap<Integer, ArrayList<Integer>> toTurn = new HashMap<Integer, ArrayList<Integer>>();
		
		while(!finished){
			if(currentTurn == 1){
				h.setOwnChips(playerChips);
				h.setOtherChips(aiChips);
				toTurn = h.possibleMoves(playerChips, aiChips);
				h.spielfeldAusgeben(aiChips, playerChips);
				
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Geben Sie ihren Zug ein (w): ");
				String s = in.readLine();
				
				h.makeMove(Integer.parseInt(s), toTurn);
				
				playerChips = h.getOwnChips();
				aiChips = h.getOtherChips();
				
				currentTurn = 2;
				
			}else{
				a.setOwnChips(aiChips);
				a.setOtherChips(playerChips);
				toTurn = a.possibleMoves(aiChips, playerChips);
				
				a.spielfeldAusgeben(aiChips, playerChips);
				
//				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//				System.out.print("Geben Sie ihren Zug ein (s): ");
//				String s = in.readLine();
			
//				a.makeMove(Integer.parseInt(s));
				
				a.makeMoveAI(toTurn);
				
				aiChips = a.getOwnChips();
				playerChips = a.getOtherChips();
				
				currentTurn = 1;
				
			}
		}
	}
	
	public void setBeendet(){
		
		if((playerChips | aiChips) == -1){
			beendet = true;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		Board b = new Board();
		
		b.startGame();
	}
	
}
