package com.pw.endpoint;

import com.pw.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URLConnection;

/**
 * Calls the endpoint url parsing the response
 *
 */
public class EndpointConnector {

    public static final String ENDPOINT_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
    private static final Logger logger = Logger.getLogger(EndpointConnector.class);
    private URLConnection connection;

    private boolean isConnected;

    public EndpointConnector(URLConnection connection) {
        this.connection = connection;
    }

    /**
     * Appends the search term to the endpoint URL
     *
     * @param searchTerm    - the search term
     * @return              - enpoitn URL
     */
    public static String prepareURL(String searchTerm) {
        return ENDPOINT_URL + searchTerm;
    }

    /**
     * Connects to the URL connection
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        if (isConnected())
            return;
        try {
            connection.connect();
            isConnected = true;
        } catch (IOException e) {
            logger.log("Error making connection");
            throw e;
        }
    }

    /**
     * Reads the response from URL connection.
     *
     * @return  - the String response
     * @throws ConnectException
     */
    public String readResponse() throws ConnectException {
        if (!isConnected())
            throw new ConnectException("Connect before reading the stream.");
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }
            return response.toString();
        } catch (IOException e) {
            logger.log("Could not read the response.");
            return "";
        }
    }

    /**
     * Checks if this connector is already connected.
     *
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }

}
