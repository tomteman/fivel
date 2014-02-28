package game.components
{	
	import com.greensock.TweenLite;
	import com.greensock.easing.Bounce;
	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import game.GameManager;
	
	import utils.SoundManager;

	/**
	 * Berry (piece) graphic object.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class Berry extends Sprite
	{
		/**
		 * Constructor.
		 * 
		 * @param player The player this berry belongs to.
		 */
		public function Berry(player:uint)
		{
			var berry : MovieClip;
			
			if (player == 0) berry = new GFX_Berry0();
			else 			 berry = new GFX_Berry1();
			addChild(berry);
			
			berry.mouseEnabled = false;
		}
		
		/**
		 * Handles placing a berry in a slot graphically.
		 * 
		 * @param slot The slot the berry is added to.
		 * @param progress The progress function to play after the berry animation.
		 */
		public function placeAt(slot:Slot, progress:Function):void
		{
			slot.berry = this;
			if (this.parent) this.parent.removeChild(this);
			this.x = 0; 
			this.y = 0;
			slot.addChild(this);
			SoundManager.instance.playSoundByClass(SFX_Piece);
			TweenLite.to(this, Tile.BERRY_DURA, {scaleX:0.7, scaleY:0.7, ease:Bounce.easeOut, onComplete:progress}); 
			GameManager.instance.berry = null;
			
			var p : Point = slot.getIndices();
			GameManager.instance.selectedSlotI = p.x;
			GameManager.instance.selectedSlotJ = p.y;
			
			this.mouseEnabled = false;
		}
		
		/**
		 * Activates berry for mouse events.
		 */
		public function activate():void
		{
			stage.addEventListener(MouseEvent.MOUSE_MOVE, handle_berryMove);
			stage.addEventListener(MouseEvent.MOUSE_UP, handle_berryUp);
			stage.addEventListener(MouseEvent.MOUSE_DOWN, handle_berryDown);
			stage.addEventListener(MouseEvent.CLICK, handle_berryPlace);
		}
		
		/**
		 * Deactivates berry for mouse events.
		 */
		public function deactivate():void
		{
			stage.removeEventListener(MouseEvent.MOUSE_MOVE, handle_berryMove);
			stage.removeEventListener(MouseEvent.MOUSE_UP, handle_berryUp);
			stage.removeEventListener(MouseEvent.MOUSE_DOWN, handle_berryDown);
			stage.removeEventListener(MouseEvent.CLICK, handle_berryPlace);
		}
		
		
		// EVENT HANDLERS:
		/**
		 * Event handler for berry move.
		 * 
		 * @param e The mouse event
		 */
		private function handle_berryMove(e:MouseEvent=null):void
		{
			this.x = e.stageX;
			this.y = e.stageY;
			e.updateAfterEvent();
		}
		
		/**
		 * Event handler for berry mouse down.
		 * 
		 * @param e The mouse event
		 */
		private function handle_berryDown(e:MouseEvent=null):void
		{
			this.scaleY = this.scaleX = 0.8;
		}
		
		/**
		 * Event handler for berry mouse down.
		 * 
		 * @param e The mouse event
		 */
		private function handle_berryUp(e:MouseEvent=null):void
		{
			this.scaleY = this.scaleX = 1;
		}
		
		/**
		 * Event handler for berry placement.
		 * 
		 * @param e The mouse event
		 */
		private function handle_berryPlace(e:MouseEvent=null):void
		{
			// If there wasn't an interaction with a slot, play error sound.
			if (GameManager.instance.onSlot == null)
			{
				SoundManager.instance.playSoundByClass(SFX_Error);
			}
			// Else, place the berry.
			else
			{
				GameManager.instance.placeBerryAt(GameManager.instance.onSlot);				
				GameManager.instance.onSlot = null;
			}
		}
		
	}
}