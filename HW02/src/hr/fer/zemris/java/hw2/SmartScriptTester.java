package hr.fer.zemris.java.hw2;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Program for testing SmartScriptParser. Program gets one command line argument, a path to a file to parse.
 * If nothing is given, or given path is wrong, programs prints the message and exits.
 * Example of input - "C:\HW02\examples\doc1.txt"
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SmartScriptTester {

	/**
	 * Start point of the program.
	 * @param args Command line arguments.
	 * @throws IOException On input error.
	 */
	public static void main(String[] args ) throws IOException  {
		String filepath = null;
		String docBody = null;
		
		//Get filepath
		try {
			filepath = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please provide path to file as command line argument.");
			System.exit(-1);
		}
		
		//Read file
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8
					);
		} catch (NoSuchFileException e) {
			System.out.println("No such file. Please check provided path to file.");
			System.exit(-1);
		}
		
		//Parse document
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String text = createOriginalDoucmentBody(document);
		System.out.println(text);
		
		//Reparse parsed text
		SmartScriptParser parser2 = new SmartScriptParser(text);
		DocumentNode document2 = parser2.getDocumentNode();
		String text2 = createOriginalDoucmentBody(document2);
		
		//Compare parsed and reparsed text
		if(text2.compareTo(text) == 0) {
			System.out.println("\nParsed and reparsed documents are the same.");
		}
	}
	
	/**
	 * Creates a text representation of parsed document.
	 * @param parent Reference to DocumentNode node
	 * @return Formated text representation of parsed document.
	 */
	private static String createOriginalDoucmentBody(Node parent) {
		String nodeText = parent.asText();
		for (int i = 0; i < parent.numberOfChildern(); i++) {
			nodeText += createOriginalDoucmentBody(parent.getChild(i));
		}
		if (parent instanceof ForLoopNode) {
			nodeText += "{$ END $}";
		}
		return nodeText;
	}
}