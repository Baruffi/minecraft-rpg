package br.com.rafaelfaustini.minecraftrpg.utils;

public class TimeUtil {
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getCooldownTime(long cooldownSeconds) {
        return System.currentTimeMillis() + (1000 * cooldownSeconds);
    }

    public static long toSeconds(long timestamp) {
        return timestamp / 1000;
    }
}
