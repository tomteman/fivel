package utils.externalInterface
{	
	import flash.events.EventDispatcher;
	import flash.external.ExternalInterface;
	
	/**
	 * This class supplies an interface to the backend of Fivel throuhg Java Script.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class BackendInterface extends EventDispatcher
	{
		private static var _instance : BackendInterface; // Singelton self instance.
		
		public var listening : Boolean;                  // Whether the interface is lisetning to callback functions or not.
		
		/**
		 * Constructor.
		 */
		public function BackendInterface() {}
		
		/**
		 * Initializes the backend interface.
		 */
		public function init():void
		{
			ExternalInterface.addCallback("returnStartingPlayer", returnStartingPlayer);
			ExternalInterface.addCallback("returnMove", 		  returnMove);
			ExternalInterface.addCallback("confirmMove",		  confirmMove);
			listening = false;
		}
		
		// INTERFACE FUNCTIONS.
		/**
		 * Tells the backend to reset the game board.
		 */
		public function startGame():void
		{
			ExternalInterface.call("startGame");
		}
		
		/**
		 * Callback function, called from the backend when a move is ready after a requestMove call.
		 * 
		 * @param player The player of the move.
		 * @param i The horizontal coordinate where a piece was placed.
		 * @param j The vertical coordinate where a piece was placed.
		 * @param tile The tile number that was moved.
		 */
		public function performMove(player:uint, i:uint, j:uint, tile:uint):void
		{
			ExternalInterface.call("performMove", player, i, j, tile);
		}
		
		/**
		 * Sends a request for a move from the backend.
		 * 
		 * @param player The player which requests the move.
		 * @param diff Difficulty level of requesting player.
		 * @param turn The current mutual turn.
		 */
		public function requestMove(player:uint, diff:uint, turn:uint):void
		{
			ExternalInterface.call("requestMove", player, diff, turn);
		}
		
		/**
		 * Callback function, called from the backend when a move is ready after a requestMove call.
		 * 
		 * @param player The player of the move.
		 * @param i The horizontal coordinate where a piece was placed.
		 * @param j The vertical coordinate where a piece was placed.
		 * @param tile The tile number that was moved.
		 */
		public function returnStartingPlayer(player:uint):void
		{
			if (listening)
			{
				dispatchEvent(new ReturnStartPlayerEvent(player));
			}
		}
		
		/**
		 * Callback function, called from the backend when a move is ready after a requestMove call.
		 * 
		 * @param player The player of the move.
		 * @param i The horizontal coordinate where a piece was placed.
		 * @param j The vertical coordinate where a piece was placed.
		 * @param tile The tile number that was moved.
		 */
		public function returnMove(player:uint, i:uint, j:uint, tile:uint):void
		{
			if (listening)
			{
				dispatchEvent(new ReturnMoveEvent(player, i, j, tile));
			}
		}
		
		/**
		 * Callback function, called from the backend when a has been performed.
		 * 
		 * @param endState state of the game after the performed turn. either NONE, DRAW, PL0 or PL1.
		 * @param i1-i5 I coordinates for winning pieces.
		 * @param j1-j5 J coordinates for winning pieces. 
		 * @param x1-x5 I coordinates for winning pieces.
		 * @param y1-y5 J coordinates for winning pieces.
		 */
		public function confirmMove(endState:int, i1:uint, j1:uint, i2:uint, j2:uint, i3:uint, j3:uint, i4:uint, j4:uint, i5:uint, j5:uint,
									x1:uint, y1:uint, x2:uint, y2:uint, x3:uint, y3:uint, x4:uint, y4:uint, x5:uint, y5:uint):void
		{
			if (listening)
			{
				dispatchEvent(new ConfirmMoveEvent(endState, i1, j1, i2, j2, i3, j3, i4, j4, i5, j5, x1, y1, x2, y2, x3, y3, x4, y4, x5, y5));
			}
		}
		// END INTERFACE FUNCTIONS.
		
		/** 
		 * Singelton getter.
		 */
		public static function get instance():BackendInterface
		{
			if (_instance == null) 
			{
				_instance = new BackendInterface();
			}
			return _instance;
		}
	}
}