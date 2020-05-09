import java.lang.management.PlatformLoggingMXBean;

public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (pattern == null) {
            return -1;
        }
        int pLen = pattern.length();

        // invalid pattern string, empty
        if (pLen == 0) {
            return -1;
        }
        RollingString patRS = new RollingString(pattern, pLen);

        // invalid pattern string, too long
        if (pLen > input.length()) {
            return -1;
        }
        RollingString inputRS = new RollingString(input.substring(0, pLen), pLen);
        for (int i = pLen; i <= input.length(); i++) {
            if (inputRS.hashCode() == patRS.hashCode()) {
                if (inputRS.equals(patRS)) {
                    return i - pLen;
                }
            }
            if (i == input.length()) {
                return -1;
            }
            inputRS.addChar(input.charAt(i));
        }
        return -1;
    }

}
