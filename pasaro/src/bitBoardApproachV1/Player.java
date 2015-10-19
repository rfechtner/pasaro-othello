package bitBoardApproachV1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Player {

	private ChipType chipType;
	private int score;
	private long ownChips;
	private long otherChips;
	
	private ArrayList<Integer> upLeft = new ArrayList<Integer>();
	private ArrayList<Integer> up = new ArrayList<Integer>();
	private ArrayList<Integer> upRight = new ArrayList<Integer>();
	private ArrayList<Integer> left = new ArrayList<Integer>();
	private ArrayList<Integer> right = new ArrayList<Integer>();
	private ArrayList<Integer> downLeft = new ArrayList<Integer>();
	private ArrayList<Integer> down = new ArrayList<Integer>();
	private ArrayList<Integer> downRight = new ArrayList<Integer>();
	
	private boolean gegnerDazwischen;
	private long possibleMoves;

	public Player(ChipType chipType, long ownChips, long otherChips) {
		this.chipType = chipType;
		this.score = 2;
		this.ownChips = ownChips;
		this.otherChips = otherChips;
	}

	public static long genOwnChips(ChipType chipType) {
		long ownChips;

		if (chipType == ChipType.BLACK) {
			ownChips = 34628173824L;
		} else {
			ownChips = 68853694464L;
		}

		return ownChips;
	}
	
	public static long genOtherChips(ChipType chipType) {
		long otherChips;

		if (chipType == ChipType.WHITE) {
			otherChips = 34628173824L;
		} else {
			otherChips = 68853694464L;
		}

		return otherChips;
	}

	public long getOwnChips() {
		return ownChips;
	}
	
	public void setOwnChips(long ownChips){
		this.ownChips = ownChips;
	}
	
	public long getOtherChips() {
		return otherChips;
	}
	
	public void setOtherChips(long otherChips){
		this.otherChips = otherChips;
	}

	public int getScore() {
		return score;
	}

	public ChipType getChipType() {
		return chipType;
	}
	
	public void toTurnAusgeben(HashMap<Integer, ArrayList<Integer>> toTurn){
		for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
		    Integer key = entry.getKey();
		    ArrayList<Integer> value = entry.getValue();
		    
		    System.out.println("\t\t\t\tKey: "+key+" , Value: "+value);
		}
	}

	public HashMap<Integer, ArrayList<Integer>> possibleMoves(long ownchips, long otherchips) {
		
		HashMap<Integer, ArrayList<Integer>> toTurn = new HashMap<Integer, ArrayList<Integer>>();
		
		toTurn.clear();
		
		up.clear();
		upLeft.clear();
		upRight.clear();
		left.clear();
		right.clear();
		down.clear();
		downLeft.clear();
		downRight.clear();
		
		berechneZuege(otherchips, ownchips, toTurn);
		
		long ausgabe = 0L;
		
		for (Entry<Integer, ArrayList<Integer>> entry : toTurn.entrySet()) {
		    Integer key = entry.getKey();
		    ArrayList<Integer> value = entry.getValue();
		    
		    System.out.println("Moeglicher Zug (toTurn): "+key+" --> "+value);
		    
		}
	
		possibleMoves = ausgabe;
		
		return toTurn;
		
	}

	public void berechneZuege(long gegner, long spieler, HashMap<Integer, ArrayList<Integer>> toTurn) {
		for (int i = 0; i < 64; i++) {
			if (((spieler >> i) & 1) == 1) {
				gehtUnten(gegner, spieler, i, toTurn);
				gehtLinks(gegner, spieler, i, toTurn);
				gehtRechts(gegner, spieler, i, toTurn);
				getOben(gegner, spieler, i, toTurn);
				gehtUntenLinks(gegner, spieler, i, toTurn);
				gehtUntenRechts(gegner, spieler, i, toTurn);
				gehtObenLinks(gegner, spieler, i, toTurn);
				gehtObenRechts(gegner, spieler, i, toTurn);
			}
		}
	}

	public void gehtUnten(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if (pos + 8 > 63) {
			up.clear();
			return;
		} else if (((gegner >> pos + 8) & 1) == 1) {

			up.add(pos+8);
			
			gegnerDazwischen = true;
			gehtUnten(gegner, spieler, pos + 8, toTurn);
			
		} else if (gegnerDazwischen && ((spieler >> pos + 8) & 1) != 1) {

			if(!toTurn.containsKey(pos+8)){				
				toTurn.put(pos+8, new ArrayList<Integer>());
			}
			
			for(Integer i : up){
				toTurn.get(pos+8).add(i);
			}
			up.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos+8)+" "+toTurn.get(pos+8));
			
			gegnerDazwischen = false;
		}else{
			up.clear();
		}
	}

	public void gehtLinks(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if ((pos - 1) % 8 == 7) {
			right.clear();
			return;
		} else if (((gegner >> pos - 1) & 1) == 1) {
			
			right.add(pos-1);
			
			gegnerDazwischen = true;
			gehtLinks(gegner, spieler, pos - 1, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos - 1) & 1) != 1) {

			if(!toTurn.containsKey(pos-1)){				
				toTurn.put(pos-1, new ArrayList<Integer>());
			}
			
			for(Integer i : right){
				toTurn.get(pos-1).add(i);
			}
			right.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos-1)+" "+toTurn.get(pos-1));
			
			gegnerDazwischen = false;
		}else{
			right.clear();
		}
	}

	public void gehtUntenLinks(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if (pos + 7 > 63 || (pos + 7) % 8 == 7) {
			upRight.clear();
			return;
		} else if (((gegner >> pos + 7) & 1) == 1) {
						
			upRight.add(pos+7);
			
			gegnerDazwischen = true;
			gehtUntenLinks(gegner, spieler, pos + 7, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos + 7) & 1) != 1) {

			if(!toTurn.containsKey(pos+7)){				
				toTurn.put(pos+7, new ArrayList<Integer>());
			}
			
			for(Integer i : upRight){
				toTurn.get(pos+7).add(i);
			}
			upRight.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos+7)+" "+toTurn.get(pos+7));
			
			gegnerDazwischen = false;
		}else{
			upRight.clear();
		}
	}

	public void gehtRechts(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if ((pos + 1) % 8 == 0) {
			left.clear();
			return;
		} else if (((gegner >> pos + 1) & 1) == 1) {
			
			left.add(pos+1);
			
			gegnerDazwischen = true;
			gehtRechts(gegner, spieler, pos + 1, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos + 1) & 1) != 1) {

			if(!toTurn.containsKey(pos+1)){				
				toTurn.put(pos+1, new ArrayList<Integer>());
			}
			
			for(Integer i : left){
				toTurn.get(pos+1).add(i);
			}
			left.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos+1)+" "+toTurn.get(pos+1));
			
			gegnerDazwischen = false;
		}else{
			left.clear();
		}
	}

	public void gehtUntenRechts(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if ((pos + 9 )> 63 || (pos+9) % 8 == 0) {
			upLeft.clear();
			return;
		} else if (((gegner >> pos + 9) & 1) == 1) {
			
			upLeft.add(pos+9);
			
			gegnerDazwischen = true;
			gehtUntenRechts(gegner, spieler, pos + 9, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos + 9) & 1) != 1) {

			if(!toTurn.containsKey(pos+9)){				
				toTurn.put(pos+9, new ArrayList<Integer>());
			}
			
			for(Integer i : upLeft){
				toTurn.get(pos+9).add(i);
			}
			upLeft.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos+9)+" "+toTurn.get(pos+9));
			
			gegnerDazwischen = false;
		}else{
			upLeft.clear();
		}
	}

	public void getOben(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if (pos - 8 < 0) {
			down.clear();
			return;
		} else if (((gegner >> pos - 8) & 1) == 1) {
			
			down.add(pos-8);
			
			gegnerDazwischen = true;
			getOben(gegner, spieler, pos - 8, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos - 8) & 1) != 1) {

			if(!toTurn.containsKey(pos-8)){				
				toTurn.put(pos-8, new ArrayList<Integer>());
			}
			
			for(Integer i : down){
				toTurn.get(pos-8).add(i);
			}
			down.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos-8)+" "+toTurn.get(pos-8));
			
			gegnerDazwischen = false;
		}else{
			down.clear();
		}
	}

	public void gehtObenRechts(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if (pos - 7 < 0 || (pos-7) % 8 == 0) {
			downLeft.clear();
			return;
		} else if (((gegner >> pos - 7) & 1) == 1) {
			
			downLeft.add(pos-7);
			
			gegnerDazwischen = true;
			gehtObenRechts(gegner, spieler, pos - 7, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos - 7) & 1) != 1) {

			if(!toTurn.containsKey(pos-7)){				
				toTurn.put(pos-7, new ArrayList<Integer>());
			}
			
			for(Integer i : downLeft){
				toTurn.get(pos-7).add(i);
			}
			downLeft.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos-7)+" "+toTurn.get(pos-7));
			
			gegnerDazwischen = false;
		}else{
			downLeft.clear();
		}
	}

	public void gehtObenLinks(long gegner, long spieler, int pos, HashMap<Integer, ArrayList<Integer>> toTurn) {
		if (pos - 9 < 0 || (pos - 9) % 8 == 7) {
			downRight.clear();
			return;
		} else if (((gegner >> pos - 9) & 1) == 1) {
			
			downRight.add(pos-9);
			
			gegnerDazwischen = true;
			gehtObenLinks(gegner, spieler, pos - 9, toTurn);
		} else if (gegnerDazwischen && ((spieler >> pos - 9) & 1) != 1) {

			if(!toTurn.containsKey(pos-9)){				
				toTurn.put(pos-9, new ArrayList<Integer>());
			}
			
			for(Integer i : downRight){
				toTurn.get(pos-9).add(i);
			}
			downRight.clear();
			
			System.out.println("Umzudrehen fuer: "+(pos-9)+" "+toTurn.get(pos-9));
			
			gegnerDazwischen = false;
		}else{
			downRight.clear();
		}
	}

	public void makeMove(int dezimal, HashMap<Integer, ArrayList<Integer>> toTurn) {

		ArrayList<Integer> tmpArray = toTurn.get(dezimal);
		
		long big = 0L;
		long dezLong = (long) Math.pow(2, dezimal);
		
		big = big | (1L << 63);
		
		if(dezimal == 63){
			ownChips = ownChips | big;
		}else{
			ownChips = ownChips | dezLong;
		}
		
		for(Integer i : tmpArray){
			dezLong = (long) Math.pow(2, i);
			ownChips = ownChips | dezLong;
			otherChips = otherChips & ~(1L << i);
		}
		
	}

	/* TEST */
	public void spielfeldAusgeben(long schwarz, long weiss) {
		String spielfeld[][] = new String[8][8];
		for (int i = 0; i < 64; i++) {
			spielfeld[i / 8][i % 8] = " ";
		}
		
		for (int i = 0; i < 64; i++) {
			if (((schwarz >> i) & 1) == 1) {
				spielfeld[i / 8][i % 8] = "s";
			}
			if (((weiss >> i) & 1) == 1) {
				spielfeld[i / 8][i % 8] = "w";
			}
		}
		for (int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(spielfeld[i]));
		}
	}
}
