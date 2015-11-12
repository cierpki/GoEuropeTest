package com.pw.logic;

import com.pw.log.Logger;
import org.json.JSONArray;
import org.json.JSONException;

public class JsonParser {

    private static final Logger logger = Logger.getLogger(JsonParser.class);

    private String toParse;

    public JsonParser(String toParse) {
        this.toParse = toParse;
    }

    /**
     * Parses the String created during the object creation.
     * Creates JsonArrayIterator from the given JsonArray.
     *
     * @return  - JsonArrayIterator created from parsed String
     *          or empty array if JSONException occured
     */
    public JsonArrayIterator parse() {
        try {
            JSONArray jsonArray = new JSONArray(toParse);
            return new JsonArrayIterator(jsonArray);
        } catch (JSONException ex) {
            logger.log("Could not parse input JSON:\n" + toParse);
            return new JsonArrayIterator(new JSONArray("[]"));
        }
    }



}
