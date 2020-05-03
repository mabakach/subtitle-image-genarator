package ch.mabaka.subtitleimagegnarator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main class for subtitle image generator.
 * @author: Marc Baumgartner
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException
    {
    	// create Options object
    	Options options = new Options();

    	options.addOption("i", true, "Path to input text file.");
    	options.addOption("o", true, "Path to output folder.");
    	options.addOption("h", false, "Print this help");
    	
    	CommandLineParser parser = new DefaultParser();
    	CommandLine cmd = parser.parse( options, args);
    	
    	if (cmd.hasOption("h") || !cmd.hasOption("i") || !cmd.hasOption("o")) {
    		HelpFormatter formatter = new HelpFormatter();
    		formatter.printHelp("java -jar subtitle-image-genarator.jar", options);
    		return;
    	}
    	
    	String inputFilePath = cmd.getOptionValue("i");
    	
    	String outputFolderPath = cmd.getOptionValue("o");
    	
    	System.out.println(inputFilePath);
    	System.out.println(outputFolderPath);
    }
}
