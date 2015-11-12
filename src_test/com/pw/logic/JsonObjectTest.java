package com.pw.logic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonObjectTest {

    private String jsonArray = "{" +
            "id: 10," +
            "name: test}";

    @Test
    public void shouldReturnEmptyStringWithZeroKeys() {
        JsonObject jsonObject = new JsonObject(jsonArray);

        String result = jsonObject.toCSV("|");

        assertEquals("", result);
    }

    @Test
    public void shouldReturnStringWithSeparators() {
        JsonObject jsonObject = new JsonObject(jsonArray);

        String result = jsonObject.toCSV("|", "id", "name");

        assertEquals("10|test", result);
    }

}