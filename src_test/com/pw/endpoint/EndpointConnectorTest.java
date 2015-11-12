package com.pw.endpoint;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.*;

public class EndpointConnectorTest {

    @Test
    public void shouldConnectWithoutError() throws Exception {
        EndpointConnector sut = new EndpointConnector(new MockURLConnection(getFakeURL(), false));

        sut.connect();

        assertTrue(sut.isConnected());
    }

    @Test
    public void shouldNotConnectWhenAlreadyConnected() throws Exception {
        MockURLConnection mockURLConnection = new MockURLConnection(getFakeURL(), false);
        EndpointConnector sut = new EndpointConnector(mockURLConnection);

        sut.connect();
        sut.connect();
        sut.connect();

        assertTrue(sut.isConnected());
        assertEquals(1, mockURLConnection.getExecutionCounter());
    }

    @Test(expected = IOException.class)
    public void shouldLogError() throws Exception {
        EndpointConnector sut = new EndpointConnector(new MockURLConnection(getFakeURL(), true));

        sut.connect();
        fail();
    }

    @Test
    public void shouldReturnValidStringFromConnection() throws IOException {
        EndpointConnector sut = new EndpointConnector(new MockURLConnection(getFakeURL(), false));

        sut.connect();
        String result = sut.readResponse();

        assertEquals("ABCDEFGHIJ", result);
    }

    @Test
    public void shouldReturnEmptyStringOnException() throws IOException {
        EndpointConnector sut = new EndpointConnector(new MockURLConnection(getFakeURL(), false, true));

        sut.connect();
        String result = sut.readResponse();

        assertEquals("", result);
    }

    @Test(expected = ConnectException.class)
    public void shouldThrowExceptionWhenReadingNotConnected() throws IOException {
        EndpointConnector sut = new EndpointConnector(new MockURLConnection(getFakeURL(), false));

        String result = sut.readResponse();
        fail();
    }

    class MockURLConnection extends URLConnection {
        private int executionCounter = 0;

        private boolean isThrowExceptionOnConnect;
        private boolean isThrowExceptionOnInputStream;

        protected MockURLConnection(URL url, boolean isThrowException) {
            super(url);
            this.isThrowExceptionOnConnect = isThrowException;
        }

        protected MockURLConnection(URL url, boolean isThrowException, boolean isThrowExceptionOnInputStream) {
            super(url);
            this.isThrowExceptionOnInputStream = isThrowExceptionOnInputStream;
            this.isThrowExceptionOnConnect = isThrowException;
        }

        @Override
        public void connect() throws IOException {
            if (isThrowExceptionOnConnect)
                throw new IOException("Cannot connect");
            if (executionCounter == 0) {
                executionCounter++;
                return;
            }
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (isThrowExceptionOnInputStream)
                throw new IOException("Could not open stream.");
            return new MockInputStream();
        }

        public int getExecutionCounter() {
            return executionCounter;
        }
    }

    class MockInputStream extends InputStream {
        private String mockContent = "ABCDEFGHIJ";
        private int pointer;

        @Override
        public int read() throws IOException {
            if (pointer >= mockContent.length())
                return -1;
            return mockContent.charAt(pointer++);
        }
    }

    public static URL getFakeURL() {
        try {
            return new URL("http://example.com");
        } catch (MalformedURLException e) {
            return null;
        }
    }

}