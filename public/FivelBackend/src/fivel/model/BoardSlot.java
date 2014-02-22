package fivel.model;

import java.util.ArrayList;
import java.util.List;

import fivel.enums.PiecePosition;
import fivel.enums.PieceStatus;

/**
 * A slot object.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class BoardSlot extends FivelObject
{
	private final BoardBlock PARENT_BLOCK;         // The slot static parent block.
	private final PiecePosition POSITION_ON_TILE;  // The slot static position on the block.
	
	private List<Fivlet> m_parentWinningFive = new ArrayList<Fivlet>(); // List of all Fivlets this slot is in.
	
	/**
	 * Constructor.
	 * 
	 * @param slotID The slot's ID.
	 * @param block The slot's block.
	 * @param position The slot's block position.
	 */
	public BoardSlot(int slotID, BoardBlock block, PiecePosition position)
	{
		super(slotID);
		PARENT_BLOCK     = block;
		POSITION_ON_TILE = position;	
		
		block.updateSlotsSet(this);
	}
	
	/**
	 * @return The slot's status.
	 */
	public PieceStatus getBoardSlotPieceStatus()
	{
		TileBlock currentTile = PARENT_BLOCK.getCurrentTile();
		
		// If tile is null, this slot is void.
		if (currentTile == null)
		{
			return PieceStatus.Void;			
		}
		
		return currentTile.getTileSlot(POSITION_ON_TILE).getTileSlotPieceStatus();
	}	
	
	/**
	 * @return The block this slot is in.
	 */
	public BoardBlock getContainingBlock()
	{		
		return PARENT_BLOCK;
	}
	
	/**
	 * Sets a new status for a slot.
	 * 
	 * @param status New status for the slot.
	 * @param player Player id in case a piece is inserted.
	 * @throws NullPointerException
	 */
	public void setTileSlotPieceStatus(PieceStatus status, int player) throws NullPointerException
	{		
		TileBlock currentTile = PARENT_BLOCK.getCurrentTile();
		if (currentTile == null)
		{
			throw new NullPointerException("setTileSlotPieceStatus: Cannot set piece where there is no TileSlot.");
		}
		
		PieceStatus currentStatus = currentTile.getTileSlot(POSITION_ON_TILE).getTileSlotPieceStatus();
		
		// If the slot was empty then now it's not - update the static list in the
		// board game object
		if (currentStatus == PieceStatus.Empty)
		{
			BoardGame.removeBoardSlotFromEmptySlots(this);
		}
		
		currentTile.getTileSlot(POSITION_ON_TILE).setTileSlotPieceStatus(status);
		
		// In case updating to empty when undoing a move in the alpha bet - add this slot to the empty slots.
		if (status == PieceStatus.Empty)
		{
			BoardGame.addEmptyBoardSlotToEmptySlots(this);
		}
	}	
	
	/**
	 * Adds a Fivlet to this slot at the beginning of the game.
	 * @param parent
	 */
	public void setParentWinningFive(Fivlet parent)
	{
		m_parentWinningFive.add(parent);
	}
	
	/**
	 * @return A list of all the Fivlets this slot is in.
	 */
	public List<Fivlet> getContainerFivlet()
	{
		return m_parentWinningFive;
	}
	
	
	
	
}
