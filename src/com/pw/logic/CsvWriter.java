package com.pw.logic;

import com.pw.log.Logger;

import java.io.*;

public class CsvWriter {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String DEFAULT_CSV_SEPARATOR = "|";
    public static final Logger logger = Logger.getLogger(CsvWriter.class);
    private String separator;

    public CsvWriter(String separator) {
        this.separator = separator;
    }

    /**
     * Writes the given JsonArrayIterator to the given Writer.
     *
     * @param writeTo           - the Writer to write to
     * @param jsonArrayIterator - the JsonArrayIterator
     */
    public void writeCsvFile(Writer writeTo, JsonArrayIterator jsonArrayIterator) {
        try (BufferedWriter writer = new BufferedWriter(writeTo)) {
            while (jsonArrayIterator.hasNext()) {
                JsonObject jsonObject = jsonArrayIterator.next();
                writer.write(jsonObject.toCSV(separator, "_id", "name", "type", "geo_position"));
                writer.write(LINE_SEPARATOR);
            }
        } catch (IOException ex) {
            logger.log("Could not write to the output");
        }

    }

}
