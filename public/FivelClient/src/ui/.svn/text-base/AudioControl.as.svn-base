package ui
{	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	
	import utils.SoundManager;

	/**
	 * Audio control UI.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class AudioControl extends Sprite
	{
		/**
		 * Constructor.
		 */
		public function AudioControl()
		{
			var sound : MovieClip = new GFX_Sound_Button();
			var music : MovieClip = new GFX_Music_Button();
			
			sound.x = 703.15;
			sound.y = 31.7;
			sound.mouseChildren = false;
			sound.stop();
			addChild(sound);
			
			music.x = 728.15;
			music.y = 31.45;
			music.mouseChildren = false;
			music.stop();
			addChild(music);
			
			sound.addEventListener(MouseEvent.ROLL_OUT,   handle_rollOut);
			sound.addEventListener(MouseEvent.ROLL_OVER,  handle_rollOver);
			sound.addEventListener(MouseEvent.MOUSE_DOWN, handle_mouseDown);
			sound.addEventListener(MouseEvent.CLICK,      handle_clickSound);
			
			music.addEventListener(MouseEvent.ROLL_OUT,   handle_rollOut);
			music.addEventListener(MouseEvent.ROLL_OVER,  handle_rollOver);
			music.addEventListener(MouseEvent.MOUSE_DOWN, handle_mouseDown);
			music.addEventListener(MouseEvent.CLICK,      handle_clickMusic);
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
		 * Event handler for click on the sound toggle button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_clickSound(e:MouseEvent=null):void
		{
			MovieClip(e.currentTarget).scaleX = MovieClip(e.currentTarget).scaleY = 1.0;
			SoundManager.instance.toggleSound();
			MovieClip(e.currentTarget).gotoAndStop(3 - MovieClip(e.currentTarget).currentFrame);
		}
		
		/**
		 * Event handler for click on the music toggle button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_clickMusic(e:MouseEvent=null):void
		{
			MovieClip(e.currentTarget).scaleX = MovieClip(e.currentTarget).scaleY = 1.0;
			SoundManager.instance.toggleMusic();
			MovieClip(e.currentTarget).gotoAndStop(3 - MovieClip(e.currentTarget).currentFrame);
		}
		
	}
}