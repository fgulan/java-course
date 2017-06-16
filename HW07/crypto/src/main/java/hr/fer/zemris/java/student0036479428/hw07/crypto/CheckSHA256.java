package hr.fer.zemris.java.student0036479428.hw07.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * CheckSHA256 class computes hash digest over given file. Used hashing
 * algorithm is SHA-256.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CheckSHA256 {

	/** Byte digest */
	private byte[] digest;
	/** Message digest */
	private MessageDigest shaDigest;
	/** Source file path */
	private String filePath;

	/**
	 * CheckSHA256 class constructor.
	 * @param filePath Source file path.
	 * @throws NoSuchAlgorithmException If SHA-256 algorithm is not
	 * available in the environment.
	 */
	public CheckSHA256(String filePath) throws NoSuchAlgorithmException {
		this.filePath = filePath;
		shaDigest = MessageDigest.getInstance("SHA-256");
	}

	/**
	 * Returns digest as a string of hex values.
	 * @return Digest as a string of hex values.
	 */
	public String getDigestToHex() {
		return bytesToHex(this.digest);
	}

	/**
	 * Calculates digest for given file.
	 */
	public void calculateDigest() {
		readFile();
		this.digest = shaDigest.digest();
	}

	/**
	 * Reads given file.
	 */
	private void readFile() {
		Path path;
		try {
			path = Paths.get(filePath);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid input path");
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path.toFile());
			byte[] buff = new byte[4096];
			while (true) {
				int numOfBytes = inputStream.read(buff);
				if (numOfBytes < 1) {
					break;
				}
				shaDigest.update(buff, 0, numOfBytes);
			}
			inputStream.close();
		} catch (IOException ex) {
			System.out.println("Unable to calculate diggest!");
			return;
		}
	}

	/**
	 * Converts given array of bytes to hex textual representation.
	 * @param hash Array of bytes.
	 * @return Hex textual representation of given bytes array.
	 */
	private String bytesToHex(byte[] hash) {
		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				buffer.append('0');
			}
			buffer.append(hex);
		}

		return buffer.toString();
	}
}
