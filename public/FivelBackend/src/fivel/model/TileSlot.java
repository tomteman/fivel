package fivel.model;

import fivel.enums.PieceStatus;

/**
 * A tile slot object.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class TileSlot
{
	private PieceStatus m_pieceStatus; // Slot status.

	/**
	 * Constructor.
	 * 
	 * @param status The piece status.
	 */
	public TileSlot(PieceStatus status)
	{
		m_pieceStatus = status;
	}
	
	/**
	 * Constructor.
	 * Empty slot.
	 */
	public TileSlot()
	{
		this(PieceStatus.Empty);		
	}
	
	/**
	 * @return the slot's status.
	 */
	public PieceStatus getTileSlotPieceStatus()
	{
		return m_pieceStatus;
	}
	
	/**
	 * Sets the slot's status.
	 * 
	 * @param status The new slot's status.
	 */
	public void setTileSlotPieceStatus(PieceStatus status)
	{
		m_pieceStatus = status;
	}
}
