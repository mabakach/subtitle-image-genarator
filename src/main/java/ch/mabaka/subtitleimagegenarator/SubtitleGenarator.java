package ch.mabaka.subtitleimagegenarator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class SubtitleGenarator {

	private File inputFile;

	private File outputFolderPath;

	private int imageWidth;

	private int imageHeight;

	private int subtitleBarHeight;

	private int textStartPositionX;

	private int textStartPositionY;

	private Color subTitleBarBackgroundColor = new Color(255, 255, 255, 127);

	private Color textColor = Color.BLACK;

	private Font subtitleFont = new Font("Arial Rounded", Font.PLAIN, 48);

	private String outputFileNamePrefix = "image_";
	
	private RenderingHints renderingHints;

	public SubtitleGenarator(File inputFile, File outputFolderPath, String fontName, int fontSize, int imageWidth, int imageHeight, int subtitleBarHeight) {
		this.inputFile = inputFile;
		this.outputFolderPath = outputFolderPath;
		this.subtitleFont = new Font(fontName, Font.PLAIN, fontSize);
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
		this.subtitleBarHeight = subtitleBarHeight;
		this.textStartPositionX = imageWidth / 100;
		this.textStartPositionY = imageHeight - (subtitleBarHeight / 2) + (subtitleFont.getSize() / 2);
		renderingHints = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	}

	public void generateImages() throws FileNotFoundException, IOException {
		try (FileInputStream fis = new FileInputStream(inputFile)) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
				final long totalNumberOfLines = br.lines().count();
				fis.getChannel().position(0);
				final int numberOfDigits = getNumberOfTenBaseDigits(totalNumberOfLines);
				long currentLineNumber = 0;
				String line = null;
				while ((line = br.readLine()) != null) {
					generateImage(line, currentLineNumber, numberOfDigits);
					currentLineNumber++;
					line = br.readLine();
				}
			}
		}
	}

	private int getNumberOfTenBaseDigits(final long number) {
		int numberOfDigits = 0;
		long numberOfLines = number;
		for (; numberOfLines != 0; numberOfLines /= 10, ++numberOfDigits)
			;

		return numberOfDigits;
	}

	private void generateImage(String line, long currentLineNumber, int numberOfDigits) {
		BufferedImage image = getImage(line);
		String formatString = outputFileNamePrefix + "%0" + numberOfDigits + "d.png";
		String fileName = String.format(formatString, currentLineNumber);

		File outFile = new File(outputFolderPath.getAbsoluteFile(), fileName);
		try (FileOutputStream fos = new FileOutputStream(outFile)) {
			ImageIO.write(image, "png", fos);
		} catch (Exception e) {
			System.out.println("Could not save image: " + e.getMessage());
		}
	}

	// This method refers to the signature image to save
	private BufferedImage getImage(String line) {

		// Create a buffered image in which to draw
		BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		

		g2d.setRenderingHints(renderingHints);

		// Draw graphics
		g2d.setColor(subTitleBarBackgroundColor);
		g2d.fillRect(0, this.imageHeight - subtitleBarHeight, imageWidth, subtitleBarHeight);

		g2d.setColor(textColor);
		g2d.setFont(subtitleFont);

		g2d.drawString(line, textStartPositionX, textStartPositionY);

		// Graphics context no longer needed so dispose it
		g2d.dispose();

		return bufferedImage;
	}

}
