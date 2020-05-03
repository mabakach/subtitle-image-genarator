package ch.mabaka.subtitleimagegnarator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main class for subtitle image generator.
 * 
 * @author: Marc Baumgartner
 *
 */
public class App {
	private static final String PARAMETER_INPUT_FILE_PATH = "i";
	private static final String PARAMETER_OUTPUT_PATH = "o";
	private static final String PARAMETER_HELP = "h";
	private static final String PARAMETER_SUBTITLE_BAR_HEIGHT = "sh";
	private static final String PARAMETER_IMAGE_HEIGTH = "ih";
	private static final String PARAMETER_IMAGE_WIDTH = "iw";
	private static final String PARAMETER_FONT_NAME = "fn";
	private static final String PARAMETER_FONT_SIZE = "fs";

	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
		// create Options object
		Options options = new Options();

		options.addOption(PARAMETER_INPUT_FILE_PATH, true, "Path to input text file.");
		options.addOption(PARAMETER_OUTPUT_PATH, true, "Path to output folder.");
		options.addOption(PARAMETER_FONT_NAME, true, "Font name (optional, default is Arial Rounded)");
		options.addOption(PARAMETER_FONT_SIZE, true, "Font size in pixel (optional, default is 48)");
		options.addOption(PARAMETER_IMAGE_WIDTH, true, "Image width in pixel (optional, default is 1980)");
		options.addOption(PARAMETER_IMAGE_HEIGTH, true, "Image height in pixel (optional, default is 1080)");
		options.addOption(PARAMETER_SUBTITLE_BAR_HEIGHT, true, "Sub title bar height in pixel (optional, default is 170)");
		options.addOption(PARAMETER_HELP, false, "Print this help");

		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		if (cmd.hasOption(PARAMETER_HELP) || !cmd.hasOption(PARAMETER_INPUT_FILE_PATH) || !cmd.hasOption(PARAMETER_OUTPUT_PATH)) {
			printHelp(options);
			return;
		}

		String inputFilePath = cmd.getOptionValue(PARAMETER_INPUT_FILE_PATH);

		String outputFolderPath = cmd.getOptionValue(PARAMETER_OUTPUT_PATH);

		File inputFile = new File(inputFilePath);
		if (!inputFile.exists()) {
			System.out.println("Input file not found!");
			printHelp(options);
		}

		File outputFolder = new File(outputFolderPath);
		if (!outputFolder.exists()) {
			if(!outputFolder.mkdirs()) {
				System.out.println("Output folder does not exist and could not be created!");
			}
		}
		
		// defaults
		String fontName = "Arial Rounded";
		int fontSize = 48;
		int imageWidth = 1980;
		int imageHeight = 1080;
		int subtitleBarHeight = 170;
		
		if(cmd.hasOption(PARAMETER_FONT_NAME)) {
			fontName = cmd.getOptionValue(PARAMETER_FONT_NAME);
		}
		
		if(cmd.hasOption(PARAMETER_FONT_SIZE)) {
			String optionFs = cmd.getOptionValue(PARAMETER_FONT_SIZE);
			try {
				fontSize = Integer.parseInt(optionFs);
			} catch (NumberFormatException nfe) {
				System.out.println("Could not parse font size " + fontSize);
				printHelp(options);
			}
		}
		
		if(cmd.hasOption(PARAMETER_IMAGE_WIDTH)) {
			String optionIw = cmd.getOptionValue(PARAMETER_IMAGE_WIDTH);
			try {
				imageWidth = Integer.parseInt(optionIw);
			} catch (NumberFormatException nfe) {
				System.out.println("Could not parse image width " + imageWidth);
				printHelp(options);
			}
		}
		
		if(cmd.hasOption(PARAMETER_IMAGE_HEIGTH)) {
			String optionIh = cmd.getOptionValue(PARAMETER_IMAGE_HEIGTH);
			try {
				imageHeight = Integer.parseInt(optionIh);
			} catch (NumberFormatException nfe) {
				System.out.println("Could not parse image height " + imageHeight);
				printHelp(options);
			}
		}
		
		if(cmd.hasOption(PARAMETER_SUBTITLE_BAR_HEIGHT)) {
			String optionSh = cmd.getOptionValue(PARAMETER_SUBTITLE_BAR_HEIGHT);
			try {
				subtitleBarHeight = Integer.parseInt(optionSh);
			} catch (NumberFormatException nfe) {
				System.out.println("Could not parse sub title bar height " + subtitleBarHeight);
				printHelp(options);
			}
		}
		
		SubtitleGenarator genarator = new SubtitleGenarator(inputFile, outputFolder, fontName, fontSize, imageWidth, imageHeight, subtitleBarHeight);
		genarator.generateImages();
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar subtitle-image-genarator.jar", options);
	}
}
