package utils.externalInterface
{	
	import flash.events.Event;
	import flash.geom.Point;
	
	/**
	 * An event which carries a move information.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class ConfirmMoveEvent extends Event
	{
		public static const TYPE : String = "confirmEvent"; // Event type name.
		
		// END STATES:
		public static const NONE   : int = 0;					// Nobody won.
		public static const DRAW   : int = 1;					// Game ended in a draw.
		public static const PL0    : int = 2;					// Player 0 won.
		public static const PL1    : int = 3;					// Player 1 won.
		public static const D_DRAW : int = 4;					// Double draw.

		public var endState      : int;                     // The endstate this event carries.
		public var victory1      : Vector.<Point>;          // Vector of victory berries.
		public var victory2      : Vector.<Point>;          // Vector of victory berries.
		
		/**
		 * Constructor.
		 * 
		 * @param endState state of the game after the performed turn. either NONE, DRAW, PL0, PL1, or D_DRAW.
		 * @param i1-i5 I coordinates for winning pieces.
		 * @param j1-j5 J coordinates for winning pieces.
		 * @param x1-x5 I coordinates for winning pieces.
		 * @param y1-y5 J coordinates for winning pieces.
		 */
		public function ConfirmMoveEvent(endState:int, i1:uint, j1:uint, i2:uint, j2:uint, i3:uint, j3:uint, i4:uint, j4:uint, i5:uint, j5:uint,
										 x1:uint, y1:uint, x2:uint, y2:uint, x3:uint, y3:uint, x4:uint, y4:uint, x5:uint, y5:uint)
		{
			super(TYPE, false, false);
			
			this.endState = endState;
			
			// Translate the arguments into a vector of AS3 points (Easier to manipulate later than these 10 arguments...).
			victory1 = new Vector.<Point>(5);
			victory1[0] = new Point(i1, j1);
			victory1[1] = new Point(i2, j2);
			victory1[2] = new Point(i3, j3);
			victory1[3] = new Point(i4, j4);
			victory1[4] = new Point(i5, j5);
			
			victory2 = new Vector.<Point>(5);
			victory2[0] = new Point(x1, y1);
			victory2[1] = new Point(x2, y2);
			victory2[2] = new Point(x3, y3);
			victory2[3] = new Point(x4, y4);
			victory2[4] = new Point(x5, y5);
		}
		
	}
}