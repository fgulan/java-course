package hr.fer.zemris.java.student0036479428.hw07.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * Program Crypto is used for encryption/decryption files using AES algorithm
 * with 128-bit encryption key and computing SHA-256 digest of a file. Program
 * accepts two or three arguments. First argument is name of operation
 * (checksha, encrypt or decrypt). If checksha is given, then program accepts
 * two arguments where second argument is source file. If encrypt or decrypt is
 * chosen program accepts three arguments where second argument is path to a
 * source file and third argument is path to a destination file.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Crypto {

	/** Reader */
	private static BufferedReader reader;

	/**
	 * Start point of program Crypto.
	 * @param args Command line arguments.
	 * @throws IOException Error with input/output.
	 */
	public static void main(String[] args) throws IOException {
		if (!(args.length != 2 || args.length != 3)) {
			terminate("Invalid number of arguments! Please provide task name and file(s) name.");
		}

		reader = new BufferedReader(new InputStreamReader(System.in,
				StandardCharsets.UTF_8));

		if (args[0].equals("checksha")) {
			if (args.length != 2) {
				terminate("Invalid number of arguments for checksha task!");
			}
			checkSHA(args[1]);
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			fileCrypt(args[0], args[1], args[2]);
		} else {
			System.out.println("Invalid operation!");
		}
		reader.close();
	}

	/**
	 * Method used for calculating SHA-256 for a given file.
	 * @param file Path to a file.
	 * @throws IOException Error with reading a file.
	 */
	private static void checkSHA(String file) throws IOException {
		System.out.printf("Please provide expected sha-256 digest for " + file
				+ ":%n> ");
		String excDigest = reader.readLine();

		CheckSHA256 sha256;
		try {
			sha256 = new CheckSHA256(file);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Unable to calculate SHA-256 hash over given file.");
			return;
		}
		sha256.calculateDigest();

		if (excDigest.equals(sha256.getDigestToHex())) {
			System.out.println("Digesting completed. Digest of " + file
					+ " matches expected digest.");
		} else {
			System.out.printf("Digesting completed. Digest of " + file
					+ " does not match the expected digest.%nDigest was: "
					+ sha256.getDigestToHex());
		}
	}

	/**
	 * Method used for encrypting/decrypting given file.
	 * @param operation encrypt or decrypt.
	 * @param srcFile Source file path.
	 * @param destFile Destination file path.
	 * @throws IOException Error with reading a file.
	 */
	private static void fileCrypt(String operation, String srcFile,
			String destFile) throws IOException {
		boolean encrypt = false;
		if (operation.equals("encrypt")) {
			encrypt = true;
		}
		System.out
				.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n>");
		String password = reader.readLine();

		System.out
				.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n>");
		String iVector = reader.readLine();

		try {
			FileCrypto encryption = new FileCrypto(srcFile, destFile, password,
					iVector, encrypt);
			encryption.cryptFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		if (encrypt) {
			System.out.print("Encryption completed. ");
		} else {
			System.out.print("Decryption completed. ");
		}
		System.out.println("Generated file "  + destFile + " based on file " + srcFile +".");
	}

	/**
	 * Prints the given message and terminates program.
	 * @param message Message to print.
	 */
	private static void terminate(String message) {
		System.out.println(message);
		System.exit(-1);
	}
}
