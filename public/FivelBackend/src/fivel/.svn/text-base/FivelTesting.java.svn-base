package fivel;

import java.util.Random;

import fivel.config.Difficulty;
import fivel.enums.EndStates;
import fivel.model.BoardGame;

/**
 * TESTING CLASS.
 * In order to run Fivel without the front end, run this class as the main program. 
 * You can configure some hard-coded parameters in order to check different game options.
 * They all appear within the main function.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class FivelTesting
{
	/** CONFIGURABLES */
	public static final int numberOfGamesPerMatchType = 1; 	// How many games per match type.
	public static final int difficultyMin             = 0; 		// Minimum range for difficulty (0-3). Should be lower or equal to max.
	public static final int difficultyMax             = 2; 		// Maximum range for difficulty (0-3). Should be higher or equal to min.
	public static final boolean singleMatch           = false; 	// Whether to play a single game (with difficultyMin and Max as the players) 
																// or a range of games (from min to max - all permutations).
	/**
	 * Main function for testing purposes.
	 * 
	 * @param args Program arguments (not useD).
	 */
	public static void main(String[] args)
	{
		int matches;
		// How many matches types will be played.
		if (singleMatch) matches = 1;
		else
		{
			int difference = difficultyMax - difficultyMin + 1;
			matches = (difference * (difference + 1))/2;
		}
		String[][] results = new String[matches][numberOfGamesPerMatchType]; // Stores results.
		String[][] names   = new String[matches][2]; // Stores players names according to matches.
		turnsAnalysis      = new int[matches][numberOfGamesPerMatchType]; // Stores average turns per match.
		
		// Play the games.
		if (singleMatch)
		{
			Difficulty pl0 = FivelDifficulties.LEVELS[difficultyMin];
			Difficulty pl1 = FivelDifficulties.LEVELS[difficultyMax];
			names[0][0] = pl0.name;
			names[0][1] = pl1.name;
			results[0] = playSet(0, pl0, pl1, numberOfGamesPerMatchType);
		}
		else
		{
			int match = 0;
			for (int minRange = difficultyMin; minRange <= difficultyMax; minRange++)
			{
				for (int maxRange = minRange; maxRange <= difficultyMax; maxRange++)
				{
					Difficulty pl0 = FivelDifficulties.LEVELS[minRange];
					Difficulty pl1 = FivelDifficulties.LEVELS[maxRange];
					names[match][0] = pl0.name;
					names[match][1] = pl1.name;
					results[match] = playSet(match, pl0, pl1, numberOfGamesPerMatchType);
					match++;
				}
			}
		}
		
		// Sum results.
		System.out.println();
		System.out.println("MATCHES RESULTS (" + numberOfGamesPerMatchType + "games played in each match):");
		for (int match = 0; match < matches; match++)
		{
			String name0 = names[match][0];
			String name1 = names[match][1];
			
			double pl0  = 0;
			double pl1  = 0;
			double draw = 0;
			
			for (int result = 0; result < results[match].length; result++)
			{
				if (results[match][result].equals(name0)) pl0++;
				else if (results[match][result].equals(name1)) pl1++;
				else draw++;
			}
			
			double per0 = (pl0 / numberOfGamesPerMatchType) * 100.0; 
			double per1 = (pl1 / numberOfGamesPerMatchType) * 100.0;
			double perDraw = (draw / numberOfGamesPerMatchType) * 100.0;
			double avgTurns = 0;
			for (int game = 0; game < turnsAnalysis[match].length; game++)
			{
				avgTurns += turnsAnalysis[match][game];
			}
			avgTurns /= turnsAnalysis[match].length;
			
			System.out.print("MATCH TYPE #" + (match + 1) + "\t-\t" + name0 + " ("); 
			System.out.printf("%.0f", per0); 
			System.out.print("%)\tvs.\t" + name1 + " ("); 
			System.out.printf("%.0f", per1);
			System.out.print("%)\t|   DRAW (");
			System.out.printf("%.0f", perDraw);
			System.out.println("%)\t|   AVG. TURNS: " + avgTurns);
		}
		System.out.println();
		System.out.println("========================================================");
		
		// a&b Performance Analysis.
		System.out.println();
		System.out.println("a&b PERFORMANCE ANALYSIS:");
		System.out.println("The average time (in ms) it took the a&b algorithm to perform a move according to the mutual turn it was requested in.");
		System.out.println();
		
		System.out.print("DEPTH/TURN\t");
		for (int i = 1; i <= 16; i++)
		{
			System.out.print(i + "\t");
		}
		System.out.println();
		System.out.println();
		
		long[][][] analysis = performanceAnalysis;
		for (int i = 1; i <= 5; i++)
		{
			System.out.print(i + "\t\t");
			for (int j = 1; j <= 16; j++)
			{
				double avg;
				if (analysis[i-1][j-1][1] == 0) avg = 0;
				else avg = (double) analysis[i-1][j-1][0] / (double) analysis[i-1][j-1][1]; 
				System.out.printf("%.1f\t", avg);
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Plays a set of games and returns a victory report.
	 * 
	 * @param matchNumber Match number to save analysis statically.
	 * @param player1 Difficulty settings for player 1.
	 * @param player2 Difficulty settings for player 2.
	 * @param games Number of games.
	 * 
	 * @return A result array.
	 */
	public static String[] playSet(int matchNumber, Difficulty player1, Difficulty player2, int games)
	{
		String[] setResults = new String[games];
		for (int i = 0; i < games; i++)
		{
			setResults[i] = playGame(matchNumber, i, player1, player2);
		}
		return setResults;
	}
	
	/**
	 * Plays a game with the given options.
	 * 
	 * @param matchNumber Match number to save analysis statically.
	 * @param gameNumber Game number id.
	 * @param player1 Difficulty settings for player 1.
	 * @param player2 Difficulty settings for player 2.
	 * 
	 * @return Name of player or draw.
	 */
	public static String playGame(int matchNumber, int gameNumber, Difficulty player1, Difficulty player2)
	{
		System.out.println("STARTING GAME: " + player1.name + " vs. " + player2.name);
		System.out.println("");
		
		BoardGame b = new BoardGame(); // Initialize a new board.
		
		// Set AGGRESSION and DEFENSE rates for the players.
		double[] AGG = new double[2];
		double[] DEF = new double[2];
		AGG[0] = player1.agg;
		AGG[1] = player2.agg;
		DEF[0] = player1.def;
		DEF[1] = player2.def;
		
		// Set depths for players.
		int[][] DEPTH = new int[2][];
		DEPTH[0] = player1.depths;
		DEPTH[1] = player2.depths;
		
		int turns   = 2; // Mutual turns counter.
		int depth   = 1; // Active depth.
		// Set random starting player;
		Random rand = new Random(); 		
		int player  = rand.nextInt(2);
		int mutualTurns;
		
		// Request and perform moves.
		while (!(b.isDraw() || b.isWinningState()))
		{
			mutualTurns = (int) Math.floor(turns / 2);
			// Get depth.
			depth = BoardGame.getTurnDepth(DEPTH[player], mutualTurns);
			
			b.requestMove(player, depth, AGG[player], DEF[player], mutualTurns, true);
			player = 1 - player;
			turns++;
		}
		
		EndStates state = b.reportEndState().endState; 
		
		mutualTurns = (int) Math.floor((turns - 1)/ 2);
		turnsAnalysis[matchNumber][gameNumber] = mutualTurns;
		
		System.out.println();
		System.out.println("GAME ENDED in " + state.getName() + " (after " + mutualTurns + " mutual turns).");
		System.out.println();
		System.out.println("========================================================");
		
		if (state == EndStates.Pl0) return player1.name;
		if (state == EndStates.Pl1) return player2.name;
		return "DRAW";
	}
	
	/** NOT CONFIGURABLES */
	public static int[][] turnsAnalysis; // Saves number of turns per game for analysis.
	public static long[][][] performanceAnalysis = new long[5][16][2]; // Saves a&b performance for analysis.
}
