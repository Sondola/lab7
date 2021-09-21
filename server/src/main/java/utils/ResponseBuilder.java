package utils;

public class ResponseBuilder {
    private static StringBuilder builder = new StringBuilder();

    public static void append(String msg) {
        builder.append(msg).append("\n");
    }

    public static void appendError(String msg) {
        builder.append("Error: ").append(msg).append("\n");
    }

    public static String getBuilder() {
        return builder.toString();
    }

    public static void delete() {
        builder.delete(0, builder.capacity());
    }
}
