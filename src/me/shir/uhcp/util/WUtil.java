package me.shir.uhcp.util;

public class WUtil
{
    public static String capitalizeFully(String str) {
        str = str.toLowerCase();
        return capitalize(str);
    }
    
    public static String capitalize(final String str) {
        final char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; ++i) {
            final char ch = buffer[i];
            if (isDelimiter(ch, null)) {
                capitalizeNext = true;
            }
            else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }
    
    private static boolean isDelimiter(final char ch, final char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (final char delimiter : delimiters) {
            if (ch == delimiter) {
                return true;
            }
        }
        return false;
    }
}
