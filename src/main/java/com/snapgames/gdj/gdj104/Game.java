/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj104;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

/**
 * the basic Game container is a JPanel child.
 * 
 * @author Frédéric Delorme
 *
 */
public class Game extends JPanel {

	public final static int WIDTH = 320;
	public final static int HEIGHT = 200;
	public final static int SCALE = 2;

	/**
	 * The title for the game instance.
	 */
	private String title = "game";

	/**
	 * Game display space dimension
	 */
	private Dimension dimension = null;

	/**
	 * Active window for this Game.
	 */
	private Window window;

	/**
	 * internal rendering buffer
	 */
	private BufferedImage image;

	/**
	 * Flag to activate debug information display.
	 */
	private boolean debug = true;

	/**
	 * flag representing the exit request status. true => exit
	 */
	private boolean exit = false;

	/**
	 * Flag to track pause request.
	 */
	private boolean pause = false;

	/**
	 * Flag to display Help.
	 */
	private boolean help = false;

	/**
	 * Flag to activate screenshot recording.
	 */
	private boolean screenshot = false;

	/**
	 * Rendering interface.
	 */
	private Graphics2D g;

	/**
	 * Input manager
	 */
	private InputHandler inputHandler;

	private boolean[] layers = new boolean[3];

	/**
	 * List of managed objects.
	 */
	private List<GameObject> objects = new ArrayList<>();

	/**
	 * objects to be animated on the game display.
	 */
	// Object moved by player
	private GameObject player = null;
	// list of other entities to demonstrate GameObject usage.
	private List<GameObject> entities = new ArrayList<>();

	private Font font;

	/**
	 * the default constructor for the {@link Game} panel with a game
	 * <code>title</code>.
	 * 
	 * @param title
	 *            the title for the game.
	 */
	private Game(String title) {
		this.title = title;
		this.dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		exit = false;
		inputHandler = new InputHandler();
	}

	/**
	 * Initialize the Game object with <code>g</code>, the Graphics2D interface to
	 * render things.
	 */
	private void initialize() {

		// Internal display buffer
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();

		font = g.getFont();

		// prepare Game objects
		player = new GameObject("player", getWidth() / 2, getHeight() / 2, 32, 32, 1, 1, Color.BLUE);
		player.hSpeed = 0.6f;
		player.vSpeed = 0.3f;
		player.priority = 1;
		player.layer = 1;
		addObject(player);

		for (int i = 0; i < 3; i++) {
			layers[i] = true;
		}

		for (int i = 0; i < 10; i++) {

			GameObject entity = new GameObject("entity_" + i, getWidth() / 2, getHeight() / 2, 32, 32, 1, 1, Color.RED);
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

	/**
	 * Add an object to the Object list and sort them according to their layer and
	 * priority.
	 * 
	 * @param object
	 */
	private void addObject(GameObject object) {
		objects.add(object);
		objects.sort(new Comparator<GameObject>() {
			public int compare(GameObject o1, GameObject o2) {
				return (o1.layer > o2.layer ? -1 : (o1.priority > o2.priority ? -1 : 1));
			};
		});
	}

	/**
	 * The main Loop !
	 */
	private void loop() {
		long currentTime = System.currentTimeMillis();
		long lastTime = currentTime;
		while (!exit) {
			currentTime = System.currentTimeMillis();
			long dt = currentTime - lastTime;

			// manage input
			input();
			if (!pause) {
				// update all game's objects
				update(dt);
			}
			// render all Game's objects
			render(g);
			// copy buffer
			drawToScreen();

			lastTime = currentTime;
		}
	}

	/**
	 * Copy buffer to window.
	 */
	private void drawToScreen() {

		// copy buffer to window.
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		if (screenshot) {
			ImageUtils.screenshot(image);
			screenshot = false;
		}
	}

	/**
	 * Manage Game input.
	 */
	private void input() {
		inputGame();
		inputPlayer(player);
	}

	/**
	 * Manage Input at game level.
	 */
	private void inputGame() {
		// Process keys
		KeyEvent k = inputHandler.getEvent();
		if (k != null) {
			switch (k.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_Q:
				setExit(true);
				break;
			case KeyEvent.VK_PAUSE:
			case KeyEvent.VK_P:
				pause = !pause;
				break;
			case KeyEvent.VK_F9:
			case KeyEvent.VK_D:
				debug = !debug;
				break;
			case KeyEvent.VK_H:
				help = !help;
				break;
			case KeyEvent.VK_S:
				screenshot = true;
				break;
			case KeyEvent.VK_NUMPAD1:
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
	}

	/**
	 * Manage input for Player.
	 * 
	 * @param player
	 */
	private void inputPlayer(GameObject player) {
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

	/**
	 * Update game internals
	 * 
	 * @param dt
	 */
	private void update(long dt) {

		for (GameObject o : objects) {
			o.update(this, dt);
		}

		int winborder = 16;
		int wl = winborder;
		int wr = this.getWidth() - player.width - winborder;
		int wt = winborder;
		int wb = this.getHeight() - player.height - winborder;

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

	/**
	 * render all the beautiful things.
	 * 
	 * @param g
	 */
	private void render(Graphics2D g) {
		// clear display
		clearBuffer(g);

		if (!objects.isEmpty()) {
			for (GameObject o : objects) {
				if (layers[o.layer - 1]) {
					o.draw(this, g);
					if (debug) {
						RenderHelper.drawDebug(g, o);
					}
				}
			}
		}

		// Display Pause state
		if (pause) {
			drawPause(g);
		}

		// display Help if requested
		if (help) {
			RenderHelper.displayHelp(this, g, 10, 20);
		}
	}

	/**
	 * @param g
	 */
	private void clearBuffer(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 * draw the Pause label.
	 * 
	 * @param g
	 */
	private void drawPause(Graphics2D g) {
		String lblPause = "Pause";

		Font bck = g.getFont();
		Font f = font.deriveFont(28.0f).deriveFont(Font.ITALIC);

		g.setFont(f);
		int lblWidth = g.getFontMetrics().stringWidth(lblPause);
		int lblHeight = g.getFontMetrics().getHeight();
		int yPos = (getHeight() - lblHeight) / 2;
		g.setColor(Color.BLUE);
		g.fillRect(0, yPos - lblHeight, getWidth(), lblHeight + 8);
		g.setColor(Color.WHITE);
		g.drawRect(-1, yPos - lblHeight, getWidth() + 1, lblHeight + 8);

		g.setColor(Color.WHITE);
		g.getFontMetrics().getHeight();
		g.drawString(lblPause, (getWidth() - lblWidth) / 2, (getHeight() - lblHeight) / 2);
		g.setFont(bck);

	}

	/**
	 * free all resources used by the Game.
	 */
	private void release() {
		objects.clear();
		window.frame.dispose();
	}

	/**
	 * the only public method to start game.
	 */
	public void run() {
		initialize();
		loop();
		release();
		System.exit(0);
	}

	/**
	 * return the title of the game.
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * return the dimension of the Game display.
	 * 
	 * @return
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * Set the active window for this game.
	 * 
	 * @param window
	 *            the window to set as active window for the game.
	 */
	public void setWindow(Window window) {
		this.window = window;
	}

	/**
	 * @return the exit
	 */
	public boolean isExit() {
		return exit;
	}

	/**
	 * @param exit
	 *            the exit to set
	 */
	public void setExit(boolean exit) {
		this.exit = exit;
	}

	/**
	 * @return the inputHandler
	 */
	public InputHandler getInputHandler() {
		return inputHandler;
	}

	/**
	 * return debug activation flag. true, visual debug is activated, false, normal
	 * rendering.
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @return the pause
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * @return the help
	 */
	public boolean isHelp() {
		return help;
	}

	/**
	 * @return the layers
	 */
	public boolean[] getLayers() {
		return layers;
	}

	/**
	 * The main entry point to start our GDJ104 game.
	 * 
	 * @param argv
	 *            list of arguments from command line.
	 */
	public static void main(String[] argv) {
		Game game = new Game("GDJ104");
		@SuppressWarnings("unused")
		Window window = new Window(game);
		game.run();
	}
}
