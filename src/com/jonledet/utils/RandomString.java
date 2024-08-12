package com.jonledet.utils;

public class RandomString {
    public static String[] strings = {
        "lose",
        "rainbow",
        "tract",
        "degree",
        "sample",
        "sound",
        "interference",
        "treatment",
        "sandwich",
        "kick",
        "acquisition",
        "strike",
        "chauvinist",
        "east",
        "cheap",
        "slab",
        "aloof",
        "athlete",
        "excess",
        "fat",
        "unrest",
        "consumption",
        "flexible",
        "chaos",
        "revolution"
    };

    public static String getRandomString() {
        return strings[Tools.random.nextInt(0, 25)];
    }
}
