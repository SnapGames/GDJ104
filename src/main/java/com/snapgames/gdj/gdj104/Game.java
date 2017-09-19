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

import javax.swing.JPanel;

import com.snapgames.gdj.gdj104.statemachine.GameStateManager;

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
	private boolean isPause = false;

	/**
	 * Flag to display Help.
	 */
	private boolean isHelp = false;

	/**
	 * Flag to activate screenshot recording.
	 */
	private boolean screenshot = false;

	/**
	 * Rendering interface.
	 */
	private Graphics2D g;

	private Font font;

	/**
	 * Input manager
	 */
	private InputHandler inputHandler;

	private GameStateManager gsm;

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
		gsm = new GameStateManager(this);
		gsm.addState("demo", new DemoState());

	}

	/**
	 * The main Loop !
	 */
	private void loop() {
		gsm.activate("demo");

		long currentTime = System.currentTimeMillis();
		long lastTime = currentTime;
		while (!exit) {
			currentTime = System.currentTimeMillis();
			long dt = currentTime - lastTime;

			// manage input
			input();
			if (!isPause) {
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
		gsm.input(inputHandler);
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
				isPause = !isPause;
				break;
			case KeyEvent.VK_F9:
			case KeyEvent.VK_D:
				debug = !debug;
				break;
			case KeyEvent.VK_H:
				isHelp = !isHelp;
				break;
			case KeyEvent.VK_S:
				screenshot = true;
				break;
			case KeyEvent.VK_NUMPAD1:

			default:
				break;
			}
		}

	}

	/**
	 * Update game internals
	 * 
	 * @param dt
	 */
	private void update(long dt) {
		gsm.update(dt);
	}

	/**
	 * render all the beautiful things.
	 * 
	 * @param g
	 */
	private void render(Graphics2D g) {
		// clear display
		clearBuffer(g);

		gsm.render(g);

		// Display Pause state
		if (isPause) {
			drawPause(g);
		}

		// display Help if requested
		if (isHelp) {
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
		gsm.dispose();
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
		return isPause;
	}

	/**
	 * @return the help
	 */
	public boolean isHelp() {
		return isHelp;
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

	public Graphics2D getRender() {
		return g;
	}
}
