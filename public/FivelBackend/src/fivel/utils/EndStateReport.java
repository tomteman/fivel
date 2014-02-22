package fivel.utils;

import java.util.List;

import fivel.enums.EndStates;

/**
 * Wraps required data for end state after a move is performed.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class EndStateReport
{
	public EndStates  endState; // The end state reported.
	public List<Pair> list1;    // List of slots coordinates for one player if won.
	public List<Pair> list2;    // List of slots coordinates for two players for double draw.
	
	/**
	 * Constructor.
	 * 
	 * @param endState The end state reported.
	 */
	public EndStateReport(EndStates endState)
	{
		this.endState = endState;
		this.list1    = null;
		this.list2    = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param endState The end state reported.
	 * @param winningFivlet1 List of slots coordinates for one player if won.
	 */
	public EndStateReport(EndStates endState, List<Pair> winningFivlet1)
	{
		this.endState = endState;
		if (winningFivlet1 != null) this.list1    = winningFivlet1;
		this.list2    = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param endState The end state reported.
	 * @param winningFivlet1 List of slots coordinates for one player if won.
	 * @param winningFivlet2 List of slots coordinates for two players for double draw.
	 */
	public EndStateReport(EndStates endState, List<Pair> winningFivlet1, List<Pair> winningFivlet2)
	{
		this.endState = endState;
		if (winningFivlet1 != null) this.list1    = winningFivlet1;
		if (winningFivlet2 != null) this.list2    = winningFivlet2;
	}
}
