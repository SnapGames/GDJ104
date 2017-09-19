/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj104.statemachine;

import java.awt.Graphics2D;

import com.snapgames.gdj.gdj104.Game;
import com.snapgames.gdj.gdj104.InputHandler;

/**
 * An interface to design all states
 */
public interface GameState {

	/**
	 * Initialize the state on the game.
	 *
	 * @param game
	 *            the game parent of this state.
	 *
	 */
	public void initialize(Game game);

	/**
	 * manage input in this State.
	 * 
	 * @param game
	 *            the parent game of this state
	 * @param input
	 *            the input handler
	 */
	public void input(Game game, InputHandler input);

	/**
	 * update the GameState
	 * 
	 * @param game
	 *            the parent game of this state
	 * @param dt
	 *            the elapsed time since previous call.
	 */
	public void update(Game game, long dt);

	/**
	 * Rendering this state
	 * 
	 * @param game
	 * @param g
	 */
	public void render(Game game, Graphics2D g);

	/**
	 * Free some resources and close the state.
	 * 
	 * @param game
	 */
	public void dispose(Game game);
}