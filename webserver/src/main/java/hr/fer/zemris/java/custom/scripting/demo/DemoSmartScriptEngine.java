package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DemoSmartScriptEngine program is used to demonstrate usage of
 * {@link SmartScriptEngine} class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DemoSmartScriptEngine {

    /**
     * Start point of program DemoSmartScriptEngine.
     * 
     * @param args
     *            Command line arguments. Not used.
     * @throws IOException
     *             On file reading error.
     */
    public static void main(String[] args) throws IOException {
        osnovni();
        brojPoziva();
        zbrajanje();
        fibonacci();
    }

    /**
     * osnovni.smscr file.
     * 
     * @throws IOException
     *             On reading error.
     */
    private static void osnovni() throws IOException {
        String documentBody = readFromDisk(new File(
                "webroot/scripts/osnovni.smscr"));
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        // create engine and execute it
        try {
            new SmartScriptEngine(
                    new SmartScriptParser(documentBody).getDocumentNode(),
                    new RequestContext(System.out, parameters,
                            persistentParameters, cookies)).execute();
        } catch (SmartScriptParserException e) {
            System.out.println("Could not parse document body.");
        }
    }

    /**
     * zbrajanje.smscr file.
     * 
     * @throws IOException
     *             On reading error.
     */
    private static void zbrajanje() throws IOException {
        String documentBody = readFromDisk(new File(
                "webroot/scripts/zbrajanje.smscr"));
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        parameters.put("a", "4");
        parameters.put("b", "2");

        try {
            new SmartScriptEngine(
                    new SmartScriptParser(documentBody).getDocumentNode(),
                    new RequestContext(System.out, parameters,
                            persistentParameters, cookies)).execute();
        } catch (SmartScriptParserException e) {
            System.out.println("Could not parse document body.");
        }
    }

    /**
     * brojPoziva.smscr file.
     * 
     * @throws IOException
     *             On reading error.
     */
    private static void brojPoziva() throws IOException {
        String documentBody = readFromDisk(new File(
                "webroot/scripts/brojPoziva.smscr"));
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        persistentParameters.put("brojPoziva", "3");
        RequestContext rc = new RequestContext(System.out, parameters,
                persistentParameters, cookies);

        try {
            new SmartScriptEngine(
                    new SmartScriptParser(documentBody).getDocumentNode(), rc)
                    .execute();
        } catch (SmartScriptParserException e) {
            System.out.println("Could not parse document body.");
        }

        System.out.println("Vrijednost u mapi: "
                + rc.getPersistentParameter("brojPoziva"));
    }

    /**
     * fibonacci.smscr file.
     * 
     * @throws IOException
     *             On reading error.
     */
    private static void fibonacci() throws IOException {
        String documentBody = readFromDisk(new File(
                "webroot/scripts/fibonacci.smscr"));
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

        try {
            new SmartScriptEngine(
                    new SmartScriptParser(documentBody).getDocumentNode(),
                    new RequestContext(System.out, parameters,
                            persistentParameters, cookies)).execute();
        } catch (SmartScriptParserException e) {
            System.out.println("Could not parse document body.");
        }
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
    private static String readFromDisk(File script) throws IOException {
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
}
