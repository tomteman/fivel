package fivel.enums;

/**
 * Enumerator for game states.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public enum EndStates
{
	None(0,"None"),       			   // Nothing happened - game continued.
	Draw(1,"Draw"),       			   // Game ended in a draw.
	Pl0(2, "Player 0 Victory"),        // Game ended with player 0 winning.
	Pl1(3, "Player 1 Victory"),        // Game ended with player 1 winning.
	DoubleDraw(4, "Double Draw");	   // Game ended in a draw with both player scoring a Fivlet.
	
	private int    m_id;   // Id.
	private String m_name; // String name.
	
	/**
	 * Constructor.
	 * 
	 * @param id Enumerator id.
	 * @param readableName String name.
	 */
	EndStates(int id, String name)
	{
		m_id   = id;
		m_name = name;
	}
	
	/**
	 * @return the ID of the enumerator.
	 */
	public int getID()
	{
		return m_id;
	}
	
	/**
	 * @return the name of the enumerator.
	 */
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * @param player Given player number.
	 * @return an ending state according to player number.
	 */
	public static EndStates getEndStateAccordingToPlayer(int player)
	{
		switch(player)
		{
			case 1 : { return Pl0; }
			case -1: { return Pl1; }
			default:
			{
				return null;			
			}
				
		}
	}
}


