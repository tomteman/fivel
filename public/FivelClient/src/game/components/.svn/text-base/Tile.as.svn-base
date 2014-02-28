package game.components
{	
	import com.greensock.OverwriteManager;
	import com.greensock.TweenLite;
	import com.greensock.easing.Strong;
	
	import flash.display.MovieClip;
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.filters.DropShadowFilter;
	import flash.geom.Point;
	
	import game.GameManager;
	
	import utils.SoundManager;

	/**
	 * A graphical moveable tile in Fivel.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class Tile extends Sprite
	{
		// ANIMATIONS:
		public static const SLIDE_DURA    : Number = 0.5;   // Duration of slide animation.
		public static const BERRY_DURA    : Number = 0.5;   // Duration of berry placement animation.
		public static const ARROW_DURA    : Number = 0.25;  // Duration of arrow fade animation.
				
		// POSITIONS:
		public static const TILE_INTERVAL : Number = 120;   // The horizontal and vertical distance between the tiles.
		public static const ARROW_DIST    : Number = 40;    // The distance of the arrow from the center.
		public static const TILE_INIT_X   : Number = 145;   // The X point from which tiles will be started to be drawn.
		public static const TILE_INIT_Y   : Number = 131;   // The Y point from which tiles will be started to be drawn.
		
		// MEMBERS:
		public var id                     : uint;           // The tild ID number.
		public var slots                  : Vector.<Slot>;  // Vector of slots graphics.
		public var arrow                  : MovieClip;      // The arrow clip.
		public var tileLogicObject        : Object;         // The tile logic object generated at mouse over.
		public var cover                  : Sprite;         // Cover for mouse events to fix a rare bug with roll over.
		
		/**
		 * Constructor.
		 */
		public function Tile(id:uint)
		{
			this.id = id;
			
			// Tile Graphics.
			var graphic : MovieClip = new GFX_Tile();
			var randRot : uint;
			var random  : Number = Math.random();
			if      (random > 0.75) randRot = 90;
			else if (random > 0.50) randRot = 180;
			else if (random > 0.25) randRot = 270;
			else 					randRot = 0;
			graphic.rotation = randRot;
			addChild(graphic);
			graphic.filters = [new DropShadowFilter(3, 45, 0x000000, 0.5)];
			
			// Slots Graphics.
			slots = new Vector.<Slot>(4);
			for (var i : uint = 0; i < 4; i++)
			{
				var slot : Slot = new Slot(this, i);
				var row  : uint = uint(i / 2);
				var col  : uint = i % 2;
				addChild(slot);
				slot.x = Slot.SLOT_INIT_X + (Slot.SLOT_INTERVAL * col);
				slot.y = Slot.SLOT_INIT_Y + (Slot.SLOT_INTERVAL * row);
				slots[i] = slot;
			}
			
			// Arrow.
			arrow = new GFX_Arrow();
			addChild(arrow);
			arrow.visible = false;
			arrow.mouseEnabled = false;
			
			// Cover for mouse events.
			cover = new Sprite();
			cover.graphics.beginFill(0x000000, 0.0);
			cover.graphics.drawRect(-graphic.width/2 + 5, -graphic.height/2 + 5, graphic.width - 10, graphic.height - 10);
			addChild(cover);
			cover.visible = false;
			cover.mouseEnabled = false;
		}
		
		/**
		 * Rests the tile.
		 */
		public function reset():void
		{
			for each (var slot : Slot in slots) slot.reset();
		}
		
		/**
		 * Activates the slots for user input (mouse events).
		 */
		public function activateSlots():void
		{
			for each (var slot : Slot in slots)
			{
				slot.activate();
				
				// To select a slot after activation if mouse is already on.
				if (slot.hitTestPoint(stage.mouseX, stage.mouseY, true))
				{
					slot.handle_rollOver();
				}
			}
			
			cover.visible      = false;
			cover.mouseEnabled = false;
		}
		
		/**
		 * Activates the slots for user input (mouse events).
		 */
		public function activateTile():void
		{
			cover.addEventListener(MouseEvent.ROLL_OVER, handle_rollOver);
			cover.addEventListener(MouseEvent.ROLL_OUT,  handle_rollOut);
			cover.addEventListener(MouseEvent.CLICK,     handle_click);
			arrow.mouseEnabled = false;
			this.mouseEnabled  = true;
			
			cover.visible      = true;
			cover.mouseEnabled = true;
			
			// To select tile after activation if mouse is already on.
			if (cover.hitTestPoint(stage.mouseX, stage.mouseY, true))
			{
				handle_rollOver();
			}
		}
		
		/**
		 * Deactivates the entire tile for user input (mouse events).
		 */
		public function deactivate():void
		{
			cover.removeEventListener(MouseEvent.ROLL_OVER, handle_rollOver);
			cover.removeEventListener(MouseEvent.ROLL_OUT,  handle_rollOut);
			cover.removeEventListener(MouseEvent.CLICK,     handle_click);
			
			for each (var slot : Slot in slots) slot.deactivate();
		}
		
		// Event handlers.
		/**
		 * Handles roll over on the tile.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOver(e:MouseEvent=null):void
		{
			tileLogicObject = tileLogic(GameManager.instance.currentEmpty);
			if (tileLogicObject.active)
			{
				arrow.visible  = true;
				arrow.alpha    = 0;
				arrow.rotation = tileLogicObject.rotation;
				arrow.x        = tileLogicObject.posX;
				arrow.y        = tileLogicObject.posY;
				TweenLite.to(arrow, ARROW_DURA, {alpha:1, overwrite:OverwriteManager.ALL_IMMEDIATE});
			}
		}
		
		/**
		 * Handles roll out on the tile.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOut(e:MouseEvent=null):void
		{
			TweenLite.to(arrow, ARROW_DURA, {alpha:0, overwrite:OverwriteManager.ALL_IMMEDIATE, onComplete: function():void { arrow.sibiel = false; }});
		}
		
		/**
		 * Handles clicking on tile.
		 * 
		 * @param e The mouse event.
		 * @param override Whether to override tileLogic or not.
		 * @param delay Whether to delay the action or not.
		 */
		public function handle_click(e:MouseEvent=null, override:Boolean=false, delay:Boolean=false):void
		{
			if (override || tileLogicObject.active)
			{
				var temp      : uint     = GameManager.instance.currentEmpty;
				var p         : Point    = getCoordinates(temp);
				var delayTime : Number   = delay ? 0.5 : 0;
				var func      : Function = function():void { SoundManager.instance.playSoundByClass(SFX_Slide); };
				TweenLite.to(this, SLIDE_DURA, {onStart: func, delay:delayTime, x:p.x, y: p.y, ease:Strong.easeOut, onComplete: GameManager.instance.commitTurn});
				GameManager.instance.currentEmpty = id;
				GameManager.instance.selectedTile  = id;
				id = temp;
				
				handle_rollOut();
				
				GameManager.instance.deactivate();
			}
			else
			{
				SoundManager.instance.playSoundByClass(SFX_Error);
			}
		}
		
		// UTILS:
		/**
		 * Returns a position object according to the ID of the empty tile.
		 * This object is used to determine graphical properties when performing various functions on the tile.
		 * 
		 * @param id The id of the empty tile.
		 * @return A object with the following fields: 
		 * 			active: Tile can be moved or not.
		 * 			rotation: The arrow's rotation.
		 * 			posX: The arrow's X position.
		 * 			posY: The arrow's Y position.
		 */
		private function tileLogic(empty : uint):Object
		{
			var active   : Boolean;
			var rotation : uint;
			var posX     : Number;
			var posY     : Number;
			
			var myRow  : uint = uint(id / 3);
			var myCol  : uint = id % 3;
			var empRow : uint = uint(empty / 3);
			var empCol : uint = empty % 3;
			
			// Empty tile to my Right.
			if      ((myRow == empRow) && (empty == id + 1))
			{
				active   = true;	
				rotation = 0;
				posX     = ARROW_DIST;
				posY     = 0;
			}
			// Empty tile to my Left.
			else if ((myRow == empRow) && (empty == id - 1))
			{
				active   = true;	
				rotation = 180;
				posX     = -ARROW_DIST;
				posY     = 0;
			}
			// Empty tile to my Up.
			else if ((myCol == empCol) && (empty == id - 3))
			{
				active   = true;	
				rotation = 270;
				posX     = 0;
				posY     = -ARROW_DIST;
			}
			// Empty tile to my Down.
			else if ((myCol == empCol) && (empty == id + 3))
			{
				active   = true;	
				rotation = 90;
				posX     = 0;
				posY     = ARROW_DIST;
			}
			// I can't move.
			else
			{
				active = false;
			}
			return {active:active, rotation:rotation, posX:posX, posY:posY}; 
		}
		
		/** 
		 * A static function which returns stage coordinates according to tile id.
		 * 
		 * @param id Tile id.
		 * @return Coordinates of tile according to id.
		 */
		public static function getCoordinates(id : uint):Point
		{
			var col  : uint = uint(id / 3);
			var row  : uint = id % 3;
			return new Point(TILE_INIT_X + (TILE_INTERVAL * row), TILE_INIT_Y + (TILE_INTERVAL * col)); 
		}
		
	}
}