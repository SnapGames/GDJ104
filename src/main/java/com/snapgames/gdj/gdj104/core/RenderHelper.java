/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj104.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * This class is a Render helper class to draw some shiny things.
 * 
 * @author Frédéric Delorme
 *
 */
public class RenderHelper {

	/**
	 * An internal Text POsition enumeration to provide a justification pattern.
	 * 
	 * @author Frédéric Delorme
	 *
	 */
	public enum TextPosition {
		LEFT, RIGHT, CENTER
	}

	public static void display(Graphics2D g, int x, int y, Font f, Object[] objects) {
		String[] helps = new String[objects.length];
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		int maxWidth = 0;
		for (Object o : objects) {
			int strWidth = g.getFontMetrics().stringWidth(o.toString());
			if (strWidth > maxWidth) {
				maxWidth = strWidth;
			}

		}
		g.setColor(new Color(.5f, .5f, .5f, .3f));
		g.fillRect(x - 10, y - fm.getHeight(), (maxWidth + 2 * 16), (objects.length + 1) * fm.getHeight());
		g.setColor(Color.WHITE);

		int i = 0;
		for (Object o : objects) {
			helps[i] = o.toString();
			drawShadowString(g, helps[i], x + 5, y + i * fm.getHeight(), Color.WHITE, Color.BLACK,TextPosition.LEFT,2);
			i++;
		}

	}

	public static String showBoolean(boolean b) {

		return showBoolean(b, "X", "_");
	}

	public static String showBoolean(boolean b, String on, String off) {

		return (b ? on : off);
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
		drawShadowString(g, text, x, y, front, back, TextPosition.LEFT);
	}

	/**
	 * Display String with front and back color at x,y positioning the text
	 * according to TextPosition.
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param front
	 * @param back
	 * @param txtPos
	 */
	public static void drawShadowString(Graphics2D g, String text, int x, int y, Color front, Color back,
			TextPosition txtPos) {
		drawShadowString(g, text, x, y, front, back, txtPos, 1);
	}

	/**
	 * Display String with front and back color at x,y positioning the text
	 * according to TextPosition. The back color will be drawn with a border.
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param front
	 * @param back
	 * @param border
	 */
	public static void drawShadowString(Graphics2D g, String text, int x, int y, Color front, Color back,
			TextPosition txtPos, int border) {
		int textWidth = g.getFontMetrics().stringWidth(text);
		switch (txtPos) {
		case LEFT:
			break;
		case RIGHT:
			x = x - textWidth;
			break;
		case CENTER:
			x = x - (textWidth / 2);
			break;
		}
		g.setColor(back);
		for (int i = 0; i < border; i++) {
			g.drawString(text, x - i, y + i);
			g.drawString(text, x - i, y - i);
			g.drawString(text, x + i, y + i);
			g.drawString(text, x + i, y - i);
		}
		g.setColor(front);
		g.drawString(text, x, y);
	}

	/**
	 * Display debug information for the game Object.
	 * 
	 * @param g
	 *            the graphic interface to use to draw things
	 * @param o
	 *            the object to be debugged.
	 */
	public static void drawDebug(Graphics2D g, GameObject o, Font f) {

		g.setFont(f);
		int pane_padding = 4;
		int pane_x = (int) o.x + (int) o.width + pane_padding;
		int pane_y = (int) o.y + (int) o.height + pane_padding;
		int link = 2;
		int fontHeight = g.getFontMetrics().getHeight();
		int pane_width = 100;

		g.setColor(new Color(0.5f, .5f, .5f, .6f));
		g.fillRect(pane_x + link, pane_y + link, pane_width, 4 * fontHeight + fontHeight / 2);

		g.setColor(Color.YELLOW);
		g.drawRect((int) o.x, (int) o.y, o.width, o.height);
		g.drawRect(pane_x + link, pane_y + link, pane_width, 4 * fontHeight + fontHeight / 2);
		g.drawLine((int) o.x + o.width, (int) o.y + o.height, (int) pane_x + link, pane_y + link);

		g.drawString(o.name, pane_x + link + pane_padding, pane_y + link + fontHeight);
		g.drawString(String.format("pos:(%4.2f,%4.2f)", o.x, o.y), pane_x + link + pane_padding,
				pane_y + link + 2 * fontHeight);
		g.drawString(String.format("spd:(%4.2f,%4.2f)", o.dx, o.dy), pane_x + link + pane_padding,
				pane_y + link + 3 * fontHeight);
		g.drawString(String.format("lyr,prio(:(%d,%d)", o.layer, o.priority), pane_x + link + pane_padding,
				pane_y + link + 4 * fontHeight);
	}
}
