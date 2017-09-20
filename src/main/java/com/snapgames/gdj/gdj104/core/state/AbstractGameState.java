/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj104.core.state;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.snapgames.gdj.gdj104.core.Game;
import com.snapgames.gdj.gdj104.core.GameObject;

/**
 * An Abstract Game State to manage all states of the Game !
 */
public abstract class AbstractGameState implements GameState {

	protected boolean[] layers = new boolean[3];

	/**
	 * List of managed objects.
	 */
	protected List<GameObject> objects = new ArrayList<>();

	/**
	 * Add an object to the Object list and sort them according to their layer and
	 * priority.
	 * 
	 * @param object
	 */
	protected void addObject(GameObject object) {
		objects.add(object);
		objects.sort(new Comparator<GameObject>() {
			public int compare(GameObject o1, GameObject o2) {
				return (o1.layer > o2.layer ? -1 : (o1.priority > o2.priority ? -1 : 1));
			};
		});
	}

	public void dispose(Game game) {
		objects.clear();
	}

	@Override
	public void keyTyped(Game game, KeyEvent e) {
	}

	@Override
	public void keyPressed(Game game, KeyEvent e) {
	}

	@Override
	public void keyReleased(Game game, KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_Q:
			game.setExit(true);
			break;
		case KeyEvent.VK_PAUSE:
		case KeyEvent.VK_P:
			game.requestPause();
			break;
		case KeyEvent.VK_F9:
		case KeyEvent.VK_D:
			game.setDebug(!game.isDebug());
			break;
		case KeyEvent.VK_S:
			game.captureScreenshot();
			break;
		case KeyEvent.VK_NUMPAD1:

		default:
			break;
		}
	}
}
