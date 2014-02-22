package fivel.utils;

/**
 * A pair object to carry slot coordinates between front and back end.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class Pair
{
	public int i; // I coordinate (col).
	public int j; // J coordinate (row).
	
	/**
	 * Constructor.
	 * 
	 * @param i column coordinate.
	 * @param j row coordinate.
	 */
	public Pair(int i, int j)
	{
		this.i = i; 
		this.j = j;
	}
	
	@Override
	/**
	 * To string function of a pair (for tracing).
	 */
	public String toString()
	{
		return String.format("%d, %d", i, j);
	}
}
