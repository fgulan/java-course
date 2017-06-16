package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for RequestContext class.
 * 
 * @author Filip Gulan
 *
 */
public class TestRequestContext {

    /** Output stream. */
    private ByteArrayOutputStream outputStream;
    /** Request context. */
    private RequestContext request;
    /** Parameters map. */
    private Map<String, String> parameters;
    /** Persistent parameters map. */
    private Map<String, String> persistentParameters;
    /** List of cookies. */
    private List<RCCookie> outputCookies;

    @Before
    public void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        parameters = new HashMap<String, String>();
        persistentParameters = new HashMap<String, String>();
        outputCookies = new ArrayList<RCCookie>();
        request = new RequestContext(outputStream, parameters,
                persistentParameters, outputCookies);
    }

    @Test
    public void testWriteString() throws IOException {
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n\r\n"
                + "Hello Java";
        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
        request.write(" Hello again");
        expected = expected + " Hello again";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testWriteBytes() throws IOException {
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n\r\n"
                + "Hello Java";
        request.write("Hello Java".getBytes());
        assertEquals(expected, outputStream.toString());
        request.write(" Hello again".getBytes());
        expected = expected + " Hello again";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testGetParameter() {
        parameters.put("Java", "Javko");
        assertEquals("Javko", request.getParameter("Java"));
    }

    @Test
    public void testGetParameterNames() {
        parameters.put("Java", "Javko");
        parameters.put("Ferko", "Ferkic");
        assertEquals(2, request.getParameterNames().size());
    }

    @Test
    public void testGetPersistentParameter() {
        persistentParameters.put("Java", "Javko");
        assertEquals("Javko", request.getPersistentParameter("Java"));
    }

    @Test
    public void testGetPersistentParameterNames() {
        persistentParameters.put("Java", "Javko");
        persistentParameters.put("Ferko", "Ferkic");
        assertEquals(2, request.getPersistentParameterNames().size());
    }

    @Test
    public void testSetPersistentParameter() {
        request.setPersistentParameter("Java", "Javko");
        assertEquals("Javko", request.getPersistentParameter("Java"));
    }

    @Test
    public void testRemovePersistentParameter() {
        request.setPersistentParameter("Java", "Javko");
        assertEquals("Javko", request.getPersistentParameter("Java"));
        request.removePersistentParameter("Java");
        assertEquals(null, request.getPersistentParameter("Java"));
    }

    @Test
    public void testTemporaryParameterMethods() {
        request.setTemporaryParameter("Java", "Javko");
        assertEquals("Javko", request.getTemporaryParameter("Java"));
        request.removeTemporaryParameter("Java");
        assertEquals(null, request.getTemporaryParameter("Java"));
        assertEquals(0, request.getTemporaryParameterNames().size());
        request.setTemporaryParameter("Java", "Javko");
        request.setTemporaryParameter("Ferko", "Ferkic");
        assertEquals(2, request.getTemporaryParameterNames().size());
    }

    @Test
    public void testCookiesHandling() throws IOException {
        outputCookies.add(new RCCookie("Java", "Javic", 10, "fer.hr", "www.",
                "HttpOnly"));
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Set-Cookie: Java=\"Javic\"; Domain=fer.hr; Path=www.; Max-Age=10; HttpOnly\r\n\r\n"
                + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
        request.write(" Hello again");
        expected = expected + " Hello again";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testRCCookieGetters() throws IOException {
        RCCookie cookie = new RCCookie("Java", "Javic", 10, "fer.hr", "www.",
                "HttpOnly");
        assertEquals("fer.hr", cookie.getDomain());
        assertEquals("www.", cookie.getPath());
        assertEquals("HttpOnly", cookie.getType());
        assertEquals(Integer.valueOf(10), cookie.getMaxAge());
        assertEquals("Java", cookie.getName());
        assertEquals("Javic", cookie.getValue());
    }

    @Test
    public void testAddRCCookie() throws IOException {
        RCCookie cookie = new RCCookie("Java", "Javic", null, null, null, null);
        request.addRCCookie(cookie);
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Set-Cookie: Java=\"Javic\"\r\n\r\n" + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testConstructor() throws IOException {
        request = new RequestContext(outputStream, null, null, null);
        RCCookie cookie = new RCCookie("Java", "Javic", null, null, null, null);
        request.addRCCookie(cookie);
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Set-Cookie: Java=\"Javic\"\r\n\r\n" + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testSetEncoding() throws IOException {
        request.setEncoding("UTF-8");
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n\r\n"
                + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testSetMimeType() throws IOException {
        request.setMimeType("image/png");
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/png\r\n\r\n" + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testSetStatusCode() throws IOException {
        request.setStatusCode(404);
        String expected = "HTTP/1.1 404 OK\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n\r\n"
                + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testSetStatusMessage() throws IOException {
        request.setStatusCode(404);
        request.setStatusText("Not found");
        String expected = "HTTP/1.1 404 Not found\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n\r\n"
                + "Hello Java";

        request.write("Hello Java");
        assertEquals(expected, outputStream.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorException() {
        request = new RequestContext(null, null, null, null);
    }

    @Test(expected = RuntimeException.class)
    public void testSetEncodingException() throws IOException {
        request.write("Java");
        request.setEncoding("UTF-8");
    }

    @Test(expected = RuntimeException.class)
    public void testSetStatusCodeException() throws IOException {
        request.write("Java");
        request.setStatusCode(404);
    }

    @Test(expected = RuntimeException.class)
    public void testSetStatusTextException() throws IOException {
        request.write("Java");
        request.setStatusText("Not found");
    }

    @Test(expected = RuntimeException.class)
    public void testSetMimeTypeException() throws IOException {
        request.write("Java");
        request.setMimeType("image/png");
    }

    @Test(expected = RuntimeException.class)
    public void testAddRCCookieException() throws IOException {
        request.write("Java");
        RCCookie cookie = new RCCookie("Java", "Javic", null, null, null, null);
        request.addRCCookie(cookie);
    }
}
