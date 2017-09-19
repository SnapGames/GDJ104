/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj104.core;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Image utilities
 * 
 * @author Frédéric Delorme
 *
 */
public class ImageUtils {
	/**
	 * default path to store image captures.
	 */
	private static String path = System.getProperty("user.home");

	/**
	 * Internal image counter.
	 */
	private static int recordingCount = 0;

	/**
	 * Sequence counter
	 */
	private static int seqCount = 0;

	/**
	 * Take a screenshot from the image to the default `user.dir`.
	 * 
	 * @param image
	 *            image to be saved to disk.
	 */
	public static void screenshot(BufferedImage image) {
		try {
			java.io.File out = new java.io.File(path + File.separator + "screenshot " + System.nanoTime() + ".jpg");
			javax.imageio.ImageIO.write(image, "JPG", out);
		} catch (Exception e) {
			System.err.println("Unable to write screenshot to user.dir: "+path);
		}
	}

	/**
	 * Record a sequence of image to default `user.dir`.
	 * 
	 * @param image
	 *            image to be saved to disk.
	 */
	public static void record(BufferedImage image) {
		try {
			java.io.File out = new java.io.File(
					path + File.separator + "sequence_" + seqCount + "_frame_" + recordingCount + ".jpg");
			javax.imageio.ImageIO.write(image, "JPG", out);
			recordingCount++;
		} catch (Exception e) {
		}
	}

	public static void startRecord() {
		seqCount++;
		recordingCount = 0;
	}

	public static void stopRecord() {

	}
}
