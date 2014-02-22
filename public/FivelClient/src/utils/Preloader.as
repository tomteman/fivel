package utils
{	
	import flash.display.MovieClip;
	
	import mx.preloaders.SparkDownloadProgressBar;

	/**
	 * Preloader for Fivel's client.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class Preloader extends SparkDownloadProgressBar
	{
		protected var graphic : MovieClip; // Loader graphic.
		
		/**
		 * Constructor.
		 */
		public function Preloader() {}
		
		/**
		 * Draws the preloader.
		 */
		protected override function createChildren():void
		{
			if (!graphic) 
			{
				graphic = new GFX_Loader();
				addChild(graphic);
			}
		}
		
		/**
		 * Handles content loading.
		 * 
		 * @param completed How many bytes loaded.
		 * @param total Total bytes to be loaded.
		 */
		protected override function setDownloadProgress(completed:Number, total:Number):void 
		{
			if (graphic)
			{
				graphic.bar.mask.scaleX = completed/total;
			}
		}
		
		/**
		 * Handles application loading when it's done. 
		 * Should be left empty.
		 * 
		 * @param completed How many bytes loaded.
		 * @param total Total bytes to be loaded.
		 */
		protected override function setInitProgress(completed:Number, total:Number):void {}
	}
}
