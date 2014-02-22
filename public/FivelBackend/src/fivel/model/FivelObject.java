package fivel.model;

/**
 * an abstract class for most of Fivel's objects.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public abstract class FivelObject 
{
	int m_id; // Object ID.

	/**
	 * Constructor.
	 * 
	 * @param id Object id.
	 */
	public FivelObject(int id)
	{
		m_id = id;
	}
	
	/** 
	 * Sets a new ID for the object.
	 * 
	 * @param id The new ID.
	 */
	public void setID(int id)
	{
		m_id = id;
	}
	
	/**
	 * @return the object's ID.
	 */
	public int getID()
	{
		return m_id;
	}
}
