package utils.externalInterface
{	
	import flash.events.Event;

	/**
	 * An event which carries a move information.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class ReturnMoveEvent extends Event
	{
		public static const TYPE : String = "moveEvent"; // Event type name.
		
		public var player : uint;                        // The playing player.
		public var i      : uint;                        // The berry i coordinate.
		public var j      : uint;                        // The berry j coordinate.
		public var tile   : uint;                        // The tile that was moved.
		
		/**
		 * Constructor.
		 * 
		 * @param player The player of the move.
		 * @param i The horizontal coordinate where a piece was placed.
		 * @param j The vertical coordinate where a piece was placed.
		 * @param tile The tile number that was moved.
		 */
		public function ReturnMoveEvent(player:uint, i:uint, j:uint, tile:uint)
		{
			super(TYPE, false, false);
			
			this.player = player;
			this.i      = i;
			this.j      = j;
			this.tile   = tile;
		}
		
	}
}