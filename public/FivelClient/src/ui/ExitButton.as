package ui
{	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	
	import game.GameManager;

	/**
	 * An exit button UI for the game component.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a. 
	 */
	public class ExitButton extends Sprite
	{
		/**
		 * Constructor.
		 */
		public function ExitButton()
		{
			var exit : MovieClip = new GFX_Exit();
			addChild(exit);
			
			exit.addEventListener(MouseEvent.ROLL_OUT,   handle_rollOut);
			exit.addEventListener(MouseEvent.ROLL_OVER,  handle_rollOver);
			exit.addEventListener(MouseEvent.MOUSE_DOWN, handle_mouseDown);
			exit.addEventListener(MouseEvent.CLICK,      handle_click);
		}
		
		
		/**
		 * Event handler for roll over on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOver(e:MouseEvent=null):void
		{
			MovieClip(e.currentTarget).scaleX = MovieClip(e.currentTarget).scaleY = 1.1;
		}
		
		/**
		 * Event handler for roll out on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOut(e:MouseEvent=null):void
		{
			MovieClip(e.currentTarget).scaleX = MovieClip(e.currentTarget).scaleY = 1.0;
		}
		
		/**
		 * Event handler for mouse down on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_mouseDown(e:MouseEvent=null):void
		{
			MovieClip(e.currentTarget).scaleX = MovieClip(e.currentTarget).scaleY = 0.8;
		}
		
		/**
		 * Event handler for click on the exit button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_click(e:MouseEvent=null):void
		{
			GameManager.instance.stopGame();
		}
	}
}