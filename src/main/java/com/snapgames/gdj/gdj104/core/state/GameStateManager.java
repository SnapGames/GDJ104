/**
 * 
 */
package com.snapgames.gdj.gdj104.core.state;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.snapgames.gdj.gdj104.core.Game;
import com.snapgames.gdj.gdj104.core.InputHandler;

/**
 * The Game State Manager is the state machine to manage all the states of the
 * game.
 * 
 * @author Frédéric Delorme
 *
 */
public class GameStateManager {

	/**
	 * internal reference to parent game.
	 */
	private Game game;

	/**
	 * The internal buffer to store instances of the available states.
	 */
	private Map<String, GameState> states = new HashMap<>();

	/**
	 * the current active state.
	 */
	private GameState currentState = null;

	/**
	 * Initialize the Game state manager
	 * 
	 * @param game
	 *            the parent game object.
	 */
	public GameStateManager(Game game) {
		this.game = game;
		initialize();
	}

	/**
	 * Initialize internal state.
	 */
	public void initialize() {
		states = new HashMap<>();
	}

	/**
	 * Add a state to the internal buffer.
	 * 
	 * @param name
	 * @param state
	 */
	public void addState(String name, GameState state) {
		assert (name != null && !name.equals(""));
		assert (state != null);
		assert (states != null);
		states.put(name, state);
	}

	/**
	 * Activate the specific state with this <code>name</code>.
	 * 
	 * @param name
	 *            the name of the state to activate.
	 */
	public void activate(String name) {
		assert (states != null && !states.isEmpty());
		if (states.containsKey(name)) {
			currentState = states.get(name);
			currentState.initialize(game);
		}
	}

	/**
	 * Delegate input management to the state.
	 * 
	 * @param input
	 *            the input handler to connect to.
	 */
	public void input(InputHandler input) {
		assert (currentState != null);
		assert (input != null);
		currentState.input(game, input);
	}

	/**
	 * Delegate the update process to the current state.
	 * 
	 * @param dt
	 *            elapsed time since previous call.
	 */
	public void update(long dt) {
		currentState.update(game, dt);
	}

	/**
	 * Delegate the rendering process to the current state.
	 * 
	 * @param g
	 *            The Graphics2D interface to render things !
	 */
	public void render(Graphics2D g) {
		currentState.render(game, g);
	}

	public void dispose() {
		for (Entry<String, GameState> gs : states.entrySet()) {
			gs.getValue().dispose(game);
			states.remove(gs.getKey());
		}
	}

	public Game getGame() {
		return game;
	}

}
