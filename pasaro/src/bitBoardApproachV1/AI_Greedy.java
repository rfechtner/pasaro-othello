package bitBoardApproachV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

<<<<<<< HEAD:pasaro/src/bitBoardApproachV1/AI.java
public class AI extends Player {

	private HashMap<Integer, Integer> moveScores = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> positionsMatrix = new HashMap<Integer, Integer>();
	private long nextOwnChips;
	private long nextOtherChips;
	private int[][] posMatrixArray = new int[][]{
		{  120, -20,  20,  5,  5,  20, -20, 120 },
		{  -20, -40,  -5, -5, -5,  -5, -40, -20 },
		{   20,  -5,  15,  3,  3,  15,  -5,  20 },
		{   5,   -5,   3,  0,  0,   3,  -5,  5 },
		{   5,   -5,   3,  0,  0,   3,  -5,  5 },
		{   20,  -5,  15,  3,  3,  15,  -5,  20 },
		{  -20, -40,  -5, -5, -5,  -5, -40, -20 },
		{  120, -20,  20,  5,  5,  20, -20, 120 } };

	public AI(ChipType chipType){
=======
public class AI_Greedy extends Player {
		
	public AI_Greedy(ChipType chipType){
>>>>>>> 82480aede6fa766bab03cd60e4a5ea2516705cd5:pasaro/src/bitBoardApproachV1/AI_Greedy.java
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

	public void makeMove(int dezimal, HashMap<Integer, ArrayList<Integer>> toTurn) {
		super.makeMove(dezimal, toTurn);
	}

	/**
	 * Updates scoring hash with new possible moves array and returns the position corresponding to the highest score --- the best move
	 * @param possibleMoves - array of all possible moves
	 * @return bestMove - position on board of best move calculated from best score
	 */


	public int getBestMove(Set<Integer> possibleMoves, int futTurns, HashMap<Integer, ArrayList<Integer>> toTurn){
		int bestMove=0;
		int bestScore=0;
		calculateScores(possibleMoves, futTurns, toTurn);
		//iterate through the best score hashmap selecting and storing the best score and move
		for(Integer i : possibleMoves){
			if(bestScore < moveScores.get(i)){
				bestScore = moveScores.get(i);
				bestMove = i;
			}
		}
		return bestMove;
	}

	/**
	 * Update moveScores hashmap with scores for all possible moves
	 * @param possibleMoves - array of all possible moves
	 */

	public void calculateScores(Set<Integer> possibleMoves, int futTurns, HashMap<Integer, ArrayList<Integer>> toTurn){
		//fill hash loop with possible move as key and score as value
		for(Integer i : possibleMoves){
			int score = 0;
			score += edgeScore(i);
			score += turnScore(i, toTurn);
			score += vulnerabilityScore(i, futTurns);
			moveScores.put(i, score);
		}
	}

	/**
	 * Runs similar to getBestMove but returns the score associated with the best move rather than the move itself.
	 * @param possibleMoves - array of all possible moves
	 * @param futTurns - how many turns into the future the method should look
	 * @return bestScore - the calculated best score
	 */

	public int getBestScore(Set<Integer> possibleMoves, int futTurns, HashMap<Integer, ArrayList<Integer>> toTurn){
		int bestScore = Integer.MIN_VALUE;

		calculateScores(possibleMoves, futTurns, toTurn);
		//iterate through the best score hashmap selecting and storing the best score
		for(Integer i : possibleMoves){
			if(bestScore < moveScores.get(i)){
				bestScore = moveScores.get(i);
			}
		}
		return bestScore;
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
	public int turnScore(int move, HashMap<Integer, ArrayList<Integer>> toTurn){
		int score=0;
		for (Integer i : toTurn.get(move)) {
		    score += i;
		}
		return score;
	}

	/**
	 * Calculates the vulnerability each move would create. Calls up the scoring methods for the board one move in the future. Works recursively until futureTurns is depleted
	 * @param move - move to be executed
	 * @param futureTurns - how many turns the AI should look into the future
	 * @return score
	 */
	public int vulnerabilityScore(int move, int futureTurns){
		nextOwnChips = getOwnChips();
		nextOtherChips = getOtherChips();
		HashMap<Integer, ArrayList<Integer>> toTurn = new HashMap<Integer, ArrayList<Integer>>();
		Set<Integer> nextPossMoves;
		int nextMoveBestScore = Integer.MIN_VALUE;
		int toBeSubtracted = 0;

		toTurn = possibleMoves (nextOwnChips, nextOtherChips);
		makeMoveFuture(move, toTurn);
		toTurn = possibleMoves (nextOwnChips, nextOtherChips);
		nextPossMoves = toTurn.keySet();
		nextMoveBestScore = getBestScore(nextPossMoves, --futureTurns, toTurn);
		if(nextMoveBestScore > 120){
			toBeSubtracted = 50;
		}else if(nextMoveBestScore > 50){
			toBeSubtracted = 10;
		}else if(nextMoveBestScore > 30){
			toBeSubtracted = 5;
		}else if(nextMoveBestScore > 10){
			toBeSubtracted = 3;
		}else{
			toBeSubtracted = 0;
		}
		return toBeSubtracted;
	}

	/**
	 * Functions identical to makeMove from Player class but works with future chips
	 * @param dezimal - move to make
	 * @param toTurn - current possible moves
	 */
	public void makeMoveFuture(int dezimal, HashMap<Integer, ArrayList<Integer>> toTurn) {

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
