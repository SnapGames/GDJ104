/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj104;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A class to help on rendering things about text and some special debug info.
 * 
 * @author Frédéric Delorme
 *
 */
public class RenderHelper {
	/**
	 * Display Help message.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public static void displayHelp(Game game, Graphics2D g, int x, int y) {
		g.setColor(new Color(.5f, .5f, .5f, .3f));
		g.fillRect(x - 10, y - 16, 248, 132);
		g.setColor(Color.WHITE);
//		drawShadowString(g, "[" + showBoolean(game.getLayers()[0]) + "] 1: show/hide layer 1", x, y, Color.WHITE,
//				Color.BLACK);
//		drawShadowString(g, "[" + showBoolean(game.getLayers()[1]) + "] 2: show/hide layer 2", x, y + 16, Color.WHITE,
//				Color.BLACK);
//		drawShadowString(g, "[" + showBoolean(game.getLayers()[2]) + "] 3: show/hide layer 3", x, y + 32, Color.WHITE,
//				Color.BLACK);
//		drawShadowString(g, "[" + showBoolean(game.isDebug()) + "] D: display debug info", x, y + 48, Color.WHITE,
//				Color.BLACK);
		drawShadowString(g, "[" + showBoolean(game.isPause()) + "] P/PAUSE: pause the computation", x, y + 64,
				Color.WHITE, Color.BLACK);
		drawShadowString(g, "[" + showBoolean(game.isHelp()) + "] H: display this help", x, y + 80, Color.WHITE,
				Color.BLACK);
		drawShadowString(g, "   CTRL+S: save a screenshot", x, y + 96, Color.WHITE, Color.BLACK);
		drawShadowString(g, "   Q/ESCAPE: Escape the demo", x, y + 112, Color.WHITE, Color.BLACK);
	}

	private static String showBoolean(boolean b) {

		return (b ? "X" : "_");
	}

	/**
	 * Display String with front and back color.
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param front
	 * @param back
	 */
	public static void drawShadowString(Graphics2D g, String text, int x, int y, Color front, Color back) {
		g.setColor(back);
		g.drawString(text, x - 1, y + 1);
		g.drawString(text, x - 1, y - 1);
		g.drawString(text, x + 1, y + 1);
		g.drawString(text, x + 1, y - 1);
		g.setColor(front);
		g.drawString(text, x, y);
	}

	/**
	 * Display debug information.
	 * 
	 * @param g
	 * @param o
	 */
	public static void drawDebug(Graphics2D g, GameObject o) {

		int pane_x = (int) o.x + (int) o.width + 10;
		int pane_y = (int) o.y + (int) o.height + 10;
		int link = 4;

		g.setColor(new Color(0.5f, .5f, .5f, .6f));
		g.fillRect(pane_x + link, pane_y + link, 150, 4 * 16 + 8);

		g.setColor(Color.YELLOW);
		g.drawRect((int) o.x, (int) o.y, o.width, o.height);
		g.drawRect(pane_x + link, pane_y + link, 150, 4 * 16 + 8);
		g.drawLine((int) o.x + o.width, (int) o.y + o.height, (int) pane_x + link, pane_y + link);

		g.drawString(o.name, pane_x + link + 10, pane_y + link + 16);
		g.drawString(String.format("pos:(%4.2f,%4.2f)", o.x, o.y), pane_x + link + 10, pane_y + link + 2 * 16);
		g.drawString(String.format("spd:(%4.2f,%4.2f)", o.dx, o.dy), pane_x + link + 10, pane_y + link + 3 * 16);
		g.drawString(String.format("lyr,prio(:(%d,%d)", o.layer, o.priority), pane_x + link + 10,
				pane_y + link + 4 * 16);
	}
}
