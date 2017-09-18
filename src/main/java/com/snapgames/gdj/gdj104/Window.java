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

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * the {@link Window} class to contains and display all the game.
 * 
 * @author Frédéric Delorme
 *
 */
public class Window {
	/**
	 * the internal JFrame containing the {@link Game} object.
	 */
	JFrame frame = null;

	/**
	 * The default unique constructor to initialize a {@link Window} on the
	 * <code>game</code>.
	 * 
	 * @param game
	 *            the game to display in.
	 */
	public Window(Game game) {
		game.setSize(game.getDimension());
		frame = new JFrame(game.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(game);
		frame.setLayout(new BorderLayout());
		frame.setSize(game.getDimension());
		frame.setPreferredSize(game.getDimension());
		frame.setMaximumSize(game.getDimension());
		frame.setResizable(false);
		
		// add the Game InputHandler as a KeyListener
		frame.addKeyListener(game.getInputHandler());
		
		frame.pack();
		frame.setVisible(true);
		game.setWindow(this); 
	}
}
