package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.Set;

/**
 * EchoParams implements {@link IWebWorker}. Prints the table with given names
 * and values in path.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class EchoParams implements IWebWorker {

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRequest(RequestContext context) {
        context.setMimeType("text/html");

        Set<String> parameters = context.getParameterNames();
        try {
            context.write("<html><body>");
            context.write("<table border=\"1\">");

            context.write("<tr><td>Name</td><td>Value</td></tr>");

            for (String name : parameters) {
                context.write("<tr><td>" + name + "</td><td>"
                        + context.getParameter(name) + "</td></tr>");
            }

            context.write("</table>");
            context.write("</body></html>");
        } catch (IOException e) {
            System.out.println("Server ERROR: " + e.getMessage());
        }

    }

}
