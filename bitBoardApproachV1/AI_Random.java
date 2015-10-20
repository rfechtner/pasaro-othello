package bitBoardApproachV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class AI_Random extends Player {
		
	public AI_Random(ChipType chipType){
		super(chipType, genOwnChips(chipType), genOtherChips(chipType));
	}
	
	public void makeMove(int dezimal, HashMap<Integer, ArrayList<Integer>> toTurn) {
		super.makeMove(dezimal, toTurn);
	}
	
	public void makeMoveAI(HashMap<Integer, ArrayList<Integer>> toTurn){
		
		
		int greed = berechneRandom(toTurn);
		
		
		makeMove(greed, toTurn);
	}
	
	private int berechneRandom(HashMap<Integer, ArrayList<Integer>> toTurn) {
		
		int ausgabe = 0;
		
		int zug = 1 + (int)(Math.random() * ((toTurn.size() - 1) + 1));
		int count = 1;
		
		for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
		    Integer key = entry.getKey();
		    
		    if(count == zug){
		    	ausgabe = key;
		    }
		    count++;
		    
		}
		
		return ausgabe;
	}
}