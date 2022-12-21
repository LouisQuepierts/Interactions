package net.quepierts.interactions.main.utils;

public class Time {
    public static long begin = System.currentTimeMillis();

    public static long getRuntimeMillis() {
        return System.currentTimeMillis() - begin;
    }

    public static long getRuntimeSeconds() {
        return getRuntimeMillis() / 1000;
    }

    public static long getRuntimeTicks() {
        return getRuntimeMillis() / 50;
    }
}
