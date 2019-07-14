package World16.Utils;

import java.util.HashMap;
import java.util.Map;

public class Tag {

    //Maps
    static Map<String, Map<String, String>> tagsMap = SetListMap.tagsMap;
    //...
    //Lists
    //...

    public static void addTag(String key, String tag, String string) {
        tagsMap.computeIfAbsent(key, k -> new HashMap<>());

        tagsMap.get(key).put(tag, string);
    }

    public static String getTag(String key, String tag) {
        tagsMap.computeIfAbsent(key, k -> new HashMap<>());

        return tagsMap.get(key).get(tag);
    }

    public static void removeTag(String key, String tag) {
        tagsMap.computeIfAbsent(key, k -> new HashMap<>());

        tagsMap.get(key).remove(tag);
    }

    public static void removeEverything(String key) {
        tagsMap.remove(key);
    }

    public static void removeEverything() {
        tagsMap.clear();
    }
}