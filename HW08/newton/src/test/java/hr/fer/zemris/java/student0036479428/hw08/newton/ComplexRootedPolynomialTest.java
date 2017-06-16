package hr.fer.zemris.java.student0036479428.hw08.newton;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ComplexRootedPolynomialTest {

	private ComplexRootedPolynomial polynomial;
	private Complex number;

	@Before
	public void setUp() throws Exception {
		polynomial = new ComplexRootedPolynomial(Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
	}

	@Test
	public void apply() {
		number = polynomial.apply(new Complex(1,0));
		assertEquals("4+i0", number.toString());
		number = polynomial.apply(new Complex(1,-1));
		assertEquals("0-i5", number.toString());
		number = polynomial.apply(new Complex(1,-2));
		assertEquals("-12-i4", number.toString());
	}
	
	@Test
	public void order() {
		assertEquals(3, polynomial.order());
	}
	
	@Test
	public void toComplexPolynom1() {
		ComplexPolynomial poly = polynomial.toComplexPolynom();
		assertEquals(3, poly.order());
		number = polynomial.apply(Complex.ONE);
		assertEquals("4+i0", number.toString());
	}
	
	
	@Test
	public void toComplexPolynom2() {
		ComplexPolynomial poly = polynomial.toComplexPolynom();
		assertEquals("(1+i0)z^3 + (1+i0)z^2 + (1+i0)z^1 + (1+i0)", poly.toString());
	}
	
	@Test
	public void indexOfClosestRootFor() {
		int poly = polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 1e-5);
		assertEquals(0,poly);
		
		poly = polynomial.indexOfClosestRootFor(Complex.ONE, 1e-5);
		assertEquals(-1,poly);
		
		poly = polynomial.indexOfClosestRootFor(Complex.IM, 1e-5);
		assertEquals(1,poly);
		
		poly = polynomial.indexOfClosestRootFor(Complex.IM_NEG, 1e-5);
		assertEquals(2,poly);
	}
	
	@Test
	public void testToString() {
		assertEquals("[z - (-1+i0)][z - (0+i1)][z - (0-i1)]", polynomial.toString());
	}

}
