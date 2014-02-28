package utils.externalInterface
{	
	import flash.events.Event;
	
	/**
	 * An event which carries a the starting player id (chosen randomly in the backend).
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class ReturnStartPlayerEvent extends Event
	{
		public static const TYPE : String = "startPlayerEvent"; // Event type name.
		
		public var player : uint; // Player number.
		
		/**
		 * Constructor.
		 * 
		 * @param player The starting player.
		 */
		public function ReturnStartPlayerEvent(player:uint)
		{
			super(TYPE, false, false);
			this.player = player;
		}
		
	}
}