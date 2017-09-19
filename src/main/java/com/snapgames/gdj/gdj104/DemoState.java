/**
 * 
 */
package com.snapgames.gdj.gdj104;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.snapgames.gdj.gdj104.statemachine.AbstractGameState;
import com.snapgames.gdj.gdj104.statemachine.GameState;

/**
 * @author frederic
 *
 */
public class DemoState extends AbstractGameState implements GameState {

	/**
	 * objects to be animated on the game display.
	 */
	// Object moved by player
	private GameObject player = null;
	// list of other entities to demonstrate GameObject usage.
	private List<GameObject> entities = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj104.statemachine.GameState#initialize(com.snapgames.gdj.
	 * gdj104.Game)
	 */
	@Override
	public void initialize(Game game) {

		// prepare Game objects
		player = new GameObject("player", game.getWidth() / 2, game.getHeight() / 2, 32, 32, 1, 1, Color.BLUE);
		player.hSpeed = 0.6f;
		player.vSpeed = 0.3f;
		player.priority = 1;
		player.layer = 1;
		addObject(player);

		for (int i = 0; i < 3; i++) {
			layers[i] = true;
		}

		for (int i = 0; i < 10; i++) {

			GameObject entity = new GameObject("entity_" + i, game.getWidth() / 2, game.getHeight() / 2, 32, 32, 1, 1,
					Color.RED);
			entity.dx = ((float) Math.random() / 2) - 0.1f;
			entity.dy = ((float) Math.random() / 2) - 0.1f;

			if (i < 5) {
				entity.layer = 2;
				entity.color = Color.MAGENTA;
			} else {
				entity.layer = 3;
				entity.color = Color.CYAN;
			}
			entities.add(entity);
			addObject(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.gdj104.statemachine.GameState#input(com.snapgames.gdj.
	 * gdj104.Game, com.snapgames.gdj.gdj104.InputHandler)
	 */
	@Override
	public void input(Game game, InputHandler input) {
		KeyEvent k = input.getEvent();
		if (k != null) {
			switch (k.getKeyCode()) {
			case KeyEvent.VK_1:
				layers[0] = !layers[0];
				break;
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_2:
				layers[1] = !layers[1];
				break;
			case KeyEvent.VK_NUMPAD3:
			case KeyEvent.VK_3:
				layers[2] = !layers[2];
				break;
			default:
				break;
			}
		}
		inputPlayer(input);
	}

	/**
	 * Manage input for Player.
	 * 
	 * @param player
	 */
	private void inputPlayer(InputHandler inputHandler) {
		// left / right
		if (inputHandler.getKeyPressed(KeyEvent.VK_LEFT)) {
			player.dx = -player.hSpeed;

		} else if (inputHandler.getKeyPressed(KeyEvent.VK_RIGHT)) {
			player.dx = +player.hSpeed;
		} else {
			if (player.dx != 0) {
				player.dx *= 0.980f;
			}
		}

		// up / down
		if (inputHandler.getKeyPressed(KeyEvent.VK_UP)) {
			player.dy = -player.vSpeed;
		} else if (inputHandler.getKeyPressed(KeyEvent.VK_DOWN)) {
			player.dy = +player.vSpeed;
		} else {
			if (player.dy != 0) {
				player.dy *= 0.980f;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj104.statemachine.GameState#update(com.snapgames.gdj.
	 * gdj104.Game, long)
	 */
	@Override
	public void update(Game game, long dt) {
		for (GameObject o : objects) {
			o.update(game, dt);
		}

		int winborder = 16;
		int wl = winborder;
		int wr = game.getWidth() - player.width - winborder;
		int wt = winborder;
		int wb = game.getHeight() - player.height - winborder;

		// player limit to border window
		if (player.x < wl)
			player.x = wl;
		if (player.y < wt)
			player.y = wt;
		if (player.x > wr)
			player.x = wr;
		if (player.y > wb)
			player.y = wb;

		for (GameObject o : entities) {
			if (o.x <= wl || o.x >= wr) {
				o.dx = -Math.signum(o.dx) * o.hSpeed;
			}
			if (o.y <= wt || o.y >= wb) {
				o.dy = -Math.signum(o.dy) * o.vSpeed;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj104.statemachine.GameState#render(com.snapgames.gdj.
	 * gdj104.Game, java.awt.Graphics2D)
	 */
	@Override
	public void render(Game game, Graphics2D g) {
		if (!objects.isEmpty()) {
			for (GameObject o : objects) {
				if (layers[o.layer - 1]) {
					o.draw(game, g);
					if (game.isDebug()) {
						RenderHelper.drawDebug(g, o);
					}
				}
			}
		}
	}

	/**
	 * @return the layers
	 */
	public boolean[] getLayers() {
		return layers;
	}

}
