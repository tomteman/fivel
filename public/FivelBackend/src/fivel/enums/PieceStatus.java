package fivel.enums;

/**
 * Enumerator for slot status.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public enum PieceStatus
{
	Void(Integer.MIN_VALUE, "void"), // No tile at this slot index.
	Empty(0,"empty"),                // There's a tile but the slot in it is empty.
	Red(1,"red"),                    // Filled by player 0 (1) piece.
	Blue(-1, "blue");                // Filled by player 1 (-1) piece.
	
	private int m_id;                // Id.
	private String m_readableName;   // String name.
	
	/**
	 * Constructor.
	 * 
	 * @param id Enumerator id.
	 * @param readableName String name.
	 */
	PieceStatus(int id, String readableName)
	{
		m_id = id;
		m_readableName = readableName;
	}
	
	/**
	 * @return the ID of the enumerator.
	 */
	public int getID()
	{
		return m_id;		
	}
	
	/**
	 * @return String name of the enumerator.
	 */
	public String getReadableName()
	{
		return m_readableName;
	}
}
