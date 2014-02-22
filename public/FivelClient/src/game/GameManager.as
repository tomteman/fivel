package game
{	
	import com.greensock.TweenLite;
	import com.greensock.easing.Bounce;
	
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import game.components.Berry;
	import game.components.Player;
	import game.components.Slot;
	import game.components.Tile;
	
	import ui.ExitButton;
	
	import utils.SoundManager;
	import utils.externalInterface.BackendInterface;
	import utils.externalInterface.ConfirmMoveEvent;
	import utils.externalInterface.ReturnMoveEvent;
	import utils.externalInterface.ReturnStartPlayerEvent;

	/**
	 * This class visuallizes the game board of Fivel.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class GameManager extends Sprite
	{
		// SINGLETON REFERENCE.
		public static var instance        : GameManager;    // A semi-singelton reference (semi, since it has to be created manually on init).
		
		// CONSTANTS:
		public static const EMPTY_TILE    : uint   = 4;      // The tile id that won't be drawn in the beginning.
		public static const MODE_PVP      : String = "pvp";  // Player vs Player mode.
		public static const MODE_PVC      : String = "pvc";  // Player vs CPU mode.
		public static const MODE_CVC      : String = "cvc";  // CPU vs CPU mode.
		public static const CPU           : String = "cpu";  // CPU Player.
		public static const MAN           : String = "man";  // MAN Player.
		
		// UI:
		public var exit                   : ExitButton;      // Exit button.		
		public var status                 : MovieClip;       // Status text.
		public var order                  : MovieClip;       // Order text.
		public var char                   : MovieClip;       // Player character.
		public var turns                  : MovieClip;       // Turn counter.
		
		// GAME GRAPHICS:
		public var tiles                  : Vector.<Tile>;   // Graphical tiles vector.
		public var berry                  : Berry;           // Berry graphic.
		public var onSlot                 : Slot;            // Highlighted slot.
		
		// GAME STATUS:
		public var gameMode               : String;          // Current game mode (PvC, PvP or CvC).
		public var players                : Vector.<Player>; // Vector of players.
		public var currentPlayer          : uint;            // Current player.
		
		// MOVES:
		public var selectedSlotI          : uint;            // The slot the playing player chose for a berry - I coordinate.
		public var selectedSlotJ          : uint;            // The slot the playing player chose for a berry - J coordinate.
		public var selectedTile           : uint;            // The tile the playing player chose to move - J coordinate.
		public var mutualTurns            : uint;            // How many mutual turns were made.
		public var turnSwitch             : Boolean;         // Whether to increase mutual turns or not.
		
		// SIMPLE GAME LOGIC:
		public var currentEmpty           : uint;            // The current empty tile. 
		
		/* ***********************************************************
		 * INITIALIZATION
		 */
		
		/**
		 * Constructor.
		 */
		public function GameManager()
		{
			instance = this;
			
			// Starts listening to back end events.
			BackendInterface.instance.addEventListener(ReturnStartPlayerEvent.TYPE, returnStartPlayerEvent);
			BackendInterface.instance.addEventListener(ReturnMoveEvent.TYPE,        returnMove);
			BackendInterface.instance.addEventListener(ConfirmMoveEvent.TYPE,       confirmMove);
			
			// Draws board.
			addChild(new GFX_Board());
			visible = false;
			
			tiles = new Vector.<Tile>(9);
			for (var i : uint = 0; i < 9; i++)
			{
				if (i != EMPTY_TILE)
				{
					var tile : Tile = new Tile(i);
					var p    : Point = Tile.getCoordinates(i);
					tile.x = p.x; tile.y = p.y;
					tiles[i] = tile;
					addChild(tile);
				}
			}
			
			currentEmpty = EMPTY_TILE;
			
			// Exit button.
			exit = new ExitButton();
			exit.x = 46.5; exit.y = 450.25;
			addChild(exit);
			deactivate();
			
			// Status.
			status = new GFX_Status();
			status.x = 612.45; status.y = 90.95;
			addChild(status);
			
			// Order.
			order = new GFX_Order();
			order.x = 612.45; order.y = 180.95;
			addChild(order);
			
			// Character.
			char = new GFX_Character();
			char.x = 649.45; char.y = 349;
			addChild(char);
			
			// Turns.
			turns = new GFX_Turns();
			turns.x = 612.45; turns.y = 50.95;
			addChild(turns);
			
			// Players and difficulty.
			players = new Vector.<Player>();
			players[0] = new Player();
			players[1] = new Player();
		}
		
		/**
		 * Preapres the board for game start.
		 */
		public function startBoard():void
		{
			mutualTurns = 0;
			turnSwitch  = true;
			turns.gotoAndStop(mutualTurns);
			
			// Sets the graphic to Fivel before receiving any call from the Java backend (just in case).
			if (gameMode != MODE_PVP)
			{
				char.gotoAndStop(players[1].char);
			}
			
			// Starts listening to events from the interface (we don't want events calling when not in play).
			BackendInterface.instance.listening = true;
			BackendInterface.instance.startGame();
		}
		
		/**
		 * Exits the game.
		 * 
		 * @param e A mouse event in case this was called from a listener.
		 */
		public function stopGame(e:MouseEvent=null):void
		{
			BackendInterface.instance.listening = false;
			
			deactivate();
			FivelClient.instance.transitEndGame();
			
			SoundManager.instance.playSoundByClass(SFX_Click);
			SoundManager.instance.playMusicByClass(SFX_Theme, 99999);
			
			// Resets the board after a short delay (when the board is already covered by the bushes).
			TweenLite.delayedCall(2, resetBoard);
		}
		
		/**
		 * Resets the board for a new game.
		 */
		public function resetBoard():void
		{
			currentEmpty = EMPTY_TILE;
			
			for (var i : uint = 0; i < 9; i++)
			{
				if (tiles[i] != null) 
				{
					tiles[i].reset();
					var p : Point = Tile.getCoordinates(i);
					tiles[i].x  = p.x;
					tiles[i].y  = p.y;
					tiles[i].id = i;
				}
			}
			
			if (berry)
			{
				removeChild(berry);
				berry = null;
			}
		}
		
		/* ***********************************************************
		* UI.
		*/
		
		/**
		 * Enables slots for user input.
		 */
		public function activateSlots():void
		{
			for each (var tile : Tile in tiles)
			{
				if (tile != null) tile.activateSlots();
			}
			exit.mouseEnabled = true;
		}
		
		/**
		 * Enables tiles for user input.
		 */
		public function activateTiles():void
		{
			for each (var tile : Tile in tiles)
			{
				if (tile != null) tile.activateTile();
			}
			exit.mouseEnabled = true;
		}
		
		/**
		 * Disables all user input.
		 */
		public function deactivate():void
		{
			for each (var tile : Tile in tiles)
			{
				if (tile != null) tile.deactivate();
			}
			
			if (berry) berry.deactivate();
			
			if (stage) stage.removeEventListener(MouseEvent.CLICK, stopGame);
			exit.mouseEnabled = false;
		}
		
		/**
		 * Sets the status, order and character graphics according to game type and current player.
		 */
		public function prepareNewTurn():void
		{
			// Increase mutual turns text.
			if (turnSwitch) 
			{
				mutualTurns++;
				turns.gotoAndStop(mutualTurns);
			}
			
			// If player is human.
			if (players[currentPlayer].type == MAN)
			{
				// Activate GUI for user input.
				activateSlots();
				prepareBerry(currentPlayer);
				order.gotoAndStop(1);
				status.gotoAndStop(players[currentPlayer].char);
				char.gotoAndStop(players[currentPlayer].char);
			}
			// Else, if player is CPU.
			else
			{
				order.gotoAndStop(3);
				status.gotoAndStop(players[currentPlayer].char);
				char.gotoAndStop(players[currentPlayer].char);
				
				// Ask for an AI move after a short delay so human player will not be confused.
				TweenLite.delayedCall(0.5, getAIMove);
			}
		}
		
		/* ***********************************************************
		* GAME FLOW.
		*/
		
		/**
		 * Commits a turn to the backend.
		 */
		public function commitTurn():void
		{
			// Performs the move in the backend.
			BackendInterface.instance.performMove(currentPlayer, selectedSlotI, selectedSlotJ, selectedTile);
		}
		
		/**
		 * Passes a turn.
		 */
		public function passTurn():void
		{
			turnSwitch = !turnSwitch;
			
			// Switches to the next player.
			currentPlayer = 1 - currentPlayer;
			prepareNewTurn();
		}
		
		/**
		 * Asks for an AI move depending on the difficulty of the current CPU player.
		 * The difficulty data is taken from an external class.
		 */
		public function getAIMove():void
		{
			var level : uint = players[currentPlayer].char;
			
			// Send the diffculty level (minus 2 due to ui reasons).
			BackendInterface.instance.requestMove(currentPlayer, level - 2, mutualTurns);
		}
		
		/**
		 * Perform an AI move graphically.
		 * 
		 * @param player The player performing.
		 * @param i The berry i coordinate.
		 * @param j The berry j coordinate.
		 * @param tile The tile moved.
		 */
		public function performAIMove(player:uint, i:uint, j:uint, tile:uint):void
		{
			// Translate the received cooridnates into the tile and slot graphical objects.
			berry = new Berry(player);
			var berryTileRow : uint = uint(j / 2);
			var berryTileCol : uint = uint(i / 2);
			var berryTile    : uint = (berryTileRow * 3) + berryTileCol;
			var berrySlotRow : uint;
			var berrySlotCol : uint;
			var realTile     : uint;
			
			berrySlotRow = j % 2;
			berrySlotCol = i % 2;
			
			var slot : Slot = getTileById(berryTile).slots[(berrySlotRow * 2) + berrySlotCol];
			
			// Progress function - move tile after berry was placed.
			var progress : Function = function():void
			{
				if (BackendInterface.instance.listening)
				{
					getTileById(tile).handle_click(null, true, true);
				}
			} 
			
			berry.placeAt(slot, progress);
		}
		
		/* ***********************************************************
		* GRAPHICS.
		*/
		
		/**
		 * Prepares a berry graphically for a placement move.
		 * 
		 * @param player The player who currently plays.
		 */
		public function prepareBerry(player:uint):void
		{
			berry = new Berry(player);
			addChild(berry);
			berry.mouseEnabled = false;
			berry.activate();
			berry.x = stage.mouseX;
			berry.y = stage.mouseY;
		}
		
		/** 
		 * Places a berry graphically and progresses game.
		 * 
		 * @param slot A slot clip.
		 */
		public function placeBerryAt(slot:Slot):void
		{
			deactivate();
			
			// Reset berry.			
			berry.deactivate();
			
			// Progress function - what happens after berry animation if over.
			var progress : Function = function():void 
			{
				if (BackendInterface.instance.listening)
				{
					activateTiles();
					order.gotoAndStop(2);
				}
			}
			
			// Set berry in slot.
			berry.placeAt(slot, progress);
		}
		
		/**
		 * Plays victory graphic and sound.
		 * 
		 * @param player The winning player.
		 * @param victory A vector of the winning 5.
		 */
		private function handleVictory(player:uint, victory:Vector.<Point>):void
		{
			if (gameMode != MODE_PVC)
			{
				order.gotoAndStop(4); // Victory.
				SoundManager.instance.playSoundByClass(SFX_Victory);
			}
			else if (player == 1)
			{
				order.gotoAndStop(5); // Defeat.
				SoundManager.instance.playSoundByClass(SFX_Defeat);
			}
			else 
			{
				order.gotoAndStop(4); // Victory vs PC.
				SoundManager.instance.playSoundByClass(SFX_Victory);
			}
			
			// Highlight winning berries.
			highlightVictory(victory);
		}
		
		/**
		 * Plays victory animation for a certain slots sets.
		 * 
		 * @param set Set of slots.
		 */
		public function highlightVictory(victory:Vector.<Point>):void
		{
			for each (var p : Point in victory)
			{
				var i       : uint = p.x;
				var j       : uint = p.y;
				var tileCol : uint = uint(i/2);
				var tileRow : uint = uint(j/2);
				var tileID  : uint = tileCol + (tileRow * 3);
				var slotCol : uint = i % 2;
				var slotRow : uint = j % 2;
				var slotID  : uint = slotCol + (slotRow * 2);
				var tile    : Tile = getTileById(tileID);
				var slot    : Slot = tile.slots[slotID];
				tile.setChildIndex(slot, tile.numChildren - 1);
				
				TweenLite.to(slot.berry, Tile.BERRY_DURA, {scaleX: 1.1, scaleY: 1.1,
														   glowFilter:{color:0xFFFFFF, blurX:8, blurY:8, strength:2, alpha:1},
														   glowFilter:{color:0xFFFFFF, blurX:7, blurY:7, strength:1, alpha:1, inner:true},
														   ease:Bounce.easeOut});
			}
		}
		
		/* ***********************************************************
		* BACKEND EVENT HANDLING
		*/
		
		/**
		 * Callback function recieved from the backend.
		 * Carries the starting player from the backend.
		 * 
		 * @param e The event that carries the returned turn from the backend.
		 */
		private function returnStartPlayerEvent(e:ReturnStartPlayerEvent=null):void
		{
			// Sets the receied player and starts the game flow.
			currentPlayer = e.player;
			if (players[currentPlayer].type == CPU) TweenLite.delayedCall(1.25, prepareNewTurn);
			else prepareNewTurn();
		}
		
		/**
		 * Callback function recieved from the backend.
		 * Indicates that an AI player has finished thinking and returned a move.
		 * After the move is received, it is played graphically and progresses the game.
		 * 
		 * @param e The event that carries the returned turn from the backend.
		 */
		private function returnMove(e:ReturnMoveEvent=null):void
		{
			performAIMove(e.player, e.i, e.j, e.tile);
		}
		
		/**
		 * Callback function recieved from the backend.
		 * Indicates that an the backend performed the move, and carries the state of the game afterwards.
		 * 
		 * @param e The event that carries the returned turn from the backend.
		 */
		private function confirmMove(e:ConfirmMoveEvent=null):void
		{
			// If game didn't end, continue the game and pass a turn.
			if (e.endState == ConfirmMoveEvent.NONE)
			{
				passTurn();
			}
			else
			{
				stage.addEventListener(MouseEvent.CLICK, stopGame);
				switch (e.endState)
				{
					// Game ended in double draw.
					case ConfirmMoveEvent.D_DRAW:
					{
						SoundManager.instance.playSoundByClass(SFX_Draw);
						order.gotoAndStop(6);
						highlightVictory(e.victory1);
						highlightVictory(e.victory2);
						break;
					}
						
						// Game ended in a draw.
					case ConfirmMoveEvent.DRAW : 
					{
						SoundManager.instance.playSoundByClass(SFX_Draw);
						order.gotoAndStop(6);
						break;
					}
						// Player 1 won.
					case ConfirmMoveEvent.PL0  :
					{
						handleVictory(0, e.victory1); break;
					}
						// Player 2 won.
					case ConfirmMoveEvent.PL1  :
					{
						handleVictory(1, e.victory1); break;
					}
				}
			}
		}
		
		/* ***********************************************************
		* UTILS
		*/
		
		/**
		 * Finds a tile by ID.
		 * 
		 * @param id Tile id.
		 * @return Tile object.
		 */
		private function getTileById(id:uint):Tile
		{
			for each (var tile : Tile in tiles)
			{
				if (tile != null && tile.id == id) return tile;
			}
			return null;
		}
	}
}