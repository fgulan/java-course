package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClientWorker implements {@link Runnable} interface. Represent clients thread
 * which in run() method process HTTP request.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ClientWorker implements Runnable {

    /** Pushback stream buffer size. */
    private static final int STREAM_BUFFER_SIZE = 3;

    /** Server address. */
    private String address;
    /** Mime types. */
    private Map<String, String> mimeTypes;
    /** Map of web workers. */
    private Map<String, IWebWorker> workersMap;
    /** Current document path. */
    private Path documentRoot;
    /** Session timeout. */
    private int sessionTimeout;

    /** Client socket. */
    private Socket csocket;
    /** Input stream. */
    private PushbackInputStream istream;
    /** Output stream. */
    private OutputStream ostream;
    /** HTTP version. */
    private String version;
    /** Request method. */
    private String method;
    /** Parameters. */
    private Map<String, String> params;
    /** Persistent parameters. */
    private Map<String, String> permPrams;
    /** List of cookies. */
    private List<RCCookie> outputCookies;
    /** Session map. */
    private Map<String, SessionMapEntry> sessions;
    /** Random number generator. */
    private Random sessionRandom;
    /** Session ID. */
    private String SID;

    /**
     * Constructor for ClientWorker class.
     * 
     * @param address
     *            Server address.
     * @param mimeTypes
     *            Supported mime types.
     * @param workersMap
     *            Web workers map.
     * @param documentRoot
     *            Current document path.
     * @param csocket
     *            Client socket.
     * @param sessionTimeout
     *            Session timeout.
     * @param sessions
     *            Session map.
     * @param sessionRandom
     *            Random number generator.
     */
    public ClientWorker(String address, Map<String, String> mimeTypes,
            Map<String, IWebWorker> workersMap, Path documentRoot,
            Socket csocket, int sessionTimeout,
            Map<String, SessionMapEntry> sessions, Random sessionRandom) {
        super();
        this.address = address;
        this.mimeTypes = mimeTypes;
        this.workersMap = workersMap;
        this.documentRoot = documentRoot;
        this.csocket = csocket;
        this.sessionTimeout = sessionTimeout;
        this.sessions = sessions;
        this.sessionRandom = sessionRandom;
        this.params = new HashMap<String, String>();
        this.outputCookies = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        List<String> request = null;
        try {
            istream = new PushbackInputStream(csocket.getInputStream(),
                    STREAM_BUFFER_SIZE);
            ostream = csocket.getOutputStream();
            request = getRequest();
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }

        if (request != null && request.size() < 1) {
            response(400, "Invalid request");
            return;
        }

        String firstLine = request.get(0);
        String[] splittedLine = firstLine.trim().split("\\s+");

        if (splittedLine.length != 3) {
            response(400, "Invalid request");
            return;
        }
        method = splittedLine[0];
        version = splittedLine[2];
        String reqPath = splittedLine[1];

        if (!method.equals("GET")
                || !(version.equals("HTTP/1.1") || version.equals("HTTP/1.0"))) {
            response(400, "Invalid request");
            return;
        }
        checkSession(request);
        String path = getPath(reqPath);

        RequestContext requestContext = new RequestContext(ostream, params,
                permPrams, outputCookies);

        if (path.startsWith("/ext/")) {
            processWorkers(path, requestContext);
            return;
        }

        if (workersMap.containsKey(path)) {
            workersMap.get(path).processRequest(requestContext);
            socketClose();
            return;
        }

        Path resolvedPath = getResolvedPath(path);
        if (!resolvedPath.toString().startsWith(documentRoot.toString())) {
            response(403, "Forbidden");
            return;
        }

        File requestedFile = resolvedPath.toFile();
        if (!(requestedFile.exists() && requestedFile.isFile() && requestedFile
                .canRead())) {
            response(404, "Not found");
            return;
        }

        String fileName = requestedFile.getName();
        int dotPosition = fileName.lastIndexOf(".");
        String extension;
        if (dotPosition != -1) {
            extension = fileName.substring(dotPosition + 1, fileName.length());
        } else {
            response(400, "Invalid request");
            return;
        }

        if (extension.equals("smscr")) {
            processSmartScript(requestedFile, requestContext);
            return;
        }

        String mimeType = "application/octet-stream";
        if (mimeTypes.containsKey(extension)) {
            mimeType = mimeTypes.get(extension);
        }
        requestContext.setMimeType(mimeType);

        writeData(requestedFile, requestContext);
        System.out.println(resolvedPath.toAbsolutePath().toString());
        socketClose();
        return;
    }

    /**
     * Get path from request.
     * 
     * @param reqPath
     *            Header line with path.
     * @return Path.
     */
    private String getPath(String reqPath) {
        String path;
        if (reqPath.contains("?")) {
            String[] splittedPath = reqPath.split("\\?");
            parseParameters(splittedPath[1]);
            path = splittedPath[0];
        } else {
            path = reqPath;
        }
        return path;
    }

    /**
     * Process requested web worker.
     * 
     * @param path
     *            Web worker path.
     * @param requestContext
     *            Request context.
     */
    private void processWorkers(String path, RequestContext requestContext) {
        String workersPackage = "hr.fer.zemris.java.webserver.workers.";
        String fqcn = path.replace("/ext/", "");

        try {
            Class<?> referenceToClass = this.getClass().getClassLoader()
                    .loadClass(workersPackage + fqcn);
            Object newObject = referenceToClass.newInstance();
            IWebWorker iww = (IWebWorker) newObject;
            iww.processRequest(requestContext);
        } catch (ClassNotFoundException e) {
            System.out.println("Server ERROR: " + e.getMessage());
            response(404, "Not found");
        } catch (InstantiationException e) {
            System.out.println("Server ERROR: " + e.getMessage());
            response(404, "Not found");
        } catch (IllegalAccessException e) {
            System.out.println("Server ERROR: " + e.getMessage());
            response(404, "Not found");
        } finally {
            socketClose();
        }
        return;
    }

    /**
     * Process and execute given SmartScript file.
     * 
     * @param file
     *            SmartScript file.
     * @param request
     *            Request context.
     */
    private void processSmartScript(File file, RequestContext request) {
        try {
            String docBody = getSmartScript(file);
            SmartScriptParser scriptParser = new SmartScriptParser(docBody);
            new SmartScriptEngine(scriptParser.getDocumentNode(), request)
                    .execute();
            socketClose();
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }
    }

    /**
     * Write given file data to request context output stream.
     * 
     * @param requestedFile
     *            Input file.
     * @param requestContext
     *            Request context.
     */
    private void writeData(File requestedFile, RequestContext requestContext) {
        final int size = 1024;
        try {
            BufferedInputStream reader = new BufferedInputStream(
                    new FileInputStream(requestedFile));
            int length = 0;
            do {
                byte[] output = new byte[size];
                length = reader.read(output);
                if (length == -1) {
                    break;
                }
                requestContext.write(Arrays.copyOfRange(output, 0, length));
            } while (length != -1);
            reader.close();
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }
        socketClose();
    }

    /**
     * Reads SmartScript file into string.
     * 
     * @param script
     *            SmartScript file.
     * @return SmartScript file as string.
     * @throws IOException
     *             On error with reading given script.
     */
    private String getSmartScript(File script) throws IOException {
        StringBuilder docBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(script), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            while (line != null) {
                docBody.append(line);
                docBody.append(System.lineSeparator());
                line = reader.readLine();
            }
        }
        return docBody.toString();
    }

    /**
     * Resolve given path with respect to documentRoot.
     * 
     * @param path
     *            Input path.
     * @return Resolved path.
     */
    private Path getResolvedPath(String path) {
        Path resolvedPath = documentRoot.toAbsolutePath()
                .resolve(Paths.get("./" + path)).toAbsolutePath();

        try {
            resolvedPath = Paths.get(resolvedPath.toFile().getCanonicalPath());
        } catch (IOException e1) {
            System.out.println(resolvedPath.toAbsolutePath().toString());
        }
        return resolvedPath;
    }

    /**
     * Parse input path parameters.
     * 
     * @param input
     *            Input path.
     */
    private void parseParameters(String input) {
        String[] param = input.split("&");
        for (int i = 0; i < param.length; i++) {
            String[] parameter = param[i].split("=");
            params.put(parameter[0], parameter[1]);
        }
    }

    /**
     * Response with given status code and status message.
     * 
     * @param statusCode
     *            Status code.
     * @param message
     *            Status message.
     */
    private void response(int statusCode, String message) {
        RequestContext rc = new RequestContext(ostream, params, permPrams,
                outputCookies);
        rc.setStatusCode(statusCode);
        rc.setStatusText(message);

        try {
            StringBuilder sb = new StringBuilder("<html>\r\n" + "  <head>\r\n"
                    + "    <title>" + statusCode + " " + message
                    + "</title>\r\n" + "    <BASE href=\"/error_docs/\">"
                    + "  </head>\r\n" + "  <body>\r\n" + "    <h1>" + message
                    + "</h1>\r\n"
                    + "The requested path was not found on this server."
                    + "    <P>\r\n" + "    <HR>\r\n" + "    <ADDRESS>\r\n"
                    + "Web Server at " + address + "    </ADDRESS>\r\n"
                    + "  </body>\r\n" + "</html>\r\n");

            byte[] responseText = sb.toString()
                    .getBytes(StandardCharsets.UTF_8);
            rc.write(responseText);
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }
        socketClose();
    }

    /**
     * Reads HTTP request and splits it into list of lines.
     * 
     * @return HTTP request lines.
     * @throws IOException
     *             On request reading.
     */
    private List<String> getRequest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int ch;
        while ((ch = istream.read()) != -1) {
            if (ch != 13 && ch != 10) {
                bos.write(ch);
            } else if (ch == 13) {
                byte[] chars = new byte[STREAM_BUFFER_SIZE];
                istream.read(chars);
                if (chars[0] == 10 && chars[1] == 13 && chars[2] == 10) {
                    bos.write(10);
                    bos.write(10);
                    break;
                }
                istream.unread(chars);
            } else if (ch == 10) {
                bos.write(ch);
                int ch1 = istream.read();
                if (ch1 == 10) {
                    bos.write(ch1);
                    break;
                }
                istream.unread(ch1);
            }
        }

        if (bos.size() == 0 || ch == -1) {
            return null;
        }
        String request = new String(bos.toByteArray(),
                StandardCharsets.US_ASCII);
        return Arrays.asList(request.split("\n"));
    }

    /**
     * Closes client socket.
     */
    private void socketClose() {
        try {
            csocket.close();
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }
    }

    /**
     * SessionMapEntry class represents session map entry for storing cookies
     * information.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    public static class SessionMapEntry {
        /** Session ID. */
        private String sid;
        /** Expiration time. */
        private long validUntil;
        /** Parameters map. */
        private Map<String, String> map;

        /**
         * Constructor for SessionMapEntry class.
         * 
         * @param sid
         *            Session ID.
         * @param validUntil
         *            Expiration time.
         */
        public SessionMapEntry(String sid, long validUntil) {
            this.sid = sid;
            this.validUntil = validUntil;
            this.map = new ConcurrentHashMap<String, String>();
        }

        /**
         * Returns expiration time.
         * 
         * @return Expiration time.
         */
        public long getValidUntil() {
            return validUntil;
        }

        /**
         * Sets expiration time.
         * 
         * @param validUntil
         *            New expiration time.
         */
        public void setValidUntil(long validUntil) {
            this.validUntil = validUntil;
        }

        /**
         * Returns session ID.
         * 
         * @return Session ID.
         */
        public String getSid() {
            return sid;
        }

        /**
         * Returns current parameters map.
         * 
         * @return Current parameters map.
         */
        public Map<String, String> getMap() {
            return map;
        }

    }

    /**
     * Finds SID from HTTP request and checks if it is already in session map.
     * If not, creates a new SID and adds it to the session map.
     * 
     * @param request
     *            HTTP request.
     */
    private synchronized void checkSession(List<String> request) {
        String sidCandidate = null;
        SessionMapEntry currentEntry = null;

        for (String line : request) {
            if (line.startsWith("Cookie:")) {
                String[] cookies = line.replace("Cookie:", "").split(";");
                for (int i = 0; i < cookies.length; i++) {
                    String[] cookie = cookies[i].split("=");

                    String name = cookie[0];
                    String value = cookie[1].replace("\"", "");
                    if (name.trim().equals("sid")) {
                        sidCandidate = value;
                    }
                }
            }
        }

        if (sidCandidate == null) {
            currentEntry = getSidEntry();
        } else {
            long currentTime = new Date().getTime() / 1000;
            SessionMapEntry entry = sessions.get(sidCandidate);
            if (entry == null || currentTime > entry.getValidUntil()) {
                currentEntry = getSidEntry();
            } else {
                entry.setValidUntil(currentTime + sessionTimeout);
                currentEntry = entry;
            }
        }
        permPrams = currentEntry.getMap();
    }

    /**
     * Creates and returns a new {@link SessionMapEntry}. It also adds newly
     * created entry in current session map.
     * 
     * @return A new {@link SessionMapEntry}.
     */
    private SessionMapEntry getSidEntry() {
        final int size = 20;
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            char c = chars[sessionRandom.nextInt(chars.length)];
            builder.append(c);
        }
        SID = builder.toString();

        long validUntil = (new Date().getTime() / 1000) + sessionTimeout;

        SessionMapEntry entry = new SessionMapEntry(SID, validUntil);
        sessions.put(SID, entry);

        outputCookies.add(new RCCookie("sid", SID, null, address, "/",
                "HttpOnly"));

        return entry;
    }
}