package org.project.mega_city_cab_service_app.util;

public class JsonUtils {
    /**
     * Extracts the value of a specific key from a JSON string.
     *
     * @param jsonInput The JSON input as a string.
     * @param key       The key whose value needs to be extracted.
     * @return The value of the key, or null if the key is not found.
     */
    public static String extractValueFromJson(String jsonInput, String key) {
        // Look for the key in the JSON string
        int keyIndex = jsonInput.indexOf("\"" + key + "\"");
        if (keyIndex == -1) {
            return null; // Key not found
        }

        // Find the start of the value
        int valueStartIndex = jsonInput.indexOf(":", keyIndex) + 1;
        if (valueStartIndex == -1) {
            return null; // Invalid JSON
        }

        // Find the end of the value
        int valueEndIndex = jsonInput.indexOf(",", valueStartIndex);
        if (valueEndIndex == -1) {
            valueEndIndex = jsonInput.indexOf("}", valueStartIndex);
        }
        if (valueEndIndex == -1) {
            return null; // Invalid JSON
        }

        // Extract the value and remove quotes and whitespace
        String value = jsonInput.substring(valueStartIndex, valueEndIndex).trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1); // Remove surrounding quotes
        }

        return value;
    }
}

//package org.example.mega_city_cab_service_app.util;
//
//public class JsonUtils {
//
//    /**
//     * Extracts the value of a specific key from a JSON string.
//     *
//     * @param jsonInput The JSON input as a string.
//     * @param key       The key whose value needs to be extracted.
//     * @return The value of the key, or null if the key is not found.
//     */
//    public static String extractValueFromJson(String jsonInput, String key) {
//        if (jsonInput == null || key == null) {
//            return null; // Avoid NullPointerException
//        }
//
//        // Format the key for search: "key":
//        String searchKey = "\"" + key + "\"";
//
//        // Find key in JSON string
//        int keyIndex = jsonInput.indexOf(searchKey);
//        if (keyIndex == -1) {
//            return null; // Key not found
//        }
//
//        // Locate the start of the value (after ":")
//        int colonIndex = jsonInput.indexOf(":", keyIndex);
//        if (colonIndex == -1) {
//            return null; // Invalid JSON
//        }
//
//        // Find start of value (skip spaces)
//        int valueStartIndex = colonIndex + 1;
//        while (valueStartIndex < jsonInput.length() &&
//                (jsonInput.charAt(valueStartIndex) == ' ' || jsonInput.charAt(valueStartIndex) == '"')) {
//            valueStartIndex++;
//        }
//
//        // Find end of value (comma or closing brace)
//        int valueEndIndex = valueStartIndex;
//        while (valueEndIndex < jsonInput.length() &&
//                jsonInput.charAt(valueEndIndex) != ',' &&
//                jsonInput.charAt(valueEndIndex) != '}') {
//            valueEndIndex++;
//        }
//
//        // Extract value and remove trailing quotes/spaces
//        String value = jsonInput.substring(valueStartIndex, valueEndIndex).trim();
//        if (value.startsWith("\"") && value.endsWith("\"")) {
//            value = value.substring(1, value.length() - 1); // Remove surrounding quotes
//        }
//
//        return value.isEmpty() ? null : value;
//    }
//}
