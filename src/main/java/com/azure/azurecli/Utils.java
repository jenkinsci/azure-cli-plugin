package com.azure.azurecli;

import hudson.EnvVars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String REGEX = "\\{(.+?)}";

    public static List<String> extractTokens(String text) {
        List<String> tokens = new ArrayList();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            tokens.add(matcher.group(0));
        }
        return tokens;
    }

    public static String tokenizeText(String text, HashMap<String, String> replacements) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(text);

        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String replacement = replacements.get(matcher.group(1));
            builder.append(text.substring(i, matcher.start()));
            if (replacement == null) {
                builder.append(matcher.group(0));
            } else {
                builder.append('"' + replacement + '"');
            }
            i = matcher.end();
        }
        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }

    public static String getEnvVar(EnvVars envVars, String var) {
        String env = System.getenv(var);
        if (envVars != null && env == null || env == "") {
            env = envVars.get(var);
        }
        return env;
    }

    public String getREGEX() {
        return REGEX;
    }

}
