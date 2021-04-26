package me.shir.uhcp.util;

public class TimeUtils
{
    public static String setFormat(final Integer value) {
        final int remainder = value * 1000;
        final int seconds = remainder / 1000 % 60;
        final int minutes = remainder / 60000 % 60;
        final int hours = remainder / 3600000 % 24;
        return String.valueOf((hours > 0) ? String.format("%02d:", hours) : "") + String.format("%02d:%02d", minutes, seconds);
    }
}
