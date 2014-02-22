package game.components
{	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import game.GameManager;

	/**
	 * A graphical slot on a tile in Fivel.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class Slot extends Sprite
	{
		public static const SLOT_INTERVAL : Number =  47.0; // The horizontal and vertical distance between the slot.
		public static const SLOT_INIT_X   : Number = -23.5; // The X point from which slots will be started to be drawn.
		public static const SLOT_INIT_Y   : Number = -23.5; // The Y point from which slots will be started to be drawn.
		
		public var id     : uint;                           // The slot ID.
		public var tile   : Tile;                           // Reference to parent tile.
		public var berry  : Berry;   						// Berry clip.
		
		/**
		 * Constructor.
		 * 
		 * @param tile Parent tile.
		 * @param id The slot id.
		 */
		public function Slot(tile:Tile, id:uint)
		{
			// Graphics.
			var graphic : MovieClip = new GFX_Slot();
			addChild(graphic);
			
			this.tile = tile;
			this.id   = id;
		}
		
		/**
		 * Resets the slot.
		 */
		public function reset():void
		{
			if (berry)
			{
				this.removeChild(berry);
				berry = null;
			}
			deactivate();
		}
		
		
		/**
		 * Activates the slot for user input.
		 */
		public function activate():void
		{
			mouseEnabled  = true;
			mouseChildren = true;
			addEventListener(MouseEvent.ROLL_OVER, handle_rollOver);
			addEventListener(MouseEvent.ROLL_OUT,  handle_rollOut);
		}
		
		/**
		 * Deactivates the slot for user input.
		 */
		public function deactivate():void
		{
			mouseEnabled  = false;
			mouseChildren = false;
			if (berry) berry.mouseEnabled = false;
			removeEventListener(MouseEvent.ROLL_OVER, handle_rollOver);
			removeEventListener(MouseEvent.ROLL_OUT,  handle_rollOut);
		}
		
		// EVENT HANDLERS:
		/**
		 * Event handler for roll over on a slot.
		 * 
		 * @param e The mouse event.
		 */
		public function handle_rollOver(e:MouseEvent=null):void
		{
			// Stores the last slot the mouse was over on, so it knows where to put the berry when clicking.
			if (berry == null)
			{
				GameManager.instance.onSlot = this;
			}
		}
		
		/**
		 * Event handler for roll out on a slot.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOut(e:MouseEvent=null):void
		{
			if (berry == null)
			{
				GameManager.instance.onSlot = null;
			}
		}
		
		// UTILS:
		/**
		 * @return The indices of this slot on the board.
		 */
		public function getIndices():Point
		{
			// Translates the selected slot position into coordinates.
			var slotTile : uint = tile.id;
			var tileRow  : uint = uint(slotTile / 3);
			var tileCol  : uint = slotTile % 3;
			
			var slotRow  : uint = uint(id / 2);
			var slotCol  : uint = id % 2;
			var i        : uint = (tileCol * 2) + slotCol;
			var j        : uint = (tileRow * 2) + slotRow;
			
			return new Point(i, j);
		}
	}
}