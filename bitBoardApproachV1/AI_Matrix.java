package bitBoardApproachV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AI_Matrix extends Player{
	
	private int[] posMatrixArray = new int[]{
		  120, -20,  20,  5,  5,  20, -20, 120 ,
		  -20, -40,  -5, -5, -5,  -5, -40, -20 ,
		   20,  -5,  15,  3,  3,  15,  -5,  20 ,
		   5,   -5,   3,  0,  0,   3,  -5,  5 ,
		  5,   -5,   3,  0,  0,   3,  -5,  5 ,
		   20,  -5,  15,  3,  3,  15,  -5,  20 ,
		  -20, -40,  -5, -5, -5,  -5, -40, -20 ,
		  120, -20,  20,  5,  5,  20, -20, 120  };

	public AI_Matrix(ChipType chipType) {
		super(chipType, genOwnChips(chipType), genOtherChips(chipType));
	}
	
	public void makeMove(int dezimal, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		super.makeMove(dezimal, toTurn);
	}
	
	public void makeMoveAI(ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		
		int matrixBest = berechneAusMatrix(toTurn);
		
		makeMove(matrixBest, toTurn);
		
	}
	
	public int[] getMatrix(){
		return posMatrixArray;
	}
	
	public int berechneAusMatrix(ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		
		int selectedMove = 0;
		int highestValue = Integer.MIN_VALUE;
		
		for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
		    Integer key = entry.getKey();
		    
		    if(posMatrixArray[key] > highestValue && toTurn.get(key).size() != 0){
		    	highestValue = posMatrixArray[key];
		    	selectedMove = key;
		    }
		    
		}
		
		return selectedMove;
	}

}
