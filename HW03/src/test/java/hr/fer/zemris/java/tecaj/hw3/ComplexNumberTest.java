package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;
import org.junit.Assert;

public class ComplexNumberTest {
	//Define the greatest difference while two numbers are the same.
	private static final double DELTA = 1e-8;

	@Test
	public void TestAllComplexNumberGetters() {
		ComplexNumber testNumber = new ComplexNumber(3, 4);
		Assert.assertEquals(3, testNumber.getReal(), DELTA);
		Assert.assertEquals(4, testNumber.getImaginary(), DELTA);
		Assert.assertEquals(5, testNumber.getMagnitude(), DELTA);
		Assert.assertEquals(Math.atan2(4, 3), testNumber.getAngle(), DELTA);
		
		testNumber = ComplexNumber.fromImaginary(2.56);
		Assert.assertEquals(0, testNumber.getReal(), DELTA);
		Assert.assertEquals(2.56, testNumber.getImaginary(), DELTA);
		
		testNumber = ComplexNumber.fromReal(-3.18);
		Assert.assertEquals(-3.18, testNumber.getReal(), DELTA);
		Assert.assertEquals(0, testNumber.getImaginary(), DELTA);
	}
	
	@Test
	public void TestParsingStringToComplexNumber(){
		ComplexNumber testNumber = ComplexNumber.parse("2.889855+3.6545i");
		Assert.assertEquals(2.889855, testNumber.getReal(), DELTA);
		Assert.assertEquals(3.6545, testNumber.getImaginary(), DELTA);
		
		testNumber = ComplexNumber.parse("i");
		Assert.assertEquals(0, testNumber.getReal(), DELTA);
		Assert.assertEquals(1, testNumber.getImaginary(), DELTA);
		
		testNumber = ComplexNumber.parse("-2.898783");
		Assert.assertEquals(-2.898783, testNumber.getReal(), DELTA);
		Assert.assertEquals(0, testNumber.getImaginary(), DELTA);
	}

	@Test(expected=ComplexNumberException.class)
	public void testParsingInvalidComplexNumber_0() {
		ComplexNumber.parse("2.889855i+3.6545i");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestParsingInvalidComplexNumber_1() {
		ComplexNumber.parse("2.889855+3.6545");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestParsingInvalidComplexNumber_2() {
		ComplexNumber.parse("2.56+-3i");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestParsingInvalidComplexNumber_3() {
		ComplexNumber.parse("2.56+-");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestParsingInvalidComplexNumber_4() {
		ComplexNumber.parse("-2.56+-");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestParsingInvalidComplexNumber_5() {
		ComplexNumber.parse("-2.56-3.52a");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestParsingInvalidComplexNumber_6() {
		ComplexNumber.parse("+2+2+2+5");
	}
	
	@Test
	public void TestCreatingComplexNumberFromMagnitudeAndAngle() {
		ComplexNumber testNumber = ComplexNumber.fromMagnitudeAndAngle(5, 0);
		Assert.assertEquals(5, testNumber.getReal(), DELTA);
		Assert.assertEquals(0, testNumber.getImaginary(), DELTA);
		
		testNumber = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI/2);
		Assert.assertEquals(0, testNumber.getReal(), DELTA);
		Assert.assertEquals(5, testNumber.getImaginary(), DELTA);
		
		testNumber = ComplexNumber.fromMagnitudeAndAngle(7, Math.PI/3);
		Assert.assertEquals(7.0/2.0, testNumber.getReal(), DELTA);
		Assert.assertEquals(7*Math.sqrt(3)/2, testNumber.getImaginary(), DELTA);
	}
	
	@Test
	public void TestAddingTwoComplexNumbers() {
		ComplexNumber testNumber = new ComplexNumber(1.256, 9.758);
		ComplexNumber result = testNumber.add(new ComplexNumber(2.76, 3.65));
		Assert.assertEquals(4.016, result.getReal(), DELTA);
		Assert.assertEquals(13.408, result.getImaginary(),DELTA);
	}
	
	@Test
	public void TestSubOfTwoComplexNumbers() {
		ComplexNumber testNumber = new ComplexNumber(1.256, 9.758);
		ComplexNumber result = testNumber.sub(new ComplexNumber(2.76, 3.65));
		Assert.assertEquals(-1.504, result.getReal(), DELTA);
		Assert.assertEquals(6.108, result.getImaginary(),DELTA);
	}
	
	@Test
	public void TestParsingComplexNumberToString() {
		ComplexNumber testNumber = new ComplexNumber(1, 2);
		Assert.assertEquals("1 + 2i", testNumber.toString());
		
		testNumber = new ComplexNumber(-1.256, -1);
		Assert.assertEquals("-1,256 - 1i", testNumber.toString());
		
		testNumber = new ComplexNumber(-1.256, 0);
		Assert.assertEquals("-1,256 + 0i", testNumber.toString());
		
		testNumber = new ComplexNumber(0, -13.5892);
		Assert.assertEquals("0 - 13,589i", testNumber.toString());
	}

	@Test
	public void TestMultiplyingTwoComplexNumber() {
		ComplexNumber testNumber = new ComplexNumber(1, 2);
		ComplexNumber result = testNumber.mul(new ComplexNumber(5, 6));
		Assert.assertEquals(-7, result.getReal(), DELTA);
		Assert.assertEquals(16, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(0, 2);
		result = testNumber.mul(new ComplexNumber(0, 6));
		Assert.assertEquals(-12, result.getReal(), DELTA);
		Assert.assertEquals(0, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(1.256,0);
		result = testNumber.mul(new ComplexNumber(0, 6));
		Assert.assertEquals(0, result.getReal(), DELTA);
		Assert.assertEquals(7.536, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(1.256,0);
		result = testNumber.mul(new ComplexNumber(5, 0));
		Assert.assertEquals(6.28, result.getReal(), DELTA);
		Assert.assertEquals(0, result.getImaginary(), DELTA);
	}
	
	@Test
	public void TestDivisionOfTwoComplexNumber() {
		ComplexNumber testNumber = new ComplexNumber(1, 2);
		ComplexNumber result = testNumber.div(new ComplexNumber(5, 6));
		Assert.assertEquals(17.0/61, result.getReal(), DELTA);
		Assert.assertEquals(4.0/61, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(0, 2);
		result = testNumber.div(new ComplexNumber(0, 6));
		Assert.assertEquals(1.0/3, result.getReal(), DELTA);
		Assert.assertEquals(0, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(1.25,0);
		result = testNumber.div(new ComplexNumber(0, 4));
		Assert.assertEquals(0, result.getReal(), DELTA);
		Assert.assertEquals(-0.3125, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(1.25,0);
		result = testNumber.div(new ComplexNumber(4, 0));
		Assert.assertEquals(0.3125, result.getReal(), DELTA);
		Assert.assertEquals(0, result.getImaginary(), DELTA);
	}
	
	@Test(expected=ComplexNumberException.class)
	public void TestDivisionByZeroComplexNumber() {
		ComplexNumber testNumber = new ComplexNumber(1, 2);
		testNumber.div(new ComplexNumber(0, 0));
	}
	
	@Test
	public void TestPowerOfComplexNumber(){
		ComplexNumber testNumber = new ComplexNumber(5, 2);
		ComplexNumber result = testNumber.power(0);
		Assert.assertEquals(1, result.getReal(), DELTA);
		Assert.assertEquals(0, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(1, 2);
		result = testNumber.power(1);
		Assert.assertEquals(1, result.getReal(), DELTA);
		Assert.assertEquals(2, result.getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(3, 2);
		result = testNumber.power(2);
		Assert.assertEquals(5, result.getReal(), DELTA);
		Assert.assertEquals(12, result.getImaginary(), DELTA);
	}

	@Test(expected=ComplexNumberException.class)
	public void TestInavlidPowerOfComplexnumber() {
		ComplexNumber testNumber = new ComplexNumber(1, 2);
		testNumber.power(-5);
	}
	
	@Test
	public void TestRootOfComplexNumber() {
		ComplexNumber testNumber = new ComplexNumber(3, 4);
		ComplexNumber[] result = testNumber.root(2);
		Assert.assertEquals(2, result[0].getReal(), DELTA);
		Assert.assertEquals(1, result[0].getImaginary(), DELTA);
		Assert.assertEquals(-2, result[1].getReal(), DELTA);
		Assert.assertEquals(-1, result[1].getImaginary(), DELTA);
		
		testNumber = new ComplexNumber(-119, -120);
		result = testNumber.root(4);
		Assert.assertEquals(3, result[0].getReal(), DELTA);
		Assert.assertEquals(-2, result[0].getImaginary(), DELTA);
		Assert.assertEquals(2, result[1].getReal(), DELTA);
		Assert.assertEquals(3, result[1].getImaginary(), DELTA);
		Assert.assertEquals(-3, result[2].getReal(), DELTA);
		Assert.assertEquals(2, result[2].getImaginary(), DELTA);
		Assert.assertEquals(-2, result[3].getReal(), DELTA);
		Assert.assertEquals(-3, result[3].getImaginary(), DELTA);
	}

	@Test(expected=ComplexNumberException.class)
	public void TestInavlidRootOfComplexNumber() {
		ComplexNumber testNumber = new ComplexNumber(1, 2);
		testNumber.root(-5);
	}
}
