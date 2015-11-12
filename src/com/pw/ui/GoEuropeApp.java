package com.pw.ui;

import com.pw.endpoint.EndpointConnector;
import com.pw.log.Logger;
import com.pw.logic.CsvWriter;
import com.pw.logic.JsonArrayIterator;
import com.pw.logic.JsonParser;
import com.pw.logic.ex.WriterException;

import java.io.*;
import java.net.URL;

/**
 * Main class for the application
 *
 */
public class GoEuropeApp {

    private static final Logger logger = Logger.getLogger(GoEuropeApp.class);

    private static CsvWriter csvWriter;

    private static File writeTo;
    private static String searchTerm;
    private static JsonArrayIterator jsonArrayIterator;

    public static void main(String[] args) {
        try {
            parseArguments(args);
        } catch (IllegalArgumentException ex) {
            logger.log("Insufficient Arguments");
            ConsolePrinter.printUsage();
            return;
        }
        ConsolePrinter.printWelcomeMessage();
        ConsolePrinter.printUsage();
        runSearch();
        writeResponse();
    }


    /**
     * Read program arguments and initialize variables
     *
     * @param args  - program arguments
     */
    private static void parseArguments(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("Insufficient Arguments");
        boolean isSeparatorArgument = false;
        searchTerm = args[0];
        if (args.length == 2)
            writeTo = new File(args[1]);
        if (args.length == 3) {
            csvWriter = new CsvWriter(args[2]);
            isSeparatorArgument = true;
        }
        if (!isSeparatorArgument)
            csvWriter = new CsvWriter(CsvWriter.DEFAULT_CSV_SEPARATOR);
    }

    /**
     * Runs the actual search calling the endpoint URL
     *
     */
    public static void runSearch() {
        try {
            URL endPointURL = new URL(EndpointConnector.prepareURL(searchTerm));
            EndpointConnector endpointConnector = new EndpointConnector(endPointURL.openConnection());
            endpointConnector.connect();
            String response = endpointConnector.readResponse();
            JsonParser jsonParser = new JsonParser(response);
            jsonArrayIterator = jsonParser.parse();
        } catch (IOException e) {
            logger.log("Could not connect to endpoint URL");
        }
    }

    /**
     * Writes the response to the output.
     *
     */
    public static void writeResponse() {
        try {
            Writer writer = createWriter();
            csvWriter.writeCsvFile(writer, jsonArrayIterator);
        } catch (WriterException e) {
            logger.log("Could not write to output.");
        }

    }

    /**
     * Creates a writer depending on the initialization.
     * If File path argument was passed creates FileWriter.
     * If no file part argument creates a simple console writer.
     *
     * @return  - PrintWriter if no file specified, FileWriter otherwise
     * @throws WriterException
     */
    private static Writer createWriter() throws WriterException {
        if (writeTo == null)
            return new PrintWriter(System.out);
        try {
            return new FileWriter(writeTo);
        } catch (IOException e) {
            throw new WriterException();
        }
    }

}
