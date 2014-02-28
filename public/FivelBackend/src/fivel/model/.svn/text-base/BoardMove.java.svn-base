package fivel.model;

import java.util.ArrayList;

import fivel.enums.PieceStatus;
import fivel.utils.MoveNode;
import fivel.utils.Pair;

/**
 * Describes a move in Fivel (both piece placement and tile sliding).
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class BoardMove
{
	private BoardBlock m_src   = null; // Source block the moved tile was in.
	private BoardBlock m_dst   = null; // Target block the moved tile is moved to.
	private BoardSlot m_slotID = null; // The slot ID that the piece was placed in (after the tile slide!).
	private PieceStatus m_status;      // The new status for the slot ID.
	private int m_player;              // The player making this move.
	
	// Map of all children nodes for alpha beta. 
	// We save all the nodes for testing reasons and to get the first best move.
	// We used an array list and not a map because of debugging and problems with repeating keys.
	public ArrayList<MoveNode> childMoves = null;
	
	/**
	 * Constructs an empty Board move for as an Alpha beta root.
	 */
	public BoardMove() {}
	
	/**
	 * Constructor.
	 * 
	 * @param src Source block the moved tile was in.
	 * @param dst Target block the moved tile is moved to.
	 * @param slotID The slot ID that the piece was placed in.
	 * @param player The player making this move.
	 */
	public BoardMove(BoardBlock src, BoardBlock dst, BoardSlot slotID, int player)
	{
		m_src    = src;
		m_dst    = dst;
		m_slotID = slotID;
		m_status = player > 0 ? PieceStatus.Red : PieceStatus.Blue;
		m_player = player;
	}
	
	/**
	 * @return The source block of this move.
	 */
	public BoardBlock getSourceBlock()
	{
		return m_src;
	}
	
	/**
	 * @return The destination block of this move.
	 */
	public BoardBlock getDestBlock()
	{
		return m_dst;
	}
	
	/**
	 * @return The slot id of this move.
	 */
	public BoardSlot getSlot()
	{
		return m_slotID;
	}
	
	/**
	 * Performs the move on the board - first puts the slot and then swaps the tiles.
	 */
	public void performMove()
	{
		m_slotID.setTileSlotPieceStatus(m_status, m_player);
		swapTiles(m_src, m_dst);
	}
	
	/**
	 * Undos the move from the board.
	 */
	public void undoMove()
	{
		swapTiles(m_dst, m_src);
		
		if (m_slotID.getContainingBlock().getCurrentTile() == null)
		{
			m_slotID.setTileSlotPieceStatus(PieceStatus.Void, m_player);
		}
		else
		{			
			m_slotID.setTileSlotPieceStatus(PieceStatus.Empty, m_player);
		}
	}
	
	/**
	 * Swapping two tiles and updating the board accordingly .
	 * 
	 * @param source A source block.
	 * @param dest A target block.
	 * @return true if swap was performed and false otherwise.
	 */
	private boolean swapTiles(BoardBlock source, BoardBlock dest)
	{
		// Defensive checks.
		if (source.getCurrentTile() == null) return false;
		if (dest.getCurrentTile() != null) return false;
		
		if (!legalTilesSwap(source.getID(), dest.getID()))
		{
			return false;
		}
		
		// Swap the tiles.
		dest.setCurrentTile(source.getCurrentTile());
		source.setCurrentTile(null);
		
		// All the slots from the tile that was moved should all be empty.
		for (BoardSlot slot : source.getBlockBoardSlots())
		{
			BoardGame.removeBoardSlotFromEmptySlots(slot);
		}
		
		for (BoardSlot slot : dest.getBlockBoardSlots())
		{
			// Update empty slots on the board.
			if (slot.getBoardSlotPieceStatus() != PieceStatus.Empty)
			{
				BoardGame.removeBoardSlotFromEmptySlots(slot);
			}
			else
			{
				BoardGame.addEmptyBoardSlotToEmptySlots(slot);
			}
		}
		
		// Set the moved tile's block to be empty.
		BoardGame.setEmptyBoardBlock(source);
		
		return true;
	}	
	
	/**
	 * Checks if the tile swap is legal.
	 *  
	 * @param sourceID Source tile id.
	 * @param destID Destination tile id.
	 * @return true if legal false otherwise.
	 */
	private boolean legalTilesSwap(int sourceID, int destID)
	{
		int distance = Math.abs(sourceID - destID);
		
		if ((sourceID % 3 == destID % 3 ) && (distance == 3))
		{
			return true;
		}
		if (((Math.abs(sourceID % 3 - destID % 3)) == 1) && (distance == 1))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * @return String represenation of the move for testing purposes.
	 */
	@Override
	public String toString()
	{
		Pair cord = BoardGame.translateIndex(m_slotID.m_id); 
		return "Player " + (m_player > 0 ? 0 : 1) + " placed a piece on (" + cord.i + "," + cord.j + ") and moved tile from " + m_src.m_id + " to " + m_dst.m_id + "."; 
	}
	
}