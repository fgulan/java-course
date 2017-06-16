package hr.fer.zemris.java.student0036479428.hw06;

import static org.junit.Assert.*;
import org.junit.Test;

public class CijeliBrojeviTest {


	@Test
	public void parnostBroja() {
		assertTrue(CijeliBrojevi.jeParan(2));
		assertTrue(CijeliBrojevi.jeNeparan(3));
		assertFalse(CijeliBrojevi.jeNeparan(2));
		assertFalse(CijeliBrojevi.jeParan(3));
	}

	@Test
	public void prostBroj() {
		assertTrue(CijeliBrojevi.jeProst(2));
		assertTrue(CijeliBrojevi.jeProst(37));
		assertFalse(CijeliBrojevi.jeProst(4));
		assertFalse(CijeliBrojevi.jeProst(81));
		assertFalse(CijeliBrojevi.jeProst(25));
	}
}
