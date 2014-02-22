package fivel.utils;

import fivel.model.BoardMove;

/**
 * A move node object for the alpha beta.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class MoveNode
{
	public double score; // The move's score.
	public BoardMove move; // The move.
	
	public MoveNode(double score, BoardMove move)
	{
		this.score = score;
		this.move  = move;
	}
}
