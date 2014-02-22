package utils
{	
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;

	/**
	 * A Flash context menu for Fivel.
	 * 
	 * @author Shay Davidson, Moshe Lichman and Tom Teman.
	 * Game Intelligence course, TAU 2010/11a.
	 */
	public class FivelContextMenu
	{
		/**
		 * A context menu factory.
		 */
		public static function getMenu():ContextMenu
		{
			var text        : String          = "Fivel";
			var context     : ContextMenu     = new ContextMenu();
			var contextText : ContextMenuItem = new ContextMenuItem(text, false, true, true);
			context.hideBuiltInItems();
			context.customItems.push(contextText);
			return context;
		}
		
	}
}