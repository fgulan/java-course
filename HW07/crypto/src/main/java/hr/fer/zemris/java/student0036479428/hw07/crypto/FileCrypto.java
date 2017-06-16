package hr.fer.zemris.java.student0036479428.hw07.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * FileCrypto class is used for encryption/decryption of a given file. Used
 * algorithm is AES with 128-bit encryption key.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class FileCrypto {

	/** Source file path */
	private String srcFilePath;
	/** Destination file path */
	private String destFilePath;
	/** Cipher */
	private Cipher cipher;

	/**
	 * FileCrypto class constructor.
	 * @param srcFilePath Source file path.
	 * @param destFilePath Destination file path.
	 * @param keyText Given password.
	 * @param ivText Initialization vector.
	 * @param encrypt If true operation is encryption, otherwise decryption.
	 */
	public FileCrypto(String srcFilePath, String destFilePath, String keyText,
			String ivText, boolean encrypt) {
		this.srcFilePath = srcFilePath;
		this.destFilePath = destFilePath;

		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(
				hextobyte(ivText));
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
					keySpec, paramSpec);
		} catch (Exception e) {
			throw new RuntimeException("Unable to encrypt/decrypt file!");
		}
	}

	/**
	 * Encrypt/decrypt given file using buffered input and output.
	 * @throws IOException Error with reading or writing data.
	 * @throws ShortBufferException Output buffer provided by the user 
	 * is too short to hold the operation result.
	 * @throws IllegalBlockSizeException The length of data provided to
	 * a block cipher is incorrect.
	 * @throws BadPaddingException Output buffer provided by the user 
	 * is too short to hold the operation result.
	 */
	public void cryptFile() throws IOException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		FileInputStream inputStream = new FileInputStream(srcFilePath);
		FileOutputStream outputStream = new FileOutputStream(destFilePath);

		byte[] inputBuffer = new byte[4096];
		while (true) {
			int byteCount = inputStream.read(inputBuffer);
			if (byteCount < 1)
				break;

			byte[] outputBuffer = new byte[4096];
			int outputByteCount = cipher.update(inputBuffer, 0, byteCount,
					outputBuffer);
			outputStream.write(outputBuffer, 0, outputByteCount);
		}

		byte[] outputBuffer = new byte[4096];
		int outputByteCount = cipher.doFinal(outputBuffer, 0);
		outputStream.write(outputBuffer, 0, outputByteCount);
		inputStream.close();
		outputStream.close();
	}

	/**
	 * Converts given string with hex values to array of bytes.
	 * @param input Input string.
	 * @return Array of bytes.
	 * @throws IllegalArgumentException If given string does not contains
	 * hex values or number of values is not even number.
	 */
	public static byte[] hextobyte(String input) {
		int len = input.length();
		if (len % 2 != 0) {
			throw new IllegalArgumentException(
					"Vector and chiper must have even length!");
		}
		if (!input.matches("\\p{XDigit}+")) {
			throw new IllegalArgumentException(
					"Given string is not consisted of hex values!");
		}
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4) + Character
					.digit(input.charAt(i + 1), 16));
		}
		return data;
	}
}
