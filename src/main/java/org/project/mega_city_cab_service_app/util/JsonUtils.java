//package org.project.mega_city_cab_service_app.util;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
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
//        // Look for the key in the JSON string
//        int keyIndex = jsonInput.indexOf("\"" + key + "\"");
//        if (keyIndex == -1) {
//            System.out.println("Key '" + key + "' not found in JSON input.");
//            return null; // Key not found
//        }
//
//        // Find the start of the value
//        int valueStartIndex = jsonInput.indexOf(":", keyIndex) + 1;
//        if (valueStartIndex == -1) {
//            System.out.println("Invalid JSON format: No value found for key '" + key + "'.");
//            return null; // Invalid JSON
//        }
//
//        // Find the end of the value
//        int valueEndIndex = jsonInput.indexOf(",", valueStartIndex);
//        if (valueEndIndex == -1) {
//            valueEndIndex = jsonInput.indexOf("}", valueStartIndex);
//        }
//        if (valueEndIndex == -1) {
//            System.out.println("Invalid JSON format: Could not find end of value for key '" + key + "'.");
//            return null; // Invalid JSON
//        }
//
//        // Extract the value and remove quotes and whitespace
//        String value = jsonInput.substring(valueStartIndex, valueEndIndex).trim();
//        if (value.startsWith("\"") && value.endsWith("\"")) {
//            value = value.substring(1, value.length() - 1); // Remove surrounding quotes
//        }
//
//        // Remove any remaining escape characters (e.g., \" becomes ")
//        value = value.replace("\\\"", "\"");
//
//        // Debugging: Log the extracted value
//        System.out.println("Extracted value for key '" + key + "': " + value);
//
//        return value;
//    }
//
//    /**
//     * Parses a LocalDateTime from a string in ISO-8601 format (e.g., "2023-10-15T09:00:00").
//     *
//     * @param dateTimeStr The date-time string to parse.
//     * @return A LocalDateTime object, or null if parsing fails.
//     */
//    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
//        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
//            System.out.println("Date-time string is null or empty.");
//            return null;
//        }
//
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeStr, formatter);
//            System.out.println("Parsed LocalDateTime: " + parsedDateTime);
//            return parsedDateTime;
//        } catch (Exception e) {
//            System.out.println("Failed to parse date-time string: " + dateTimeStr);
//            e.printStackTrace();
//            return null; // Return null if parsing fails
//        }
//    }
//
//    /**
//     * Validates if the given range string is well-formed.
//     *
//     * @param rangeJson The range string to validate.
//     * @return true if the range string is valid, false otherwise.
//     */
//    public static boolean isValidRangeFormat(String rangeJson) {
//        if (rangeJson == null || rangeJson.isEmpty()) {
//            System.out.println("Range string is null or empty.");
//            return false;
//        }
//
//        // Ensure there are no extra quotes
//        rangeJson = rangeJson.trim().replaceAll("^\"|\"$", "");
//
//        // Regex to validate the range format: e.g., "200-250=50,250-300=80"
//        String regex = "^(\\d+-\\d+=\\d+(\\.\\d+)?)(,\\d+-\\d+=\\d+(\\.\\d+)?)*$";
//        boolean isValid = rangeJson.matches(regex);
//
//        if (!isValid) {
//            System.out.println("Invalid range format: " + rangeJson);
//        }
//
//        return isValid;
//    }
//}

/// //////////////////////////////////////////////////////////////////////////////////
/// ///////////////////////////////////////////////////////////////////////////////////////
//package org.project.mega_city_cab_service_app.util;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class JsonUtils {
//    /**
//     * Extracts the value of a specific key from a JSON string.
//     *
//     * @param jsonInput The JSON input as a string.
//     * @param key       The key whose value needs to be extracted.
//     * @return The value of the key, or null if the key is not found.
//     */
//    public static String extractValueFromJson(String jsonInput, String key) {
//        // Look for the key in the JSON string
//        int keyIndex = jsonInput.indexOf("\"" + key + "\"");
//        if (keyIndex == -1) {
//            return null; // Key not found
//        }
//
//        // Find the start of the value
//        int valueStartIndex = jsonInput.indexOf(":", keyIndex) + 1;
//        if (valueStartIndex == -1) {
//            return null; // Invalid JSON
//        }
//
//        // Find the end of the value
//        int valueEndIndex = jsonInput.indexOf(",", valueStartIndex);
//        if (valueEndIndex == -1) {
//            valueEndIndex = jsonInput.indexOf("}", valueStartIndex);
//        }
//        if (valueEndIndex == -1) {
//            return null; // Invalid JSON
//        }
//
//        // Extract the value and remove quotes and whitespace
//        String value = jsonInput.substring(valueStartIndex, valueEndIndex).trim();
//        if (value.startsWith("\"") && value.endsWith("\"")) {
//            value = value.substring(1, value.length() - 1); // Remove surrounding quotes
//        }
//
//        return value;
//    }
//
//    /**
//     * Parses a LocalDateTime from a string in ISO-8601 format (e.g., "2023-10-15T09:00:00").
//     *
//     * @param dateTimeStr The date-time string to parse.
//     * @return A LocalDateTime object, or null if parsing fails.
//     */
//    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
//        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
//            return null;
//        }
//
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//            return LocalDateTime.parse(dateTimeStr, formatter);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null; // Return null if parsing fails
//        }
//    }
//
//    /**
//     * Converts an object to a JSON string.
//     *
//     * @param obj The object to serialize.
//     * @return A JSON representation of the object.
//     */
//    public static String toJson(Object obj) {
//        if (obj instanceof List<?>) {
//            List<?> list = (List<?>) obj;
//            StringBuilder json = new StringBuilder("[");
//            for (Object item : list) {
//                json.append(toJson(item)).append(",");
//            }
//            if (!list.isEmpty()) {
//                json.deleteCharAt(json.length() - 1); // Remove trailing comma
//            }
//            json.append("]");
//            return json.toString();
//        } else if (obj instanceof Type) {
//            Type type = (Type) obj;
//            return "{\"id\": " + type.getId() +
//                    ", \"typeName\": \"" + escapeJsonString(type.getTypeName()) + "\"}";
//        } else {
//            return "{}"; // Unsupported object type
//        }
//    }
//
//    private static String escapeJsonString(String str) {
//        if (str == null) {
//            return "";
//        }
//        return str.replace("\\", "\\\\")
//                .replace("\"", "\\\"")
//                .replace("\n", "\\n")
//                .replace("\r", "\\r")
//                .replace("\t", "\\t");
//    }
//
//}

/// ///////////////

package org.project.mega_city_cab_service_app.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.model.VehiclePlan;


public class JsonUtils {

    /**
     * Extracts the value of a specific key from a JSON string.
     *
     * @param jsonInput The JSON input as a string.
     * @param key       The key whose value needs to be extracted.
     * @return The value of the key, or null if the key is not found.
     */
    public static String extractValueFromJson(String jsonInput, String key) {
        if (jsonInput == null || key == null || key.isEmpty()) {
            return null;
        }

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

    /**
     * Parses a LocalDateTime from a string in ISO-8601 format (e.g., "2023-10-15T09:00:00").
     *
     * @param dateTimeStr The date-time string to parse.
     * @return A LocalDateTime object, or null if parsing fails.
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param obj The object to serialize.
     * @return A JSON representation of the object.
     */
    public static String toJson(Object obj) {
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            StringBuilder json = new StringBuilder("[");
            for (Object item : list) {
                json.append(toJson(item)).append(",");
            }
            if (!list.isEmpty()) {
                json.deleteCharAt(json.length() - 1); // Remove trailing comma
            }
            json.append("]");
            return json.toString();
        } else if (obj instanceof VehiclePlan) {
            VehiclePlan vehiclePlan = (VehiclePlan) obj;
            return "{\"id\": " + vehiclePlan.getId() +
                    ", \"planName\": \"" + escapeJsonString(vehiclePlan.getPlanName()) + "\"}";
        } else if (obj instanceof DistanceRange) {
            DistanceRange distanceRange = (DistanceRange) obj;
            return "{\"id\": " + distanceRange.getId() +
                    ", \"minDistance\": " + distanceRange.getMinDistance() +
                    ", \"maxDistance\": " + distanceRange.getMaxDistance() + "}";
        } else {
            return "{}"; // Unsupported object type
        }
    }

    /**
     * Escapes special characters in a JSON string.
     *
     * @param str The string to escape.
     * @return The escaped string.
     */
    private static String escapeJsonString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}


/// ////////////
//
//
//












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
