package fivel.model;

import java.util.ArrayList;
import java.util.List;

import fivel.enums.PieceStatus;

/**
 * A Fivlet object.
 * There are 32 Fivlets in Fivel, each is a set of slots that form a winning 5-pieces.
 * They are initialized statically at the beginning of the game. 
 * For more information about Fivlets, read the presentation. 
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class Fivlet extends FivelObject
{
	private static final int NUMBER_OF_SLOTS = 5;    // How many slots a Fivlet contains.	
	public  static final int BIG_CONST       = 5000; // Big const for winning Fivlets. 
	// The BIG_CONST is higher than 4^max(AGGRESSION, DEFENSE) for any player so not full Fivlets won't pass this const. 
	
	private List<BoardSlot> m_boardSlots = new ArrayList<BoardSlot>(NUMBER_OF_SLOTS); // The set of slots this Fivlet makes.
	
	private int fullFivletFor;         // Whether this Fivlet is full (1 or -1) or not (0).
	
	/**
	 * Constructor.
	 * 
	 * @param id ID of Fivlet.
	 * @param boardSlots A set of slots this Fivlet contains. 
	 */
	public Fivlet(int id, List<BoardSlot> boardSlots)
	{
		super(id);
		
		// Sets the slots of this Fivlet and for each slot sets its Fivlet parent as this.
		for (BoardSlot boardSlot : boardSlots)
		{
			m_boardSlots.add(boardSlot);
			boardSlot.setParentWinningFive(this);
		}
	}
	
	/**
	 * Returns score and updates fivlet full status.
	 * 
	 * @param Player to get the score for.
	 * @return The Fivlet's score.
	 */
	public double getAndUpdateScore(int player)
	{
		int pl0 = 0;
		int pl1 = 0;		
		
		// For each slot in the Fivlet...
		for(BoardSlot boardSlot : m_boardSlots)
		{
			PieceStatus slotStatus = boardSlot.getBoardSlotPieceStatus();
			
			// If pl0, count up. 
			if (slotStatus == PieceStatus.Red)
			{
				pl0++;
				// If pl1's, stop the counting, the score will be zero anyway.
				if (pl1 > 0)
				{
					fullFivletFor = 0;
					return 0;
				}
			}
			
			// If pl1, count his up.
			else if (slotStatus == PieceStatus.Blue)
			{
				pl1++;	
				
				// If pl0's, stop the counting, the score will be zero anyway.
				if (pl0 > 0)
				{
					fullFivletFor = 0;
					return 0;
				}
			}			
		}
		
		// After the counting, check for conditions.
		
		// If nobody has pieces here the score is 0.
		if (pl0 == 0 && pl1 == 0)
		{
			fullFivletFor = 0;
			return 0; 
		}
		
		// If player 0 something without opponent interference, score this for me postively.  
		if (pl0 > 0)
		{	
			// Full Fivlet.
			if (pl0 == 5) 
			{
				fullFivletFor = PieceStatus.Red.getID();
				return player > 0 ? BIG_CONST : -BIG_CONST;
			}
			// Rate.
			else
			{
				fullFivletFor = 0;
				return player > 0 ? Math.pow(pl0, BoardGame.AGG) : -Math.pow(pl0, BoardGame.DEF);
			}
		}
		
		// If opponent has something without my interference, score this for me negatively. 
		if (pl1 == 5)
		{
			// Full Fivlet.
			fullFivletFor = PieceStatus.Blue.getID();
			return player > 0 ? -BIG_CONST : BIG_CONST;
		}
		// Rate.
		else
		{
			fullFivletFor = 0;
			return player > 0 ? -Math.pow(pl1, BoardGame.DEF) : Math.pow(pl1, BoardGame.AGG);
		}
	}
	
	/**
	 * @return Whether this Fivlet is winning or not and for which player.
	 */
	public int isFull()
	{
		return fullFivletFor;
	}
	
	/**
	 * @return The slots set of this Fivlet.
	 */
	public List<BoardSlot> getBoardSlots()
	{
		return m_boardSlots;		
	}
}
