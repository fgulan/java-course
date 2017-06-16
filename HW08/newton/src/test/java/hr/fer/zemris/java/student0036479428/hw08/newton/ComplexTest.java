package hr.fer.zemris.java.student0036479428.hw08.newton;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ComplexTest {

	private static final double DELTA = 1e-8;
	private Complex number;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void parserComplex() {
		number = Complex.parse("1");
		assertEquals("1+i0", number.toString());
		number = Complex.parse("-11");
		assertEquals("-11+i0", number.toString());
		number = Complex.parse("-1+i2.25");
		assertEquals("-1+i2.25", number.toString());
		number = Complex.parse("-i");
		assertEquals("0-i1", number.toString());
		number = Complex.parse("+i");
		assertEquals("0+i1", number.toString());
	}

	@Test
	public void parserException1(){
		thrown.expect(IllegalArgumentException.class);
		number = Complex.parse("a");
	}
	
	@Test
	public void parserException2(){
		thrown.expect(IllegalArgumentException.class);
		number = Complex.parse("2.52+3i");
	}
	
	@Test
	public void parserException3(){
		thrown.expect(IllegalArgumentException.class);
		number = Complex.parse("+2.52+-i3");
	}
	
	@Test
	public void parserException4(){
		thrown.expect(IllegalArgumentException.class);
		number = Complex.parse("i3+2,52");
	}
	
	@Test
	public void negateComplex() {
		number = Complex.parse("1").negate();
		assertEquals("-1-i0", number.toString());
		number = Complex.parse("-11").negate();
		assertEquals("11-i0", number.toString());
		number = Complex.parse("-1+i2.25").negate();
		assertEquals("1-i2.25", number.toString());
		number = Complex.parse("-i").negate();
		assertEquals("-0+i1", number.toString());
		number = Complex.parse("+i").negate();
		assertEquals("-0-i1", number.toString());
	}
	
	@Test
	public void moduleComplex() {
		number = Complex.parse("1");
		assertEquals(1.0, number.module(), DELTA);
		number = Complex.parse("3+i4");
		assertEquals(5.0, number.module(), DELTA);
		number = Complex.parse("-3+i4");
		assertEquals(5.0, number.module(), DELTA);
		number = Complex.parse("-1+i1");
		assertEquals(Math.sqrt(2), number.module(), DELTA);
	}
	
	@Test
	public void multiplyComplex() {
		number = Complex.parse("1").multiply(new Complex(2,0));
		assertEquals("2+i0", number.toString());
		number = Complex.parse("1-i").multiply(new Complex(1,2));
		assertEquals("3+i1", number.toString());
		number = Complex.parse("1").multiply(new Complex());
		assertEquals("0+i0", number.toString());
	}
	
	@Test
	public void addComplex() {
		number = new Complex(2,0).add(new Complex());
		assertEquals("2+i0", number.toString());
		number = new Complex(2,5).add(new Complex(-2,-3));
		assertEquals("0+i2", number.toString());
	}
	
	@Test
	public void subComplex() {
		number = new Complex(2,0).sub(new Complex());
		assertEquals("2+i0", number.toString());
		number = new Complex(2,5).sub(new Complex(-2,-3));
		assertEquals("4+i8", number.toString());
	}
	
	@Test
	public void divideComplex() {
		number = Complex.parse("1-i").divide(new Complex(1,-1));
		assertEquals("1+i0", number.toString());
		number = Complex.parse("2-i2").divide(new Complex(2,0));
		assertEquals("1-i1", number.toString());
	}
	
	@Test
	public void divideException4(){
		thrown.expect(IllegalArgumentException.class);
		number = Complex.parse("1-i").divide(new Complex());
	}
}
