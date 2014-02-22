package game.components
{	
	/**
	 * Player information.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class Player
	{
		public var char : uint;   // Which character (difficulty in AI mode) this player is.
		public var type : String; // Type of player (cpu or human).
		
		/**
		 * Constructor.
		 * Players are created at game's initilization and their properties are updated when a new game starts.
		 */
		public function Player()
		{
		}
		
	}
}