package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * RequestContext class represents simple HTTP request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class RequestContext {
    /** Output stream. */
    private OutputStream outputStream;
    /** Charset. */
    private Charset charset;

    /** Encoding. */
    private String encoding = "UTF-8";
    /** Status code. */
    private int statusCode = 200;
    /** Status text. */
    private String statusText = "OK";
    /** Mime type. */
    private String mimeType = "text/html";

    /** Parameters map. */
    private Map<String, String> parameters;
    /** Temporary parameters map. */
    private Map<String, String> temporaryParameters;
    /** Persistent parameters map. */
    private Map<String, String> persistentParameters;
    /** List of cookies. */
    private List<RCCookie> outputCookies;

    /** Generated header. */
    private boolean headerGenerated = false;

    /**
     * Constructor for RequestContext class.
     * 
     * @param outputStream
     *            Output stream.
     * @param parameters
     *            Parameters map.
     * @param persistentParameters
     *            Persistent parameters map.
     * @param outputCookies
     *            Output cookies.
     */
    public RequestContext(OutputStream outputStream,
            Map<String, String> parameters,
            Map<String, String> persistentParameters,
            List<RCCookie> outputCookies) {

        if (outputStream == null) {
            throw new IllegalArgumentException(
                    "RequestContext outputStream cannot be a null value.");
        }

        this.outputStream = outputStream;

        if (parameters == null) {
            this.parameters = new HashMap<String, String>();
        } else {
            this.parameters = parameters;
        }

        if (persistentParameters == null) {
            this.persistentParameters = new HashMap<String, String>();
        } else {
            this.persistentParameters = persistentParameters;
        }

        if (outputCookies == null) {
            this.outputCookies = new ArrayList<RCCookie>();
        } else {
            this.outputCookies = outputCookies;
        }
        this.temporaryParameters = new HashMap<String, String>();
    }

    /**
     * Returns value from parameters map with given name.
     * 
     * @param name
     *            Parameter name.
     * @return Parameter value.
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Returns immutable set of parameters names.
     * 
     * @return Immutable set of parameters names.
     */
    public Set<String> getParameterNames() {
        Set<String> names = new HashSet<String>();
        for (Entry<String, String> parameter : parameters.entrySet()) {
            names.add(parameter.getKey());
        }
        return Collections.unmodifiableSet(names);
    }

    /**
     * Returns value from persistent parameters map with given name.
     * 
     * @param name
     *            Parameter name.
     * @return Parameter value.
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Returns immutable set of persistent parameters names.
     * 
     * @return Immutable set of persistent parameters names.
     */
    public Set<String> getPersistentParameterNames() {
        Set<String> names = new HashSet<String>();
        for (Entry<String, String> parameter : persistentParameters.entrySet()) {
            names.add(parameter.getKey());
        }
        return Collections.unmodifiableSet(names);
    }

    /**
     * Adds element with given name and value to persistent parameters map.
     * 
     * @param name
     *            Parameter name.
     * @param value
     *            Parameter value.
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Removes element from persistent parameters map with given name.
     * 
     * @param name
     *            Parameter name.
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    /**
     * Returns the value of element from temporary parameters map with given
     * name.
     * 
     * @param name
     *            Parameter name.
     * @return Parameter value.
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Returns immutable set of temporary parameters names.
     * 
     * @return Immutable set of temporary parameters names.
     */
    public Set<String> getTemporaryParameterNames() {
        Set<String> names = new HashSet<String>();
        for (Entry<String, String> parameter : temporaryParameters.entrySet()) {
            names.add(parameter.getKey());
        }
        return Collections.unmodifiableSet(names);
    }

    /**
     * Adds element with given name and value to temporary parameters map.
     * 
     * @param name
     *            Parameter name.
     * @param value
     *            Parameter value.
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * Removes element from temporary parameters map with given name.
     * 
     * @param name
     *            Parameter name.
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Writes given data on current output stream.
     * 
     * @param data
     *            Data.
     * @return Current instance of {@link RequestContext} object.
     * @throws IOException
     *             On writing error.
     */
    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            outputStream.write(setHeader()
                    .getBytes(StandardCharsets.ISO_8859_1));
            outputStream.flush();
        }

        outputStream.write(data);
        outputStream.flush();
        return this;
    }

    /**
     * Writes given string on current output stream using current charset.
     * 
     * @param text
     *            Input string.
     * @return Current instance of {@link RequestContext} object.
     * @throws IOException
     *             On writing error.
     */
    public RequestContext write(String text) throws IOException {
        if (!headerGenerated) {
            outputStream.write(setHeader()
                    .getBytes(StandardCharsets.ISO_8859_1));
            outputStream.flush();
        }

        outputStream.write(text.getBytes(charset));
        outputStream.flush();
        return this;
    }

    /**
     * Generates header for current request.
     * 
     * @return Header for current request.
     */
    private String setHeader() {
        StringBuilder header = new StringBuilder();

        header.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
        header.append("Content-Type: " + mimeType);

        if (mimeType.startsWith("text/")) {
            header.append("; charset=" + encoding);
        }

        for (RCCookie cookie : outputCookies) {
            header.append("\r\n");
            header.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value
                    + "\";");

            if (cookie.domain != null) {
                header.append(" Domain=" + cookie.domain + ";");
            }
            if (cookie.path != null) {
                header.append(" Path=" + cookie.path + ";");
            }
            if (cookie.maxAge != null) {
                header.append(" Max-Age=" + cookie.maxAge + ";");
            }
            if (cookie.type != null) {
                header.append(" " + cookie.type + " ");
            }
            String newHeader = header.substring(0, header.length() - 1);
            header = new StringBuilder(newHeader);
        }

        header.append("\r\n");
        header.append("\r\n");

        charset = Charset.forName(encoding);
        headerGenerated = true;

        return header.toString();
    }

    /**
     * Sets the current request encoding.
     * 
     * @param encoding
     *            Encoding name.
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException(
                    "Header is generated. Changes are not allowed!");
        }
        this.encoding = encoding;
    }

    /**
     * Sets the current request status code.
     * 
     * @param statusCode
     *            Status code.
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException(
                    "Header is generated. Changes are not allowed!");
        }
        this.statusCode = statusCode;
    }

    /**
     * Sets the current request status message.
     * 
     * @param statusText
     *            Status message.
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException(
                    "Header is generated. Changes are not allowed!");
        }
        this.statusText = statusText;
    }

    /**
     * Sets the current request mime type.
     * 
     * @param mimeType
     *            Mime type.
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException(
                    "Header is generated. Changes are not allowed!");
        }
        this.mimeType = mimeType;
    }

    /**
     * Adds a given cookie to cookies list.
     * 
     * @param cookie
     *            Input cookie.
     */
    public void addRCCookie(RCCookie cookie) {
        if (headerGenerated) {
            throw new RuntimeException(
                    "Header is generated. Changes are not allowed!");
        }

        outputCookies.add(cookie);
    }

    /**
     * RCCookie class represents cookie used in HTTP request.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    public static class RCCookie {

        /** Cookie name. */
        private String name;
        /** Cookie value. */
        private String value;
        /** Cookie domain. */
        private String domain;
        /** Cookie path. */
        private String path;
        /** Cookie max age. */
        private Integer maxAge;
        /** Cookie type. */
        private String type;

        /**
         * Constructor for RCCookie class.
         * 
         * @param name
         *            Cookie name.
         * @param value
         *            Cookie value.
         * @param maxAge
         *            Cookie max age.
         * @param domain
         *            Cookie domain.
         * @param path
         *            Cookie path.
         * @param type
         *            Cookie type.
         */
        public RCCookie(String name, String value, Integer maxAge,
                String domain, String path, String type) {
            super();
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
            this.type = type;
        }

        /**
         * Returns cookie name.
         * 
         * @return Cookie name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns cookie value.
         * 
         * @return Cookie value.
         */
        public String getValue() {
            return value;
        }

        /**
         * Returns cookie domain.
         * 
         * @return Cookie domain.
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Returns cookie path.
         * 
         * @return Cookie path.
         */
        public String getPath() {
            return path;
        }

        /**
         * Returns cookie max age.
         * 
         * @return Cookie max age.
         */
        public Integer getMaxAge() {
            return maxAge;
        }

        /**
         * Returns cookie type.
         * 
         * @return Cookie type.
         */
        public String getType() {
            return type;
        }

    }
}
