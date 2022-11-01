package asserts;

public class AssertsCommon {

    protected static String getMessage(String fieldName) {
        return String.join(" ", fieldName, "does not match");
    }
}
