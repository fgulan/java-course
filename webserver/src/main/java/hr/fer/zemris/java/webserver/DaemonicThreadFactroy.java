package hr.fer.zemris.java.webserver;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * DaemonicThreadFactroy implements {@link ThreadFactory}. It creates daemon
 * threads in executor service.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DaemonicThreadFactroy implements ThreadFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(true);
        return thread;
    }

}
