package utils
{	
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.media.SoundMixer;
	import flash.media.SoundTransform;
	import flash.utils.Dictionary;
	import flash.utils.getDefinitionByName;

	/**
	 * Provides various tool to play sounds.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
     * Game Intelligence course, TAU 2010/11a.
	 */
	public class SoundManager
	{
		protected var musicLoops       : uint;           // Stores the amount of loops originally designated for music.
		protected var musicSound       : Sound;          // The sound of the current playing music.
		protected var musicChannel     : SoundChannel;   // The channel of the current playing music.
		protected var soundVolume      : SoundTransform; // Sound transform object for sounds.
		protected var musicVolume      : SoundTransform; // Sound transform object for musics.
		protected var globalOn         : Boolean;        // Global sound on flag.
		protected var soundOn          : Boolean;        // Sound on flag.
		protected var musicOn          : Boolean;        // Music on flag.
		protected var sounds           : Dictionary;     // Stores all sound for reuse.
		protected static var _instance : SoundManager;   // Singelton self instance.
		
		/**
		 * Constructor.
		 */
		public function SoundManager()
		{
			soundOn       = true;
			musicOn       = true;
			globalOn      = true;
			sounds        = new Dictionary();
			soundVolume   = new SoundTransform(1);
			musicVolume   = new SoundTransform(1);
		}
		
		/**
		 * Plays a sound effect and saves its channel.
		 * 
		 * @param className Class name of sound.
		 * @return The generated sound channel.
		 */
		public function playSoundByClass(className:Class, loops:uint=0):SoundChannel
		{
			if (soundOn)
			{
				var sound : Sound = sounds[className];
				
				if (!sound)
				{
					sound = new className() as Sound;
					sounds[className] = sound;
				}
				
				return sound.play(0, loops, soundVolume);
			}
			return null;
		}
		
		/**
		 * Plays a music and saves its channel.
		 * 
		 * @param className Class name of sound.
		 * @param loops Loops.
		 * @return The generated sound channel.
		 */
		public function playMusicByClass(className:Class, loops:uint):SoundChannel
		{
			if (musicOn)
			{
				if (musicChannel) musicChannel.stop();
				var channel : SoundChannel = playSoundByClass(className, loops);
				musicChannel = channel;
				musicSound   = sounds[className];
				musicLoops   = loops;
				return channel;
			}
			return null;
		}
		
		/**
		 * Stops the currently playing music.
		 */
		public function stopMusic():void
		{
			if (musicChannel)
			{
				musicChannel.stop();
				musicChannel = null;
				musicSound   = null;
			}
		}
		
		/**
		 * Stops or allows all sounds.
		 * 
		 @param value True for muted sound/music, false for otherwise.
		 */
		public function setGlobalSound(value:Boolean):void
		{
			globalOn = value;
			if (!globalOn)
			{
				SoundMixer.soundTransform = new SoundTransform(0);
				if (musicChannel) musicChannel.stop();
			}
			else
			{
				SoundMixer.soundTransform = new SoundTransform(1);
				if (musicChannel)
				{
					musicSound.play(0, musicLoops, musicVolume);
				}
			}
		}
		
		/**
		 * Toggles the sound on/off.
		 */
		public function toggleSound():void
		{
			setSound(!soundOn);
		}
		
		/**
		 * Toggles the music on/off.
		 */
		public function toggleMusic():void
		{
			setMusic(!musicOn);
		}
		
		/**
		 * Sets sound status.
		 * 
		 * @param value True for muted sound, false for otherwise.
		 */
		public function setSound(value:Boolean):void
		{
			soundOn = value;
			if (!soundOn)
			{
				soundVolume.volume = 0;
			}
			else
			{
				soundVolume.volume = 1;
			}
		}
		
		/** 
		 * Sets music status.
		 * 
		 * @param value True for muted music, false for otherwise.
		 */
		public function setMusic(value:Boolean):void
		{
			musicOn = value;
			if (!musicOn)
			{
				musicVolume.volume = 0;
				if (musicChannel) musicChannel.stop();
			}
			else
			{
				musicVolume.volume = 1;
				if (musicChannel)
				{
					musicChannel = musicSound.play(0, musicLoops, musicVolume);
				}
			}
		}
		
		/** 
		 * Singelton getter.
		 */
		public static function get instance():SoundManager
		{
			if (_instance == null) 
			{
				_instance = new SoundManager();
			}
			return _instance;
		}
	}
}