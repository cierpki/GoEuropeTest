package com.pw.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonParserTest {

    private String jsonArray = "[{" +
            "id: 10," +
            "name: test}," +
            "{" +
            "id: 20," +
            "name: test2}]";

    @Test
    public void shouldReturnValidArrayIteratorForValidJSON() {
        JsonParser sut = new JsonParser(jsonArray);

        JsonArrayIterator result = sut.parse();

        assertTrue(result.hasNext());
    }

    @Test
    public void shouldEmptyArrayIteratorForInValidJSON() {
        JsonParser sut = new JsonParser("Not valid");

        JsonArrayIterator result = sut.parse();

        assertFalse(result.hasNext());
    }

}