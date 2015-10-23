package bitBoardApproachV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AI_Basic extends Player {

	private HashMap<Integer, Integer> positionsMatrix = new HashMap<Integer, Integer>();
	private long nextOwnChips;
	private long nextOtherChips;
	private int[][] posMatrixArray = new int[][]{
		{   99,  -8,   8,  6,  6,   8,  -8,  99 },
		{   -8, -24,  -4, -3, -3,  -4, -24,  -8 },
		{    8,  -4,   7,  4,  4,   7,  -4,   8 },
		{    6,  -3,   3,  0,  0,   3,  -3,   6 },
		{    6,  -3,   3,  0,  0,   3,  -3,   6 },
		{    8,  -4,   7,  4,  4,   7,  -4,   8 },
		{   -8, -24,  -4, -3, -3,  -4, -24,  -8 },
		{   99,  -8,   8,  6,  6,   8,  -8,  99 } };

	public AI_Basic(ChipType chipType){
		super(chipType, genOwnChips(chipType), genOtherChips(chipType));
		//fills the hashmap with the desired score values for each position
		int key = 0;
		for(int y = 0;y < 8;y++){
			for(int x = 0;x < 8; x++){
				key = x+y*8;
				positionsMatrix.put(key, posMatrixArray[x][y]);
			}
		}
	}

	public void makeMove(int dezimal, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn) {
		super.makeMove(dezimal, toTurn);
	}


	/**
	 * Updates scoring hash with new possible moves array and returns the position corresponding to the highest score --- the best move
	 * @param possibleMoves - array of all possible moves
	 * @return bestMove - position on board of best move calculated from best score
	 */
	public int getBestMove(int futTurns, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		ConcurrentHashMap<Integer, Integer> moveScores = new ConcurrentHashMap<Integer, Integer>();
		nextOwnChips = getOwnChips();
		nextOtherChips = getOtherChips();
		Set<Integer> possibleMoves = toTurn.keySet();
		int bestMove=0;
		int bestScore=Integer.MIN_VALUE;
		moveScores = calculateScores(futTurns, toTurn);
		//iterate through the best score hashmap selecting and storing the best score and move
		for(Integer i : possibleMoves){
			System.out.println("Possible move: "+i+" with score of: "+moveScores.get(i));
			if(bestScore < moveScores.get(i)){
				bestScore = moveScores.get(i);
				bestMove = i;
			}
		}
		return bestMove;
	}


	/**
	 * Runs similar to getBestMove but returns the score associated with the best move rather than the move itself.
	 * @param possibleMoves - array of all possible moves
	 * @param futTurns - how many turns into the future the method should look
	 * @return bestScore - the calculated best score
	 */
	public int getBestScore(int futTurns, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		ConcurrentHashMap<Integer, Integer> moveScores2 = new ConcurrentHashMap<Integer, Integer>();
		int bestScore = Integer.MIN_VALUE;
		Set<Integer> possibleMoves = toTurn.keySet();

		moveScores2 = calculateScores(futTurns, toTurn);
		//iterate through the best score hashmap selecting and storing the best score
		for(Integer i : possibleMoves){
			if(bestScore < moveScores2.get(i)){
				bestScore = moveScores2.get(i);
			}
		}
		return bestScore;
	}


	/**
	 * Update moveScores hashmap with scores for all possible moves
	 * @param possibleMoves - array of all possible moves
	 */
	public ConcurrentHashMap<Integer, Integer> calculateScores(int futTurns, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		ConcurrentHashMap<Integer, ArrayList<Integer>> toTurnClone = new ConcurrentHashMap<Integer, ArrayList<Integer>>();
		ConcurrentHashMap<Integer, Integer> newMoveScores = new ConcurrentHashMap<Integer, Integer>();
		Set<Integer> possibleMoves = toTurn.keySet();
		toTurnClone = toTurn;
		//fill hash loop with possible move as key and score as value
		System.out.println("PossibleMoves.size: "+possibleMoves.size());
		for(Integer i : possibleMoves){
			toTurn = toTurnClone;
			int score = 0;
			score += edgeScore(i);
			score += turnScore(i, toTurn);
			if(futTurns>0){
				score += vulnerabilityScore(i, futTurns);
			}
			newMoveScores.put(i, score);
			System.out.println("for: "+i+" size: "+newMoveScores.size());
		}

		return newMoveScores;
	}


	/**
	 * Reads a value out of the positionsMatrix hashmap
	 * @param move - the move being evaluated
	 * @return corresponding points awarded/subtracted based on the values in the positionMatrix set when AI is initialized
	 */
	public int edgeScore(int move){
		return positionsMatrix.get(move);
	}


	/**
	 * Increases score by the amount of pieces the move would turn over
	 * @param move - move to be executed
	 * @return score
	 */
	public int turnScore(int move, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn){
		return toTurn.get(move).size();
	}


	/**
	 * Calculates the vulnerability each move would create. Calls up the scoring methods for the board one move in the future. Works recursively until futureTurns is depleted
	 * @param move - move to be executed
	 * @param futureTurns - how many turns the AI should look into the future
	 * @return score
	 */
	public int vulnerabilityScore(int move, int futureTurns){
		ConcurrentHashMap<Integer, ArrayList<Integer>> nextToTurn = new ConcurrentHashMap<Integer, ArrayList<Integer>>();
		System.out.println("move: "+move);
		int nextMoveBestScore = Integer.MIN_VALUE;
		long origNextOwnChips = nextOwnChips;
		long origNextOtherChips = nextOtherChips;
		System.out.println("nextOwnChips: "+nextOwnChips);
		System.out.println("nextOtherChips: "+nextOtherChips);
		nextToTurn = possibleMoves(nextOwnChips, nextOtherChips);
		makeMoveFuture(move, nextToTurn);
		nextToTurn.clear();
		nextToTurn = possibleMoves(nextOtherChips, nextOwnChips);
		nextMoveBestScore = getBestScore(--futureTurns, nextToTurn);

		nextOwnChips = origNextOwnChips;
		nextOtherChips = origNextOtherChips;

		if(nextMoveBestScore > 120){
			return -50;
		}else if(nextMoveBestScore > 50){
			return -10;
		}else if(nextMoveBestScore > 30){
			return -5;
		}else if(nextMoveBestScore > 10){
			return -3;
		}else{
			return 0;
		}
	}


	/**
	 * Functions identical to makeMove from Player class but works with future chips
	 * @param dezimal - move to make
	 * @param toTurn - current possible moves
	 */
	public void makeMoveFuture(int dezimal, ConcurrentHashMap<Integer, ArrayList<Integer>> toTurn) {

		ArrayList<Integer> tmpArray = toTurn.get(dezimal);

		long big = 0L;
		long dezLong = (long) Math.pow(2, dezimal);

		big = big | (1L << 63);

		if(dezimal == 63){
			nextOwnChips = nextOwnChips | big;
		}else{
			nextOwnChips = nextOwnChips | dezLong;
		}

		for(Integer i : tmpArray){
			dezLong = (long) Math.pow(2, i);
			nextOwnChips = nextOwnChips | dezLong;
			nextOtherChips = nextOtherChips & ~(1L << i);
		}

	}

}
