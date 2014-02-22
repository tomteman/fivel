package ui
{	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	
	import game.GameManager;
	
	import utils.SoundManager;

	/**
	 * This class is responsible to show pre-game animation.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class CharTransit extends Sprite
	{
		private var graphic : MovieClip; // The animation graphic.
		
		/**
		 * Constructor.
		 */
		public function CharTransit()
		{
			graphic = new GFX_Intro();
			addChild(graphic);
			graphic.stop();
			
			graphic.addFrameScript(graphic.totalFrames - 1, function():void
				{ 
					graphic.stop(); 
					FivelClient.instance.transitShowBoard(); 
					SoundManager.instance.playMusicByClass(SFX_Game, 99999);
				}
			);
			
			visible = false;
		}
		
		/**
		 * Plays the transition animation with the chosen characters.
		 */
		public function playAnimation():void
		{
			SoundManager.instance.playMusicByClass(SFX_Intro, 1);
			
			graphic.char_1.gotoAndStop(GameManager.instance.players[0].char);
			graphic.text_2.gotoAndStop(GameManager.instance.players[0].char);
			
			graphic.char_2.gotoAndStop(GameManager.instance.players[1].char);
			graphic.text_1.gotoAndStop(GameManager.instance.players[1].char);
			
			graphic.gotoAndPlay(1);
		}
		
	}
}