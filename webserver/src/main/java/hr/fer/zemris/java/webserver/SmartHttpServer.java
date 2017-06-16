package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.ClientWorker.SessionMapEntry;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SmartHttpServer program represents simple HTTP server written in Java. It
 * supports cookies and executing SmartScript (.smscr) files.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SmartHttpServer {

    /** Server address. */
    private String address;
    /** Server port. */
    private int port;
    /** Number of threads. */
    private int workerThreads;
    /** Timeout. */
    private int sessionTimeout;
    /** Map of mime types. */
    private Map<String, String> mimeTypes = new HashMap<>();
    /** Map of web workers. */
    private Map<String, IWebWorker> workersMap = new HashMap<>();
    /** Server thread. */
    private ServerThread serverThread;
    /** Executor service pool. */
    private ExecutorService threadPool;
    /** File path. */
    private Path documentRoot;
    /** Sessions map. */
    private Map<String, ClientWorker.SessionMapEntry> sessions = new HashMap<>();
    /** Random number generator. */
    private Random sessionRandom = new Random();
    /** Timeout timer. */
    private Timer timer;
    /** Cookie killer. */
    private CookieKiller cookieKiller;

    /**
     * Constructor for SmartHttpServer class. It accepts path to
     * server.properties file.
     * 
     * @param configFileName
     *            Path to server.properties file.
     */
    public SmartHttpServer(String configFileName) {
        Properties configServer = new Properties();
        Path configPath = Paths.get(configFileName);

        try {
            configServer.load(Files.newInputStream(configPath));
        } catch (IOException e) {
            System.out.println("Unable to read server configuration files!");
            System.exit(-1);
        }

        address = configServer.getProperty("server.address");
        port = Integer.parseInt(configServer.getProperty("server.port"));
        sessionTimeout = Integer.parseInt(configServer
                .getProperty("session.timeout"));
        documentRoot = Paths.get(configServer
                .getProperty("server.documentRoot"));
        workerThreads = Integer.parseInt(configServer
                .getProperty("server.workerThreads"));

        Path workersPath = Paths
                .get(configServer.getProperty("server.workers"));
        try {
            getWorkers(workersPath);
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }

        Path mimeConfigPath = Paths.get(configServer
                .getProperty("server.mimeConfig"));
        Properties configMime = new Properties();

        try {
            configMime.load(Files.newInputStream(mimeConfigPath));
        } catch (IOException e) {
            System.out.println("Unable to read mime configuration file!");
        }

        for (Entry<Object, Object> mime : configMime.entrySet()) {
            mimeTypes.put(mime.getKey().toString(), mime.getValue().toString());
        }

    }

    /**
     * Parse workers form workers.properties file.
     * 
     * @param workersPath
     *            Path to workers.properties file.
     * @throws IOException
     *             On error with reading properties.
     */
    private void getWorkers(Path workersPath) throws IOException {
        List<String> workers = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(workersPath.toFile()),
                StandardCharsets.UTF_8))) {
            String worker = reader.readLine();

            while (worker != null) {
                if (workers.contains(worker)) {
                    throw new IllegalArgumentException("'" + workersPath
                            + "' cannot contain duplicate lines.");
                }

                workers.add(worker);
                worker = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(workersPath
                    + " file is not found.");
        }

        for (String worker : workers) {
            String[] workerSplitted = worker.split(" = ");
            String path = workerSplitted[0];
            String fqcn = workerSplitted[1];

            try {
                Class<?> referenceToClass = this.getClass().getClassLoader()
                        .loadClass(fqcn);
                Object newObject = referenceToClass.newInstance();
                IWebWorker iww = (IWebWorker) newObject;

                workersMap.put(path, iww);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Class " + fqcn
                        + " is not found.");
            } catch (InstantiationException e) {
                throw new IllegalArgumentException("Could not instantiate "
                        + fqcn + " class.");
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Illegal access to " + fqcn
                        + " class.");
            }
        }

    }

    /**
     * Starts the server.
     */
    protected synchronized void start() {
        if (serverThread == null) {
            serverThread = new ServerThread();
        }
        threadPool = Executors.newFixedThreadPool(workerThreads,
                new DaemonicThreadFactroy());
        serverThread.start();

        // Server killer
        threadPool.submit(new ServerKiller());
    }

    /**
     * Stops the server.
     */
    protected synchronized void stop() {
        System.out.println("Stoping the SmartHttpServer...");
        serverThread.terminate();
        threadPool.shutdown();
    }

    /**
     * ServerThread extends {@link Thread} class. Thread for emulating server.
     * 
     * @author Filip Gulan
     * @version 1.0
     * 
     */
    protected class ServerThread extends Thread {
        /** Thread running. */
        private volatile boolean runThread = true;
        /** Cookie killer timeout. */
        private static final int timeout = 300 * 1000;

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {

            // Sets cookie killer
            timer = new Timer();
            cookieKiller = new CookieKiller();
            timer.schedule(cookieKiller, timeout, timeout);

            try {
                InetAddress adrs = InetAddress.getByName(address);

                // Init server socket
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.setSoTimeout(1000);
                serverSocket.bind(new InetSocketAddress(adrs, port));

                System.out.println("Server started on adress: "
                        + adrs.toString() + " on port: " + port);

                while (runThread) {
                    Socket client = null;
                    try {
                        client = serverSocket.accept();
                    } catch (SocketTimeoutException e) {
                        if (!runThread) {
                            break;
                        }
                        continue;
                    }

                    ClientWorker cw = new ClientWorker(address, mimeTypes,
                            workersMap, documentRoot, client, sessionTimeout,
                            sessions, sessionRandom);

                    threadPool.submit(cw);
                }
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Server ERROR: " + e.getMessage());
            }
        }

        /**
         * Terminates current server thread.
         */
        public void terminate() {
            this.runThread = false;
            cookieKiller.cancel();
            timer.cancel();
        }
    }

    /**
     * CookieKiller extends {@link TimerTask} class. Thread for cleaning
     * sessions map every five minutes.
     * 
     * @author Filip Gulan
     * @version 1.0
     * 
     */
    protected class CookieKiller extends TimerTask {
        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            if (sessions != null) {
                synchronized (sessions) {
                    Iterator<SessionMapEntry> iter = sessions.values()
                            .iterator();
                    while (iter.hasNext()) {
                        SessionMapEntry entry = iter.next();
                        if (entry.getValidUntil() < (new Date().getTime() / 1000)) {
                            iter.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * ServerKiller implements {@link Runnable}. This threads waits for user
     * input for terminating server.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    protected class ServerKiller implements Runnable {

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in, StandardCharsets.UTF_8));
            while (true) {
                String command = "";
                try {
                    command = reader.readLine();
                } catch (IOException e) {
                    System.out.println("SERVER ERROR: " + e.getMessage());
                }
                if (command == null) {
                    continue;
                }
                if (command.equals("stop")) {
                    stop();
                    return;
                }
            }
        }
    }

    /**
     * Start point of program SmartHttpServer.
     * 
     * @param args
     *            Command line arguments. Path to a server.properties file.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out
                    .println("Please provide a path to a server.propertes file!");
            System.exit(-1);
        }
        String path = args[0];
        SmartHttpServer server = new SmartHttpServer(path);
        System.out
                .println("Starting the SmartHttpServer... Type 'stop' to exit.");
        server.start();
    }
}