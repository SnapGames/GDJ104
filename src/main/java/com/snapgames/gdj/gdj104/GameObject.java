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
 * GameObject
 * 
 * @author Frédéric Delorme
 *
 */
public class GameObject {
	/**
	 * internal index to generate the default object name.
	 */
	private static int index = 0;

	/**
	 * default internal constants.
	 */
	/**
	 * default width for any object
	 */
	public final static int DEFAULT_WIDTH = 16;
	/**
	 * default height for any object
	 */
	public final static int DEFAULT_HEIGHT = 16;
	/**
	 * default horizontal speed for any object
	 */
	public final static float DEFAULT_HSPEED = 0.6f;
	/**
	 * default vertical speed for any object
	 */
	public final static float DEFAULT_VSPEED = 0.2f;
	/**
	 * Name of this object.
	 */
	public String name;
	/**
	 * position of this object onto the display space.
	 */
	public float x = 0, y = 0;

	/**
	 * default speed for this object.
	 */
	public float vSpeed, hSpeed;

	/**
	 * Velocity of this object.
	 */
	public float dx = 0, dy = 0;
	/**
	 * Size of this object.
	 */
	public int width = 32, height = 32;
	/**
	 * Rendering depth and priority.
	 */
	public int layer = 0, priority = 1;

	public Color color = Color.GREEN;

	/**
	 * Default constructor for this GameObject.
	 */
	public GameObject() {
		super();
		index++;
	}

	/**
	 * Create a GameObject with <code>name</code>, position
	 * (<code>x</code>,<code>y</code>) size (<code>width</code>,<code>height</code>)
	 * on a <code>layer</code> width a rendering <code>priority</code> and a
	 * <code>color</code>.
	 * 
	 * @param name
	 *            the name for this object.
	 * @param x
	 *            x position in the (x,y) for this object
	 * @param y
	 *            y position in the (x,y) for this object
	 * @param width
	 *            width of the object
	 * 
	 * @param height
	 *            height of the object
	 * @param layer
	 *            layer where to render this object
	 * @param priority
	 *            priority to sort the rendering position of this object.
	 */
	public GameObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		this(name, x, y, 0, 0);
		this.width = width;
		this.height = height;
		this.layer = layer;
		this.priority = priority;
		this.color = color;
	}

	/**
	 * Create a GameObject with <code>name</code>, position
	 * (<code>x</code>,<code>y</code>) with a velocity of
	 * (<code>dx</code>,<code>dy</code>).
	 * 
	 * @param name
	 *            the name for this object.
	 * @param x
	 *            x position in the (x,y) for this object
	 * @param y
	 *            y position in the (x,y) for this object
	 * @param dx
	 *            velocity on x direction
	 * @param dy
	 *            velocity on y direction.
	 */
	public GameObject(String name, int x, int y, int dx, int dy) {
		super();
		// if name is null, generate a default name.
		this.name = (name == null || name.equals("") ? "noname_" + index : name);
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.hSpeed = DEFAULT_HSPEED;
		this.vSpeed = DEFAULT_VSPEED;

	}

	/**
	 * Update object position.
	 * 
	 * @param game
	 * @param dt
	 */
	public void update(Game game, long dt) {
		// compute basic physic mechanic
		x += dx * dt;
		y += dy * dt;

		// limit speed
		if (Math.abs(dx) < 0.01) {
			dx = 0.0f;
		}
		if (Math.abs(dy) < 0.01) {
			dy = 0.0f;
		}

	}

	/**
	 * Draw object
	 * 
	 * @param game
	 * @param g
	 */
	public void draw(Game game, Graphics2D g) {
		
		g.setColor(color);
		g.fillRect((int) x, (int) y, width, height);
		// Extended object will use their own draw process.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameObject [name=").append(name).append(", x=").append(x).append(", y=").append(y)
				.append(", vSpeed=").append(vSpeed).append(", hSpeed=").append(hSpeed).append(", dx=").append(dx)
				.append(", dy=").append(dy).append(", width=").append(width).append(", height=").append(height)
				.append(", layer=").append(layer).append(", priority=").append(priority).append(", color=")
				.append(color).append("]");
		return builder.toString();
	}

}
