package fivel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fivel.FivelTesting;
import fivel.enums.EndStates;
import fivel.enums.PiecePosition;
import fivel.enums.PieceStatus;
import fivel.utils.EndStateReport;
import fivel.utils.MoveNode;
import fivel.utils.Pair;

/**
 * The board game main model object for Fivel.
 * This object manages game initialization and controls the game flow.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class BoardGame
{
	private static final int NUMBER_OF_BOARD_BLOCKS = 9;  			// How many blocks are there in a Fivel's board.
	private static final int NUMBER_OF_BOARD_SLOTS  = 36; 			// How many slots are there in a Fivel's board.
	private static final int NUMBER_OF_FIVLETS      = 32; 			// How many Fivlets in a Fivel board.
	
	private static ArrayList<BoardBlock>    ms_boardBlocks;			// Set of board blocks.
	private static ArrayList<BoardSlot>     ms_boardSlots;			// Set of slots.
	private static ArrayList<Fivlet>	    ms_fivlets;				// Set of Fivlets.
	private static Set<BoardSlot>   	    ms_emptySlots;			// Current empty slot.
	private static BoardBlock 			    ms_emptyBlock;			// Current empty block.
	private static ArrayList<List<Integer>> ms_possibleTileSwaps;   // Possible tile swaps.
	
	public static double AGG; 										// Current player aggression rate.
	public static double DEF; 										// Current player defense rate.
	
	/* ****************************************************************************************
	 * INITIALIZATION. 
	 */
	
	/**
	 * Constructor.
	 * Initializes the board and settings.
	 */
	public BoardGame()
	{
		// First reset everything.
		ms_boardBlocks  	 = new ArrayList<BoardBlock>(NUMBER_OF_BOARD_BLOCKS);
		ms_boardSlots   	 = new ArrayList<BoardSlot>(NUMBER_OF_BOARD_SLOTS);
		ms_fivlets 	         = new ArrayList<Fivlet>(NUMBER_OF_FIVLETS);
		ms_emptySlots   	 = new HashSet<BoardSlot>();
		ms_emptyBlock  		 = null;
		ms_possibleTileSwaps = new ArrayList<List<Integer>>(NUMBER_OF_BOARD_BLOCKS);
		
		// Now build the board and the static data.
		for (int i = 0 ; i < NUMBER_OF_BOARD_BLOCKS ; i++)
		{
			BoardBlock block = new BoardBlock(i);
			block.setCurrentTile(new TileBlock());
			
			ms_boardBlocks.add(block);
		}		
		
		// Sets the middle slot as the empty one.
		setEmptyBoardBlock(ms_boardBlocks.get(4));
		ms_boardBlocks.get(4).setCurrentTile(null);
		
		// Add slots for each static block.
		int id = 0;
		for (BoardBlock block : ms_boardBlocks)
		{
			for (PiecePosition position : PiecePosition.values())
			{
				BoardSlot slot = new BoardSlot(id, block, position);
				ms_boardSlots.add(slot);
				id++;
			}
		}
		
		initializePossibleSwaps();		
		for (BoardSlot boardSlot : ms_boardSlots)
		{
			if (boardSlot.getBoardSlotPieceStatus() == PieceStatus.Void)
			{
				continue;
			}
			
			ms_emptySlots.add(boardSlot);			
		}
		
		// Initializes Fivlets.
		initializeFivlets();	
	}
	
	private static void initializePossibleSwapsForTile(int ... ids)
	{
		List<Integer> possibleTiles = new ArrayList<Integer>(ids.length);
		for (int id : ids)
		{
			possibleTiles.add(id);
		}
		
		ms_possibleTileSwaps.add(possibleTiles);
	}	
	
	/**
	 * Initializes statically all possible tile swaps.
	 * This is done statically in order to save calculation time while traversing the alpha beta tree.
	 */
	private static void initializePossibleSwaps()
	{
		initializePossibleSwapsForTile(1,3);
		initializePossibleSwapsForTile(0,2,4);
		initializePossibleSwapsForTile(1,5);
		initializePossibleSwapsForTile(0,4,6);
		initializePossibleSwapsForTile(1,3,5,7);
		initializePossibleSwapsForTile(2,4,8);
		initializePossibleSwapsForTile(3,7);
		initializePossibleSwapsForTile(4,6,8);
		initializePossibleSwapsForTile(5,7);		
	}
	
	/**
	 * Initializes statically all the Fivlets and their corresponding slots.
	 */
	private static void initializeFivlets()
	{
		createAndAddFivlet(0, 0,1,4,5,8);
		createAndAddFivlet(1, 1,4,5,8,9);
		createAndAddFivlet(2, 3,2,7,6,11);
		createAndAddFivlet(3, 2,7,6,11,10);
		createAndAddFivlet(4, 12,13,16,17,20);
		createAndAddFivlet(5, 13,16,17,20,21);
		createAndAddFivlet(6, 15,14,19,18,23);
		createAndAddFivlet(7, 14,19,18,23,22);
		createAndAddFivlet(8, 24,25,28,29,32);
		createAndAddFivlet(9, 25,28,29,32,33);
		createAndAddFivlet(10, 27,26,31,30,35);
		createAndAddFivlet(11, 26,31,30,35,34);
		createAndAddFivlet(12, 0,3,12,15,24);
		createAndAddFivlet(13, 3,12,15,24,27);
		createAndAddFivlet(14, 1,2,13,14,25);
		createAndAddFivlet(15, 2,13,14,25,26);
		createAndAddFivlet(16, 4,7,16,19,28);
		createAndAddFivlet(17, 7,16,19,28,31);
		createAndAddFivlet(18, 5,6,17,18,29);
		createAndAddFivlet(19, 6,17,18,29,30);
		createAndAddFivlet(20, 8,11,20,23,32);
		createAndAddFivlet(21, 11,20,23,32,35);
		createAndAddFivlet(22, 9,10,21,22,33);
		createAndAddFivlet(23, 10,21,22,33,34);
		createAndAddFivlet(24, 0,2,16,18,32);
		createAndAddFivlet(25, 2,16,18,32,34);
		createAndAddFivlet(26, 1,7,17,23,33);
		createAndAddFivlet(27, 3,13,19,29,35);
		createAndAddFivlet(28, 9,11,17,19,25);
		createAndAddFivlet(29, 11,17,19,25,27);
		createAndAddFivlet(30, 8,6,16,14,24);
		createAndAddFivlet(31, 10,20,18,28,26);	
	}
	
	/**
	 * Adds a single Fivlet into the data structure.
	 * 
	 * @param id Fivlet id.
	 * @param slotsIDs 5 arguments for the Fivlet's slots' ids.
	 */
	private static void createAndAddFivlet(int id, int ... slotsIDs)
	{
		List<BoardSlot> slots = new ArrayList<BoardSlot>(5);
		for (int slot : slotsIDs)
		{			
			slots.add(ms_boardSlots.get(slot));
		}
		ms_fivlets.add(new Fivlet(id, slots));
	}
	
	/* ****************************************************************************************
	 * BOARD FUNCTIONS.
	 */
	
	/**
	 * Adds a slot to the empty slots list.
	 * 
	 * @param boardSlot The slot to add.
	 */
	public static void addEmptyBoardSlotToEmptySlots(BoardSlot boardSlot)
	{
		ms_emptySlots.add(boardSlot);
	}
	
	/**
	 * Removes a slot from the empty slot list.
	 * 
	 * @param boardSlot The slot to remove.
	 */
	public static void removeBoardSlotFromEmptySlots(BoardSlot boardSlot)
	{
		ms_emptySlots.remove(boardSlot);
	}
	
	/**
	 * Sets the empty block on the board.
	 * 
	 * @param block Which block to set as empty.
	 */
	public static void setEmptyBoardBlock(BoardBlock block)
	{
		ms_emptyBlock = block;
	}

	/**
	 * Returns the board score and updates its Fivlets status.
	 * 
	 * @param player Calling player.
	 * @return the board current heuristic score.
	 */
	private double getAndUpdateBoardScore(int player)
	{
		double score = 0;
		
		for (Fivlet fivlet : ms_fivlets)
		{
			score += fivlet.getAndUpdateScore(player);
		}
		
		return score;
	}	

	/**
	 * @return Whether the current board is a terminal board (a player has won or no more empty slots available).
	 */
	private boolean isTerminal()
	{
		return (isWinningState()) || ms_emptySlots.size() == 0;
	}	

	/**
	 * @return Whether the current board is in draw state (no more empty slots and a nobody has a full Fivlet).
	 */
	public boolean isDraw()
	{
		return ms_emptySlots.isEmpty();
	}
	
	/**
	 * Checks quickly if there's a winning fivlet on the board.
	 * 
	 * @return True if there's a full Fivlet (victory state).
	 */
	public boolean isWinningState()
	{
		for (Fivlet fivlet : ms_fivlets)
		{
			// If one Fivlet is full stop and return true.
			if (fivlet.isFull() != 0)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * API call from the game interface - performing a move by the player
	 * putting piece on i,j index and moving tile tile to the empty place.
	 * Also checks what is the game status atfer performing.
	 * 
	 * @param player The performing player.
	 * @param i Slot I coordinate.
	 * @param j Slot J coordinate.
	 * @param tile Moved tile.
	 * 
	 * @return An EndStateReport object (end state and winning fivlets if exist).
	 */
	public EndStateReport performMove(int player, int i, int j, int tile)
	{
		player = translatePlayer(player);
		BoardSlot slot = ms_boardSlots.get(translateCoords(i, j));
		BoardBlock block = ms_boardBlocks.get(tile);
		
		// Perform move.
		BoardMove move = new BoardMove(block, ms_emptyBlock, slot ,player);
		move.performMove();
		getAndUpdateBoardScore(player);
		
		return reportEndState();
	}
	
	/**
	 * @return A report of the end state of the board.
	 */
	public EndStateReport reportEndState()
	{
		boolean pl0 = false;
		boolean pl1 = false;
		Fivlet fivlet0 = null;
		Fivlet fivlet1 = null;
		List<Pair> list0 = new ArrayList<Pair>(5);
		List<Pair> list1 = new ArrayList<Pair>(5);
		
		// Check and set end state.
		for (Fivlet fivlet : ms_fivlets)
		{
			if (fivlet.isFull() == PieceStatus.Red.getID())
			{
				pl0 = true;
				// Save a winning Fivlet.
				fivlet0 = fivlet;
			}
			else if (fivlet.isFull() == PieceStatus.Blue.getID()) 
			{
				pl1 = true;
				// Save a winning Fivlet.
				fivlet1 = fivlet;
			}
		}
		
		EndStateReport report = null;
		
		// Cases by priorities.
		
		// Double draw.
		if (pl0 && pl1)
		{
			for (BoardSlot s : fivlet0.getBoardSlots())
			{
				list0.add(translateIndex(s.getID()));
			}
			for (BoardSlot s : fivlet1.getBoardSlots())
			{
				list1.add(translateIndex(s.getID()));
			}
			report = new EndStateReport(EndStates.DoubleDraw, list0, list1);
		}
		// Player 0 won.
		else if (pl0)
		{
			for (BoardSlot s : fivlet0.getBoardSlots())
			{
				list0.add(translateIndex(s.getID()));
			}
			report = new EndStateReport(EndStates.Pl0, list0);
		}
		// Player 1 won.
		else if (pl1)
		{
			for (BoardSlot s : fivlet1.getBoardSlots())
			{
				list0.add(translateIndex(s.getID()));
			}
			report = new EndStateReport(EndStates.Pl1, list0);
		}
		// Draw due to full board.
		else if (isDraw())
		{
			report = new EndStateReport(EndStates.Draw);
		}
		// Nothing happend.
		else
		{
			report = new EndStateReport(EndStates.None);
		}
	
		return report;
	}
	
	/**
	 * The alpha beta algorithm.
	 * 
	 * @param depth Remaining depth of search.
	 * @param alpha Alpha value.
	 * @param beta Beta value.
	 * @param player The current player.
	 * @param callingPlayer The player who requested the move (the max player).
	 * @param parent The parent move node.
	 * @param firstMove Whether this is the first depth.
	 * @return The returning score value.
	 */
	private double alphaBeta(int depth, double alpha, double beta, int currentPlayer, int callingPlayer, BoardMove parent, boolean firstMove)
	{	
		/* TERMINAL NODE */
		// If reached maximum depth or reached a terminal node, return the board's score.
		if ((depth == 0) || (isTerminal()))
		{
			return getAndUpdateBoardScore(callingPlayer);
		}
		
		HashSet<BoardSlot> possibleEmptySlots = new HashSet<BoardSlot>(ms_emptySlots);
		
		/* MAX PLAYER */
		if (currentPlayer == callingPlayer)
		{
			boolean prune = false;
			
			// Generates all possible moves.
			for (BoardSlot emptySlot : possibleEmptySlots)
			{		
				for (int possibleTileSwapsId : ms_possibleTileSwaps.get(ms_emptyBlock.getID()))			
				{
					BoardMove move = new BoardMove(ms_boardBlocks.get(possibleTileSwapsId), ms_emptyBlock, emptySlot, currentPlayer);
					
					// Perform move.
					move.performMove();
					
					// Update board score to see if it's a terminal next depth.
					getAndUpdateBoardScore(currentPlayer);
					
					// Alpha beta call.
					double eval = alphaBeta(depth-1, alpha, beta, -currentPlayer, callingPlayer, move, false);
					alpha = Math.max(alpha, eval);
					
					// Undo.
					move.undoMove();
					
					// Store.
					if (firstMove) parent.childMoves.add(new MoveNode(eval, move));
					
					// Prune?
					if (beta <= alpha)
					{
						prune = true;
						break;
					}					
				}	
				// Prune outside the nested loop...
				if (prune) break;
			}		
			return alpha;
		}		
		
		/* MIN PLAYER */
		else
		{
			boolean prune = false;
			// Generates all possible moves.
			for (BoardSlot emptySlot : possibleEmptySlots)
			{		
				for (int possibleTileSwapsId : ms_possibleTileSwaps.get(ms_emptyBlock.getID()))			
				{
					BoardMove move = new BoardMove(ms_boardBlocks.get(possibleTileSwapsId), ms_emptyBlock, emptySlot, currentPlayer);		

					// Perform move.
					move.performMove();
					
					// Update board score to see if it's a terminal next depth.
					getAndUpdateBoardScore(currentPlayer);
					
					// Alpha beta call.
					double eval = alphaBeta(depth-1, alpha, beta, -currentPlayer, callingPlayer, move, false);
					beta = Math.min(beta, eval);
					
					// Undo.
					move.undoMove();
					
					// Prune?
					if (beta <= alpha)
					{
						prune = true;
						break;
					}	
				}	
				// Prune outside the nested loop...
				if (prune) break;
			}
			return beta;
		}
	}
	
	/**
	 * Method for the CPU player for getting the best possible (heuristics) move.
	 * It creates all the first possible moves for him and then calls the alpha beta
	 * algorithm to see which of them is the best.
	 * 
	 * @param player - The player doing the move.
	 * @param depth  - The depth of the alpha beta.
	 * @param agg The player's aggression rate.
	 * @param def The player's defense rate.
	 * @param turn Current mutual turn for testing.
	 * @param test True if in testing mode. In testing mode the move is performed and printed.
	 * 
	 * @return A list of the arguments that represents the move (i index, j index, tile to move to the empty place)
	 */
	public List<Integer> requestMove(int player, int depth, double agg, double def, int turn, boolean test)
	{	
		// Save system time for analytics.
		long time =  System.currentTimeMillis();
		
		// Saves the player ratings statically so the fivlets can access them.
		AGG = agg;
		DEF = def;
		
		// Translates the player from the front end notation to the back end one.
		player = translatePlayer(player);
		
		// Initializes a list of the result.
		List<Integer> result = new ArrayList<Integer>(3);
		BoardMove rootMove   = new BoardMove();
		rootMove.childMoves  = new ArrayList<MoveNode>(); // Initialize the map.
		alphaBeta(depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, player, player, rootMove, true);
		
		// Get best move.
		int size 		   = rootMove.childMoves.size();
		double max  	   = Double.NEGATIVE_INFINITY;
		BoardMove bestMove = null;
		for (int i = 0; i < size; i++)
		{
			MoveNode node = rootMove.childMoves.get(i);
			if (node.score > max)
			{
				bestMove = node.move;
				max = node.score;
			}
		}
		
		// If in test mode, perform, save analysis and print the move.
		if (test)
		{
			long takenTime = (System.currentTimeMillis() - time);
			FivelTesting.performanceAnalysis[depth-1][turn-1][0] += takenTime;
			FivelTesting.performanceAnalysis[depth-1][turn-1][1]++;
			bestMove.performMove();
			System.out.println(bestMove.toString() + " [DEPTH: " + depth + " in " + takenTime + " ms]");
		}
		
		// Builds parameters for client.
		Pair pair = translateIndex(bestMove.getSlot().getID());
		result.add(pair.i);
		result.add(pair.j);
		result.add(bestMove.getSourceBlock().getID());
		
		return result;
	}
	
	/* ****************************************************************************************
	 * UTILITIES.
	 */
	
	/**
	 * Translates from matrix indices (used in the client) to singular slot index (used in the backend).
	 * 
	 * @param i I coordinate (column).
	 * @param j J coordinate (row).
	 * @return The slot id.
	 */
	public static int translateCoords(int i, int j)
	{
		int slot;
		int slotAdd = 0;
		int tileRow = (int) Math.floor(j / 2);
		int tileCol = (int) Math.floor(i / 2);
		int slotRow = j % 2;
		int slotCol = i % 2;
		int tile = tileRow * 3 + tileCol;
		
		if (slotRow == 0 && slotCol == 0) slotAdd = 0;
		else if (slotRow == 0 && slotCol == 1) slotAdd = 1;
		else if (slotRow == 1 && slotCol == 1) slotAdd = 2;
		else if (slotRow == 1 && slotCol == 0) slotAdd = 3;
		
		// Writing all possibilities above for better readability of code.
		slot = tile * 4 + slotAdd;
		
		
		return slot;
	}
	
	/**
	 * Translate from singular slot ID to matrix indices (used in the frontend).
	 * 
	 * @param index A backend slot index.
	 * @return A translated coordinate.
	 */
	public static Pair translateIndex(int index)
	{
		int tile    = (int) Math.floor(index / 4);
		int tileRow = (int) Math.floor(tile / 3);
		int tileCol = (int) tile % 3;
		
		int basicSlot = index - (tile * 4);
		int slotRow = 0;
		int slotCol = 0;
		
		if (basicSlot == 0) { slotRow = 0; slotCol = 0;}
		else if (basicSlot == 1) { slotRow = 0; slotCol = 1;}
		else if (basicSlot == 2) { slotRow = 1; slotCol = 1;}
		else if (basicSlot == 3) { slotRow = 1; slotCol = 0;}
		// Writing all possibilities above for better readability of code.
		
		int i = (tileCol * 2) + slotCol; 
		int j = (tileRow * 2) + slotRow;
		
		return new Pair(i, j);
	}
	
	/**
	 * Translates a player from front end to back end notation.
	 * 
	 * @param Frontend player id.
	 * @return Backend player id.
	 */
	public int translatePlayer(int player)
	{
		if (player == 0) return 1;
		return -1;
	}
	
	/**
	 * @param depths An array of depths according to turn.
	 * @param turn The current mutual turn.
	 * @return A depth according to mutual turn.
	 */
	public static int getTurnDepth(int[] depths, int turn)
	{
		if (turn <= depths.length) return depths[turn - 1];
		return depths[depths.length - 1];
	}
}
