package com.pw.logic;

import org.json.JSONObject;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class JsonArrayIteratorTest {

    private String jsonArray = "[{" +
            "id: 10," +
            "name: test}," +
            "{" +
            "id: 20," +
            "name: test2}]";

    private String emptyJsonArray = "[]";

    @Test
    public void shouldReturnTrueIfThereIsMoreElements() {
        JsonParser jsonParser = new JsonParser(jsonArray);
        JsonArrayIterator jsonArrayIterator = jsonParser.parse();

        boolean result = jsonArrayIterator.hasNext();

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfThereIsNoMoreElements() {
        JsonParser jsonParser = new JsonParser(emptyJsonArray);
        JsonArrayIterator jsonArrayIterator = jsonParser.parse();

        boolean result = jsonArrayIterator.hasNext();

        assertFalse(result);
    }

    @Test
    public void shouldReturnNextElementIfThereIsMoreElements() {
        JsonParser jsonParser = new JsonParser(jsonArray);
        JsonArrayIterator jsonArrayIterator = jsonParser.parse();
        JSONObject jsonObject = new JSONObject("{id: 10,name: test}");

        JSONObject result = jsonArrayIterator.next();

        assertNotNull(result);
        assertEquals(jsonObject.toString(), result.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionIfThereIsNoMoreElements() {
        JsonParser jsonParser = new JsonParser(emptyJsonArray);
        JsonArrayIterator jsonArrayIterator = jsonParser.parse();

        JSONObject result = jsonArrayIterator.next();
        fail();
    }

}