package hr.fer.zemris.java.tecaj_15;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestPasswordHash {

    @Test
    public void testPasswordHashTrue() {
        String hash1 = JPAUtilities.getPasswordHash("Java");
        String hash2 = JPAUtilities.getPasswordHash("Java");

        assertEquals(true, hash1.equals(hash2));
    }

    @Test
    public void testPasswordHashFalse() {
        String hash1 = JPAUtilities.getPasswordHash("Java");
        String hash2 = JPAUtilities.getPasswordHash("java123");

        assertEquals(false, hash1.equals(hash2));
    }

}
