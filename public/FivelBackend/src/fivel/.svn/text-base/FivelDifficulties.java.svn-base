package fivel;

import fivel.config.Difficulty;

/**
 * A tile slot object.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class FivelDifficulties
{
	public static final Difficulty[] LEVELS = new Difficulty[4];
	
	static
	{
		// Configure difficulties.
		Difficulty BEGINNER    = new Difficulty("BEGINNER",    
												new int[] { 1 },
												2, 3);
		
		Difficulty EXPERIENCED = new Difficulty("EXPERT",
												new int[] { 2 },
												2, 2);
		
		Difficulty TOUGH       = new Difficulty("TOUGH",
												new int[] { 3, 3, 3, 3, 3, 4 },
												2, 2);
		
		Difficulty GODLIKE     = new Difficulty("GODLIKE",
												new int[] { 3, 3, 3, 4, 4, 4, 4, 4, 4, 5 },
												2.5, 2);
		
		// Put difficulties in level array.
		LEVELS[0] = BEGINNER;
		LEVELS[1] = EXPERIENCED;
		LEVELS[2] = TOUGH;
		LEVELS[3] = GODLIKE;
	}
}
