package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Stash {

    private final static Map<String, Object> storage = new HashMap<>();

    public static void putLocal(String consumer, String key, Object value) {
        storage.put(consumer + "." + key, value);
    }

    public static void putGlobal(String key, Object value) {
        storage.put(key, value);
    }

    public static Object get(String key) {
        return getOrDefault(key, null);
    }

    public static Object get(String consumer, String key) {
        return getOrDefault(consumer, key, null);
    }

    public static Object getOrDefault(String key, Object defaultValue) {
        return storage.getOrDefault(key, defaultValue);
    }

    public static Object getOrDefault(String consumer, String key, Object defaultValue) {
        return storage.getOrDefault(consumer + "." + key, defaultValue);
    }


}
