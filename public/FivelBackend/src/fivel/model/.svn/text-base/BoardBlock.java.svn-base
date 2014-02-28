package fivel.model;

import java.util.HashSet;

/**
 * A board block object.
 * There are 9 blocks in Fivel, each static and doesn't move as the game progresses.
 * A block can occupy a tile or be empty. 
 * 
 * Each block knows the slots it contains, which don't change throughout the game. 
 * They are added into it at the beginning of the game.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class BoardBlock extends FivelObject
{
	private TileBlock          m_currentTile = null;                      // Which tile this block occupies.
	private HashSet<BoardSlot> m_boardSlots  = new HashSet<BoardSlot>(4); // Which static slots this block contains.

	/**
	 * Constructor for an empty Block.
	 * 
	 * @param boardID The block id (0-8).
	 */
	public BoardBlock(int boardID)
	{		
		this(boardID, new TileBlock());
	}
	
	/**
	 * Constructor.
	 * 
	 * @param boardID The block id (0-8).
	 * @param currentTile A tile object this block contains at the start of the game.
	 */
	public BoardBlock(int boardID, TileBlock currentTile)
	{
		super(boardID);
		if(getID() != 4)
		{
			m_currentTile = currentTile;
		}
	}
	
	/**
	 * @return The tile this block occupies (null if empty).
	 */
	public TileBlock getCurrentTile()
	{
		return m_currentTile;
	}
	
	/**
	 * Sets a new tile into this block (by sliding a tile).
	 * 
	 * @param tile The new tile to set here.
	 */
	public void setCurrentTile(TileBlock tile)
	{
		m_currentTile = tile;
	}
	
	/**
	 * Adds a slot to this block so it knows it throughout the game.
	 * 
	 * @param slot The slot to add.
	 */
	public void updateSlotsSet(BoardSlot slot)
	{
		m_boardSlots.add(slot);
	}
	
	/**
	 * @return The set of slots this block contains.
	 */
	public HashSet<BoardSlot> getBlockBoardSlots()
	{
		return m_boardSlots;
	}
}
