package hr.fer.zemris.java.webserver;

/**
 * IWebWorker interface represents requested web task.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface IWebWorker {
    /**
     * Process current request.
     * 
     * @param context
     *            Request context.
     */
    public void processRequest(RequestContext context);
}
