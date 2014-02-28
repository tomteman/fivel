package ui
{	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.ui.Mouse;

	/**
	 * A custom cursor for Fivel.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class Cursor extends Sprite
	{
		private var graphic : Sprite; // The cursor graphic.
		
		/**
		 * Constructor
		 */
		public function Cursor()
		{
			// Graphics.
			graphic = new GFX_Cursor();
			addChild(graphic);
			graphic.mouseEnabled  = false;
			graphic.mouseChildren = false;
			this.mouseChildren = false;
			this.mouseEnabled = false;
			Mouse.hide();
			addEventListener(Event.ADDED_TO_STAGE, handle_addedToStage);
		}
		
		/**
		 * Handles added to stage, for configuration purposes.
		 * 
		 * @param e The event.
		 */
		private function handle_addedToStage(e:Event=null):void
		{
			removeEventListener(Event.ADDED_TO_STAGE, handle_addedToStage);
			stage.addEventListener(MouseEvent.MOUSE_MOVE, handle_mouseMove);
			graphic.x = stage.mouseX;
			graphic.y = stage.mouseY;
		}
		
		/**
		 * Handles mouse move events.
		 * 
		 * @param e The mouse move event.
		 */
		private function handle_mouseMove(e:MouseEvent=null):void
		{
			graphic.x = e.stageX;
			graphic.y = e.stageY;
			Mouse.hide();
			e.updateAfterEvent();
		}
		
	}
}