package hr.fer.zemris.java.tecaj_15;

import java.security.MessageDigest;

/**
 * JPAUtilities class is consisted of few static methods used in blog web
 * application.
 * 
 * @author Filip
 *
 */
public class JPAUtilities {

    /**
     * Returns hashed value of given password using SHA-1 algorithm.
     * 
     * @param password Input password.
     * @return Hashed password using SHA-1 algorithm.
     */
    public static String getPasswordHash(String password) {
        String passwordHash = null;
        MessageDigest shaDigest = null;
        try {
            shaDigest = MessageDigest.getInstance("SHA-1");
            shaDigest.reset();
            shaDigest.update(password.getBytes("UTF-8"));
            passwordHash = bytesToHex(shaDigest.digest());
        } catch (Exception e1) {
        }
        return passwordHash;
    }

    /**
     * Converts given array of bytes to hex textual representation.
     *
     * @param hash Array of bytes.
     * @return Hex textual representation of given bytes array.
     */
    private static String bytesToHex(byte[] hash) {
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
