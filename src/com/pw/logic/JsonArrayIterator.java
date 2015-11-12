package com.pw.logic;

import org.json.JSONArray;

import java.util.NoSuchElementException;

public class JsonArrayIterator {

    private JSONArray jsonArray;
    private int iteration;
    private int size;

    public JsonArrayIterator(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.size = jsonArray.length();
    }

    /**
     * Checks if this iterator has more elements to iterate.
     *
     * @return  - true if more elements, false otherwise
     */
    public boolean hasNext() {
        return iteration < size;
    }

    /**
     * Returns next object for this iterator.
     * Throws NoSuchElementException if no more elements found
     *
     * @return  - next JsonObject for this iterator
     */
    public JsonObject next() {
        if (iteration == size)
            throw new NoSuchElementException();
        return new JsonObject(jsonArray.getJSONObject(iteration++));
    }

}
