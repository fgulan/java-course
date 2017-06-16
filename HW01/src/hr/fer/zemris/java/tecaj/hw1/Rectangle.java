package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Implements methods for getting rectangle width/height and calculating its area and perimeter.
 * Program gets two arguments from command line (width and height of rectangle).
 * If nothing is given the program asks user to enter width and height.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class Rectangle {
	/**
	 * Start point of the program.
	 * @param args Command line arguments.
	 * @throws IOException On input error.
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
		double width = 0, height = 0;
		
		if(args.length == 2) {
			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);
			if(width <= 0 || height <= 0) {
				System.err.println("Invalid arguments were provided.");
				System.exit(-1);
			}
		} else if (args.length == 1) {
			System.err.println("Invalid number of arguments was provided.");
			System.exit(-1);
		} else {
			width = getData("width", reader);
			height = getData("height", reader);
		}
		
		calculateAreaAndPerimeter(width, height);
		reader.close();
	}
	/**
	 * Gets data from standard input. Converts scanned string in the number type of a <code>double</code>.
	 * Method retrieves data until it gets a number greater than zero.
	 * @param dimension Name of data to get.
	 * @param reader Reference to buffered reader.
	 * @return Scanned number.
	 * @throws IOException On input error.
	 */
	public static double getData(String dimension, BufferedReader reader) throws IOException {
		String line;
		double data = -1;
		do {
			System.out.print("Please provide " + dimension + ": ");
			line = reader.readLine();
			if(!line.isEmpty()) {
				line.trim();
				data = Double.parseDouble(line);
				if (data < 0.0) {
					String s = Character.toUpperCase(dimension.charAt(0))+dimension.substring(1);
					System.out.println(s + " is negative.");
				} 
			} else {
				System.out.println("Nothing was given.");
			}
		} while (data <= 0.0);
		return data;
	}
	/**
	 * Calculate perimeter and area of a given rectangle. Prints calculated values on screen.
	 * @param Width Rectangle width.
	 * @param Height Rectangle height.
	 */
	public static void calculateAreaAndPerimeter(double Width, double Height) {
		Double Area = Width*Height;
		Double Perimeter = 2*Width+2*Height;
		System.out.println("You have specified a rectangle with width " +Width+ 
						   " and height " + Height + ". Its area is " + Area + " and its perimeter is " 
						   + Perimeter + ".");
	}
}