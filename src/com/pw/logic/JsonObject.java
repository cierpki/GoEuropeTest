package com.pw.logic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonObject extends JSONObject {

    public JsonObject(String source) throws JSONException {
        super(source);
    }

    public JsonObject(JSONObject jsonObject) {
        super(jsonObject.toString());
    }

    /**
     * Creates a CSV string for given separator and keys.
     *
     * @param separator     - the csv separator
     * @param keys          - keys to take values for
     * @return              - csv string
     */
    public String toCSV(String separator, String ...keys) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            sb.append(getValue(separator, keys[i]));
            if (i < keys.length - 1)
                sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * Gets the value for a given key.
     * If object hold by a key is a JSONObject, creates a CSV string
     * from its values
     *
     * @param separator - the csv separator
     * @param key       - the key to take value for
     * @return          - value hold by a given key
     */
    private Object getValue(String separator, String key) {
        Object object = get(key);
        if (object instanceof JSONObject)
            return new JsonObject((JSONObject) object).toString(separator);
        return object;
    }

    /**
     * Creates a csv string created from all values for this JSON Object
     *
     * @param separator - CSV separator
     * @return          - csv String
     */
    public String toString(String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> keys = keys();
        while (keys.hasNext()) {
            String key = keys.next();
            sb.append(get(key));
            sb.append(separator);
        }
        return sb.toString();
    }
}
