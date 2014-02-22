package fivel.config;

/**
 * Defines a difficulty level in Fivel.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class Difficulty
{
	public String name;  // Difficulty name.
	public int[] depths; // Depths array.
	public double agg;    // Aggression rating.
	public double def;    // Defense rating.
	
	/**
	 * Constructor.
	 * 
	 * @param depths Depths array.
	 * @param agg Aggression rate.
	 * @param def Defense rate.
	 */
	public Difficulty(String name, int[] depths, double agg, double def)
	{
		this.name   = name;
		this.depths = depths;
		this.agg    = agg;
		this.def    = def;
	}
}
