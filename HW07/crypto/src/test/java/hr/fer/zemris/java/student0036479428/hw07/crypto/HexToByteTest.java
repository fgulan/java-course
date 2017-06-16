package hr.fer.zemris.java.student0036479428.hw07.crypto;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class HexToByteTest {

	@Test
	public void hexToByte1() {
		byte[] array = new byte[] { (byte) 0x80, 0x53, 0x1c };
		byte[] result = FileCrypto.hextobyte("80531c");
		assertArrayEquals(array, result);
	}
	
	@Test
	public void hexToByte2() {
		byte[] array = new byte[] { (byte) 0xff, (byte) 0xac, (byte) 0xbc };
		byte[] result = FileCrypto.hextobyte("ffacbc");
		assertArrayEquals(array, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hexException1() {
		byte[] array = new byte[] { (byte) 0xff, (byte) 0xac, (byte) 0xbc };
		byte[] result = FileCrypto.hextobyte("ffacbz");
		assertArrayEquals(array, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hexException2() {
		byte[] array = new byte[] { (byte) 0xff, (byte) 0xac, (byte) 0xbc };
		byte[] result = FileCrypto.hextobyte("ffacb");
		assertArrayEquals(array, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hexException3() {
		byte[] array = new byte[] { (byte) 0xff, (byte) 0xac, (byte) 0xbc };
		byte[] result = FileCrypto.hextobyte("ffacbu");
		assertArrayEquals(array, result);
	}
}
