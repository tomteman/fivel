package ui
{	
	import com.greensock.TweenLite;
	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	
	import game.GameManager;
	
	import utils.SoundManager;

	/**
	 * This class visuallizes the Fivel's menu.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class GameMenu extends Sprite
	{
		private var graphic   : MovieClip;  // The menu graphic.
		private var inst      : MovieClip;  // Instructions movie clip;
		private var diff      : MovieClip;  // The difficulty movie clip.
		private var diffPhase : uint;       // The difficulty selection phase for CvC.
		
		/**
		 * Constructor.
		 */
		public function GameMenu()
		{
			graphic = new GFX_GameMenu();
			addChild(graphic);
		
			// Configure game modes and instruction buttons.
			configure(graphic.pvp);
			configure(graphic.pvc);
			configure(graphic.cvc);
			configure(graphic.ins);
			
			inst = new GFX_Instructions();
			addChild(inst);
			inst.visible = false;
			
			diff = new GFX_Difficulty();
			addChild(diff);
			diff.visible = false;
			configureDiff(diff.cpu1, 2);
			configureDiff(diff.cpu2, 3);
			configureDiff(diff.cpu3, 4);
			configureDiff(diff.cpu4, 5);
		}
		
		/**
		 * Configures a button for mouse events.
		 * 
		 * @param button The button graphic.
		 */
		private function configure(button:MovieClip):void
		{
			button.addEventListener(MouseEvent.MOUSE_DOWN, handle_mouseDown);
			button.addEventListener(MouseEvent.MOUSE_UP,   handle_mouseUp);
			button.addEventListener(MouseEvent.ROLL_OVER,  handle_rollOver);
			button.addEventListener(MouseEvent.ROLL_OUT,   handle_rollOut);
		}
		
		/**
		 * Configures a difficulty button.
		 * 
		 * @param button The button graphic.
		 * @param level The character level.
		 */
		private function configureDiff(button:MovieClip, level:uint):void
		{
			button.addEventListener(MouseEvent.MOUSE_UP,   handle_difficulty);
			button.addEventListener(MouseEvent.ROLL_OVER,  handle_rollOver);
			button.addEventListener(MouseEvent.ROLL_OUT,   handle_rollOut);
			button.level = level;
		}
		
		/**
		 * Activates the menu for user input.
		 */
		public function activate():void
		{
			MovieClip(graphic.pvp).mouseEnabled = true;
			MovieClip(graphic.pvc).mouseEnabled = true;
			MovieClip(graphic.cvc).mouseEnabled = true;
			MovieClip(graphic.ins).mouseEnabled = true;
		}
		
		/**
		 * Deactivates the meny for user iput.
		 */
		public function deactivate():void
		{
			MovieClip(graphic.pvp).mouseEnabled = false;
			MovieClip(graphic.pvc).mouseEnabled = false;
			MovieClip(graphic.cvc).mouseEnabled = false;
			MovieClip(graphic.ins).mouseEnabled = false;
		}
		
		/**
		 * Handles roll over on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOver(e:MouseEvent=null):void
		{
			MovieClip(e.target).gotoAndStop(2);
		}
		
		/**
		 * Handles roll out on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_rollOut(e:MouseEvent=null):void
		{
			MovieClip(e.target).gotoAndStop(1);
		}
		
		/**
		 * Handles mouse down on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_mouseDown(e:MouseEvent=null):void
		{
			MovieClip(e.target).gotoAndStop(3);
		}
		
		/**
		 * Handles mouse up on a button.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_mouseUp(e:MouseEvent=null):void
		{
			MovieClip(e.target).gotoAndStop(1);
			var name : String = MovieClip(e.target).name;
			deactivate();
			
			SoundManager.instance.playSoundByClass(SFX_Click);
			
			// If chose instructions.
			if (name == "ins")
			{
				showInstruction();
			}
			// Game mode.
			else
			{
				GameManager.instance.gameMode = name;
				if (name != GameManager.MODE_PVP)
				{
					diffPhase = 0;
					showDifficulty();
					switch (name)
					{
						case GameManager.MODE_PVC : diff.gotoAndStop(1); break;
						case GameManager.MODE_CVC : diff.gotoAndStop(2); break;
					}
				}
				else
				{
					GameManager.instance.players[0].char = 1;
					GameManager.instance.players[1].char = 5;
					GameManager.instance.players[0].type = GameManager.MAN;
					GameManager.instance.players[1].type = GameManager.MAN;
					FivelClient.instance.transitStartGame();	
				}
				
			}
		}
		
		/**
		 * Handles choosing a difficulty.
		 * 
		 * @param e The mouse event.
		 */
		private function handle_difficulty(e:MouseEvent=null):void
		{
			SoundManager.instance.playSoundByClass(SFX_Click);
			if (GameManager.instance.gameMode == GameManager.MODE_PVC)
			{
				hideDifficulty();
				
				playCharSound(MovieClip(e.currentTarget).level);
				
				GameManager.instance.players[0].char = 1;
				GameManager.instance.players[1].char = MovieClip(e.currentTarget).level;
				GameManager.instance.players[0].type = GameManager.MAN;
				GameManager.instance.players[1].type = GameManager.CPU;
				
				FivelClient.instance.transitStartGame();	
			}
			else if (diffPhase == 0)
			{
				playCharSound(MovieClip(e.currentTarget).level);
				
				GameManager.instance.players[0].type = GameManager.CPU;
				GameManager.instance.players[0].char = MovieClip(e.currentTarget).level;
				diff.gotoAndStop(3);
				diffPhase++;
			}
			else
			{
				playCharSound(MovieClip(e.currentTarget).level);
				
				GameManager.instance.players[1].type = GameManager.CPU;
				GameManager.instance.players[1].char = MovieClip(e.currentTarget).level;
				hideDifficulty();
				
				FivelClient.instance.transitStartGame();	
			}
		}
		
		/**
		 * Plays sound by character
		 * 
		 * @param char Character level
		 */
		private function playCharSound(char:uint):void
		{
			switch (char)
			{
				case 2 : SoundManager.instance.playSoundByClass(SFX_Diff2); break;
				case 3 : SoundManager.instance.playSoundByClass(SFX_Diff3); break;
				case 4 : SoundManager.instance.playSoundByClass(SFX_Diff4); break;
				case 5 : SoundManager.instance.playSoundByClass(SFX_Diff5); break;
			}
		}
		
		/**
		 * Shows the difficulty panel.
		 */
		private function showDifficulty():void
		{
			diff.visible = true;
			diff.alpha   = 0;
			TweenLite.to(diff, 0.5, {alpha:1});
		}
		
		
		/**
		 * Shows the instruction panel.
		 */
		private function showInstruction():void
		{
			inst.visible = true;
			inst.alpha   = 0;
			TweenLite.to(inst, 0.5, {alpha:1});
			inst.addEventListener(MouseEvent.CLICK, hideInstruction);
		}
		
		/**
		 * Hides the instruction panel.
		 * 
		 * @param e Mouse event.
		 */
		private function hideInstruction(e:MouseEvent=null):void
		{
			activate();
			SoundManager.instance.playSoundByClass(SFX_Click);
			inst.removeEventListener(MouseEvent.CLICK, hideInstruction);
			TweenLite.to(inst, 0.5, {alpha:0, onComplete:function():void { inst.visible = false; }});
		}
		
		/**
		 * Hides the difficulty panel.
		 * 
		 * @param e Mouse event.
		 */
		private function hideDifficulty(e:MouseEvent=null):void
		{
			TweenLite.to(diff, 0.5, {alpha:0, onComplete:function():void { diff.visible = false; }});
		}
		
		
	}
}