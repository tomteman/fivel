package fivel.model;

import fivel.enums.PiecePosition;

/**
 * A tile object.
 * There are 8 tiles in Fivel, each can move and change its position to an empty BoardBlock.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class TileBlock
{
	private TileSlot m_upperLeft;   // The upper left corner slot.
	private TileSlot m_upperRight;  // The upper right corner slot.
	private TileSlot m_bottomLeft;  // The bottom left corner slot.
	private TileSlot m_bottomRight; // The bottom right corner slot.
	
	/**
	 * Constructor.
	 */
	public TileBlock()
	{
		// Construct the tile slots.
		m_upperLeft   = new TileSlot();
		m_upperRight  = new TileSlot();
		m_bottomLeft  = new TileSlot();
		m_bottomRight = new TileSlot();
	}
	
	/**
	 * @param position A slot position.
	 * @return The slot object corresponding to the given position.
	 */
	public TileSlot getTileSlot(PiecePosition position)
	{
		switch (position)
		{
			case UpperLeft:   return getUpperLeftTileSlot();
			case UpperRight:  return getUpperRightTileSlot();
			case BottomLeft:  return getBottomLeftTileSlot();
			case BottomRight: return getBottomRightTileSlot();
			
			default:
				return null;
		}
	}
	
	/**
	 * @return The upper left slot object.
	 */
	private TileSlot getUpperLeftTileSlot()
	{
		return m_upperLeft;		
	}
	
	/**
	 * @return The upper right slot object.
	 */
	private TileSlot getUpperRightTileSlot()
	{
		return m_upperRight;		
	}
	
	/**
	 * @return The bottom left slot object.
	 */
	private TileSlot getBottomLeftTileSlot()
	{
		return m_bottomLeft;		
	}
	
	/**
	 * @return The bottom right slot object.
	 */
	private TileSlot getBottomRightTileSlot()
	{
		return m_bottomRight;		
	}
}
