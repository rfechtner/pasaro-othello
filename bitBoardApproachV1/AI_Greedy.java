package bitBoardApproachV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AI_Greedy extends Player {

	public AI_Greedy(ChipType chipType){
		super(chipType, genOwnChips(chipType), genOtherChips(chipType));
	}

	public void makeMove(int dezimal, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn) {
		super.makeMove(dezimal, toTurn);
	}

	public void makeMoveAI(ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){


		int greed = berechneGreedy(toTurn);


		makeMove(greed, toTurn);
	}

	private int berechneGreedy(ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn) {

		int biggest = 0;
		int ausgabe = 0;

		for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
		    Integer key = entry.getKey();
		    ArrayList<Integer> value = entry.getValue();

		    if(value.size() > biggest){
		    	biggest = value.size();
		    	ausgabe = key;
		    }

		}

		return ausgabe;
	}



}
