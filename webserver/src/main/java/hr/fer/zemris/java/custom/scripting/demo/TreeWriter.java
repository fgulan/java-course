package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * TreeWriter program is used to parse and print SmartScript files. It accepts
 * one command line argument: path to a file to parse.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TreeWriter {

    /**
     * Start point of program TreeWriter.
     * 
     * @param args
     *            Command line arguments. Path to a file.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments."
                    + "Please provide a path to a smart script file");
            System.exit(-1);
        }

        String path = args[0];
        if (!path.endsWith(".smscr")) {
            System.out.println("Please provide SmartScript file.");
            System.exit(-1);
        }

        String docBody = null;
        try {
            docBody = readFromDisk(new File(path));
        } catch (IOException e1) {
            System.out.println("Unable to read given file.");
            System.exit(-1);
        }

        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody.toString());
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        }

        WriterVisitor visitor = new WriterVisitor();
        parser.getDocumentNode().accept(visitor);
    }

    /**
     * WriterVisitor implements {@link INodeVisitor}. Provides recreating
     * original script from file.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class WriterVisitor implements INodeVisitor {

        /**
         * Parse given script and return it as a string.
         * 
         * @param node
         *            Node.
         * @return Parsed document.
         */
        private static String recreateOriginalScript(Node node) {
            StringBuilder body = new StringBuilder(node.asText());
            int size = node.numberOfChildern();
            for (int i = 0; i < size; i++) {
                body.append(recreateOriginalScript(node.getChild(i)));
            }

            if (node instanceof ForLoopNode) {
                body.append("{$END$}");
            }

            return body.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitTextNode(TextNode node) {
            System.out.println(recreateOriginalScript(node));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitForLoopNode(ForLoopNode node) {
            System.out.println(recreateOriginalScript(node));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitEchoNode(EchoNode node) {
            System.out.println(recreateOriginalScript(node));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitDocumentNode(DocumentNode node) {
            System.out.println(recreateOriginalScript(node));
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
