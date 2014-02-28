package fivel;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The main class for the Fivel back end. 
 * An Applet is used in order to deploy the code into a web page. 
 * This class holds the game component and also supplies an interface to the Front end (the Flash client,)
 * by sending calls to external JavaScript functions.
 * 
 * @author Shay Davidson, Moshe Lichman and Tom Teman.
 * Game Intelligence course, TAU 2010/11a.
 */
public class FivelApplet extends Applet 
{
	private FivelControl game;						 // Reference to the game control object.
	private static final long serialVersionUID = 1L; // Serialization ID. 
	
	/** 
	 * Constructor.
	 */
	public FivelApplet()
	{
		super();
		game = new FivelControl(this);
	}
	
	/**
	 * Callback function.
	 * Called from the front end when the user starts a new game.
	 */
	public void startGame()
	{
		game.startGame();
	}
	
	/**
	 * Called to the front end after startGame call with a randomly chosen starting player.
	 * 
	 * @param startingPlayer Either 0 or 1.
	 */
	public void returnStartingPlayer(int player)
	{
		try 
		{
			URL url = new URL("javascript:returnStartingPlayer(" + player + ")");
			getAppletContext().showDocument(url);
		}
		catch (MalformedURLException error) {}
	}
	
	/**
	 * Callback function.
	 * Called from the front end when the a move is performed.
	 * 
	 * @param player The number of the player performing the move.
	 * @param i Horizontal coordinate where a piece was placed.
	 * @param j Horizontal coordinate where a piece was placed.
	 * @param tile The number of the tile that was moved.
	 */
	public void performMove(int player, int i, int j, int tile)
	{
		game.performMove(player, i, j, tile);
	}
	
	/**
	 * Callback function.
	 * Called from the front end when the an AI move is requested.
	 * 
	 * @param player The number of the player to generate a move to.
	 * @param diff The difficulty level of the requesting player.
	 * @param turns The current mutual turn (to choose depth).
	 */
	public void requestMove(int player, int diff, int turns)
	{
		game.requestMove(player, diff, turns);
	}
	
	/**
	 * Called to the front end after a requestMove call, with a generated move.
	 * 
	 * @param player The number of the player performing the move.
	 * @param i Horizontal coordinate where a piece was placed.
	 * @param j Horizontal coordinate where a piece was placed.
	 * @param tile The number of the tile that was moved.
	 */
	public void returnMove(int player, int i, int j, int tile)
	{
		try 
		{
			URL url = new URL("javascript:returnMove(" + player + "," + i + "," + j + "," + tile + ")");
			getAppletContext().showDocument(url);
		}
		catch (MalformedURLException error) {}
	}
	
	/**
	 * Called to the front end after a performMove call to confirm the move. 
	 * Also sends if a player has won.
	 * 
	 * @param victoryPlayer The end state of the game (NONE, DRAW, PL0 or PL1).
	 * @param i1-i5 I coordinates for winning pieces.
	 * @param j1-j5 J coordinates for winning pieces.
	 * @param x1-x5 I coordinates for winning pieces.
	 * @param y1-y5 J coordinates for winning pieces.
	 */
	public void confirmMove(int endState, int i1, int j1, int i2, int j2, int i3, int j3, int i4, int j4, int i5, int j5, 
							int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int x5, int y5)
	{
		String confirmMsg = "javascript:confirmMove("+endState+","+i1+","+j1+","+i2+","+j2+","+i3+","+j3+","+i4+","+j4+","+i5+","+j5+
															   ","+x1+","+y1+","+x2+","+y2+","+x3+","+y3+","+x4+","+y4+","+x5+","+y5+")";
		try 
		{
			URL url = new URL(confirmMsg);
			getAppletContext().showDocument(url);
		}
		catch (MalformedURLException error) {}
	}
} 