package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Assert;
import org.junit.Test;

public class CStringTest {

	@Test
	public void TestCStringToStringMethod() {
		CString tempCString = new CString("JavaTest");
		Assert.assertEquals("JavaTest", tempCString.toString());
		
		tempCString = new CString(new char[] {'J','a','v','a','T','e','s','t'});
		Assert.assertEquals("JavaTest", tempCString.toString());
		
		tempCString = new CString(new char[] {'J','a','v','a','T','e','s','t'},0 ,4);
		Assert.assertEquals("Java", tempCString.toString());
		
		tempCString = new CString(new char[] {'J','a','v','a','T','e','s','t'},4 ,4);
		Assert.assertEquals("Test", tempCString.toString());
		
		tempCString = new CString(tempCString);
		Assert.assertEquals("Test", tempCString.toString());
	}
	
	@Test 
	public void TestCStringLengthMethod() {
		CString tempCString = new CString(new char[] {'J','a','v','a','T','e','s','t'},0 ,8);
		Assert.assertEquals("JavaTest", tempCString.toString());
		Assert.assertEquals(8, tempCString.length());
	}

	@Test
	public void TestCStringCharAtMethod() {
		CString tempCString = new CString("JavaTest");
		Assert.assertEquals('e', tempCString.charAt(5));
	}
	
	@Test
	public void TestCStringIndexOfMethod() {
		CString tempCString = new CString("JavaTest");
		Assert.assertEquals(1, tempCString.indexOf('a'));
		Assert.assertEquals(-1, tempCString.indexOf('z'));
	}

	@Test
	public void TestCStringStartsWithMethod() {
		CString tempCString = new CString("JavaTest");
		Assert.assertEquals(true, tempCString.startsWith(new CString("Java")));
		Assert.assertEquals(false, tempCString.startsWith(new CString("Test")));
		Assert.assertEquals(false, tempCString.startsWith(new CString("JavaTest1")));
	}
	
	@Test
	public void TestCStringEndsWidthMethod() {
		CString tempCString = new CString("JavaTest");
		Assert.assertEquals(false, tempCString.endsWith(new CString("Java")));
		Assert.assertEquals(true, tempCString.endsWith(new CString("Test")));
		Assert.assertEquals(false, tempCString.endsWith(new CString("1JavaTest")));
	}
	
	@Test
	public void TestCStringContainsMethod() {
		CString tempCString = new CString("JavaTest");
		Assert.assertEquals(true, tempCString.contains(new CString("Java")));
		Assert.assertEquals(true, tempCString.contains(new CString("ava")));
		Assert.assertEquals(true, tempCString.contains(new CString("Test")));
		Assert.assertEquals(false, tempCString.contains(new CString("JavaTest1")));
		Assert.assertEquals(false, tempCString.contains(new CString("Javat")));
	}
	
	@Test
	public void TestCStringSubstringMethod() {
		CString tempCString = new CString("JavaTest");
		CString sub = tempCString.substring(1, 7);
		Assert.assertEquals("avaTes", sub.toString());
		
		CString sub2 = sub.substring(1, 5);
		Assert.assertEquals("vaTe", sub2.toString());
		
		CString newCString = new CString(sub2);
		Assert.assertEquals("vaTe", newCString.toString());
	}
	
	@Test
	public void TestCStringLeftAndRightMethods() {
		CString tempCString = new CString("JavaTest");
		
		CString leftPart = tempCString.left(4);
		Assert.assertEquals("Java", leftPart.toString());
		Assert.assertEquals(4, leftPart.length());
		
		CString rightPart = tempCString.right(4);
		Assert.assertEquals("Test", rightPart.toString());
		Assert.assertEquals(4, rightPart.length());
	}
	
	@Test
	public void TestCStringAddMethod() {
		CString tempCString = new CString("JavaTest");
		
		tempCString = tempCString.add(new CString("Sharp"));
		Assert.assertEquals("JavaTestSharp", tempCString.toString());
		Assert.assertEquals(13, tempCString.length());
		
		tempCString = tempCString.add(new CString(""));
		Assert.assertEquals("JavaTestSharp", tempCString.toString());
		Assert.assertEquals(13, tempCString.length());
	}
	
	@Test
	public void TestCStringReplaceAllCharMethod() {
		CString tempCString = new CString("JavaTest");
		
		tempCString = tempCString.replaceAll('a', 'e');
		Assert.assertEquals("JeveTest", tempCString.toString());
		
		tempCString = tempCString.replaceAll('a', 'e');
		Assert.assertEquals("JeveTest", tempCString.toString());
		
		tempCString = tempCString.replaceAll('e', 'e');
		Assert.assertEquals("JeveTest", tempCString.toString());
	}
	
	@Test
	public void TestCStringReplaceAllCStringMethod() {
		CString tempCString = new CString("JavaTest");
		
		tempCString = tempCString.replaceAll(new CString("a"), new CString("e"));
		Assert.assertEquals("JeveTest", tempCString.toString());
		
		tempCString = tempCString.replaceAll(new CString("a"), new CString("e"));
		Assert.assertEquals("JeveTest", tempCString.toString());

		tempCString = tempCString.replaceAll(new CString("ev"), new CString(" "));
		Assert.assertEquals("J eTest", tempCString.toString());
		
		tempCString = new CString("ababab");
		tempCString = tempCString.replaceAll(new CString("ab"), new CString("abab"));
		Assert.assertEquals("abababababab", tempCString.toString());
		
		tempCString = tempCString.replaceAll(new CString("baba"), new CString("c"));
		Assert.assertEquals("accbab", tempCString.toString());
	}
}
