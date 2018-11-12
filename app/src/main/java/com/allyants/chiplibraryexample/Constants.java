package com.allyants.chiplibraryexample;

import java.util.regex.Pattern;

public class Constants {
    public static final String server = "https://tindev.serveo.net/backend";
    public static final Pattern usernameRegex = Pattern.compile("^[a-z\\d]+$", Pattern.CASE_INSENSITIVE);

    public static String endpoint(String end) {
        return server + end;
    }
}
