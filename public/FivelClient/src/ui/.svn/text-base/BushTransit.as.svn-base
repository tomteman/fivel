package ui
{	
	import flash.display.DisplayObject;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.Event;
	
	import utils.SoundManager;

	/**
	 * A graphical object used for transations.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class BushTransit extends Sprite
	{
		private var graphic    : MovieClip;     // The cover graphic.
		private var hide       : DisplayObject; // Stores the display object to hide in the transition.
		private var show       : DisplayObject; // Stores the display object to show in the transition.
		private var func       : Function;      // Stores the function to call after the transition (on show).
		
		/**
		 * Constructor.
		 */
		public function BushTransit()
		{
			graphic = new GFX_Cover();
			addChild(graphic);
			graphic.visible = false;
			graphic.stop();
			graphic.addFrameScript(graphic.totalFrames - 1, function():void { graphic.stop(); graphic.visible = false; });
			
			// The graphic dispatches this events on predefined frames in the animation.
			graphic.addEventListener(Event.CHANGE,   playSound);
			graphic.addEventListener(Event.COMPLETE, changeGraphic);
		}
		
		/**
		 * Plays a transition animation.
		 * 
		 * @param hide Object to hide in middle of transition.
		 * @param show Object to show in middle of transition.
		 * @param functionOnShow Which function to call on show on transition.
		 */
		public function transit(hide:DisplayObject, show:DisplayObject, functionOnShow:Function=null):void
		{
			graphic.visible = true;
			graphic.gotoAndPlay(1);
			
			this.hide		= hide;
			this.show		= show;
			this.func 		= functionOnShow;
		}
		
		/**
		 * Plays the transition sound.
		 */
		private function playSound(e:Event=null):void
		{
			SoundManager.instance.playSoundByClass(SFX_Bush);
		}
		
		/**
		 * Replaces the components behind the bushes as they close.
		 */
		private function changeGraphic(e:Event=null):void
		{
			hide.visible = false;
			show.visible = true;
			if (func != null) func.call(show);
		}
	}
}