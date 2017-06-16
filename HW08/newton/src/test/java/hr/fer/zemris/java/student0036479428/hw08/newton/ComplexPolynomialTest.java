package hr.fer.zemris.java.student0036479428.hw08.newton;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ComplexPolynomialTest {

	private ComplexPolynomial polynomial;
	private Complex number;

	@Before
	public void setUp() throws Exception {
		polynomial = new ComplexPolynomial(new Complex(1, 0),
				new Complex(1, 0), new Complex(1, 0), new Complex(1, 0));
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
	public void derivePolynomial() {
		ComplexPolynomial derived = polynomial.derive();
		assertEquals("(3+i0)z^2 + (2+i0)z^1 + (1+i0)", derived.toString());
		assertEquals("6+i0", derived.apply(new Complex(1,0)).toString());
	}
	
	@Test
	public void orderPolynomial() {
		ComplexPolynomial derived = polynomial.derive();
		assertEquals(3, polynomial.order());
		assertEquals(2, derived.order());
	}
	
	@Test
	public void multiplyPolynomial() {
		polynomial = new ComplexPolynomial(new Complex(1, 0), new Complex(1, 0));
		ComplexPolynomial polynomialMul = polynomial.multiply(polynomial);
		assertEquals(2, polynomialMul.order());
		assertEquals("(1+i0)z^2 + (2+i0)z^1 + (1+i0)", polynomialMul.toString());
		number = polynomialMul.apply(new Complex(5,1));
		assertEquals("35+i12", number.toString());
	}

}
