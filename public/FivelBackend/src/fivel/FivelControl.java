package fivel;

import java.util.List;
import java.util.Random;

import fivel.config.Difficulty;
import fivel.enums.EndStates;
import fivel.model.BoardGame;
import fivel.utils.EndStateReport;

/**
 * The game class for Fivel.
 * The class holds all nessecary components for game logic and AI.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class FivelControl
{
	private FivelApplet appletInterface;  // Reference to the external interface in order to send back moves.
	public static BoardGame ms_boardGame; // Reference to the game board structure.
	
	/**
	 * Constructor.
	 */
	public FivelControl(FivelApplet appletInterface)
	{
		this.appletInterface = appletInterface;
	}
	
	/**
	 * Resets the board game to its initial state and returns a random starting player.
	 */
	public void startGame()
	{
		ms_boardGame = new BoardGame();
		
		// Randomize a starting player.
		Random rand = new Random();		
		appletInterface.returnStartingPlayer(rand.nextInt(2));
	}
	
	/**
	 * Performs a move on the board and returns the game's state to the frontend.
	 * 
	 * @param player The number of the player performing the move.
	 * @param i Horizontal coordinate where a piece was placed.
	 * @param j Horizontal coordinate where a piece was placed.
	 * @param tile The number of the tile that was moved.
	 */
	public void performMove(int player, int i, int j, int tile)
	{
		// Perform the move on the data structure.
		EndStateReport report = ms_boardGame.performMove(player, i, j, tile);
		
		// Draw or game is not in an end state.
		if (report.endState == EndStates.None || report.endState == EndStates.Draw)
		{
			appletInterface.confirmMove(report.endState.getID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		}
		// Double draw - As in, both players got full Fivlets.
		else if (report.endState == EndStates.DoubleDraw)
		{
			appletInterface.confirmMove(EndStates.DoubleDraw.getID(), 
					report.list1.get(0).i, report.list1.get(0).j,
					report.list1.get(1).i, report.list1.get(1).j,
					report.list1.get(2).i, report.list1.get(2).j,
					report.list1.get(3).i, report.list1.get(3).j,
					report.list1.get(4).i, report.list1.get(4).j,
					report.list2.get(0).i, report.list2.get(0).j,
					report.list2.get(1).i, report.list2.get(1).j,
					report.list2.get(2).i, report.list2.get(2).j,
					report.list2.get(3).i, report.list2.get(3).j,
					report.list2.get(4).i, report.list2.get(4).j);
		}
		// Victory for a player.
		else
		{
			appletInterface.confirmMove(report.endState.getID(),
					report.list1.get(0).i, report.list1.get(0).j,
					report.list1.get(1).i, report.list1.get(1).j,
					report.list1.get(2).i, report.list1.get(2).j,
					report.list1.get(3).i, report.list1.get(3).j,
					report.list1.get(4).i, report.list1.get(4).j,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		}
	}
	
	/**
	 * Generates an AI move and returns it to the frontend. 
	 * 
	 * @param player The number of the player to generate a move to.
	 * @param diff The difficulty level of the requesting player.
	 * @param turns The current mutual turn to choose depth.
	 */
	public void requestMove(int player, int diff, int turns)
	{
		Difficulty difficulty = FivelDifficulties.LEVELS[diff];
		
		// Request an AI move and returns a list of the required parameters for the front end.
		List<Integer> returnedMove = ms_boardGame.requestMove(player, BoardGame.getTurnDepth(difficulty.depths, turns), difficulty.agg, difficulty.def, turns, false);
		appletInterface.returnMove(player, returnedMove.get(0), returnedMove.get(1), returnedMove.get(2));
	}
}
