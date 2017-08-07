/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.azurecli.helpers;

import hudson.EnvVars;
import hudson.model.Run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String REGEX = "\\$\\{(.+?)}";

    public static List<String> extractTokens(String text) {
        List<String> tokens = new ArrayList();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            tokens.add(matcher.group(0).replaceAll("\\$", "").replaceAll("\\{", "").replaceAll("}", ""));
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
                builder.append(replacement);
            }
            i = matcher.end();
        }
        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }

    public static String getEnvVar(EnvVars envVars, String var) {
        String env = System.getenv(var);
        if (envVars != null && (env == null || env.equals(""))) {
            env = envVars.get(var);
        }
        return env;
    }

    public static HashMap<String, String> parseExportedVariables(String exportedVariablesString) {
        HashMap<String, String> envVariables = new HashMap<>();
        String[] rawValues = exportedVariablesString.trim().split(",");
        for (String rawValue
                :
                rawValues) {
            String[] values = rawValue.trim().split("\\|");
            envVariables.put(values[0].trim(), values[1].trim());
        }
        return envVariables;
    }


    public static void setEnvironmentVariables(Run<?, ?> build, HashMap<String, String> environmentVariables) {

        for (Map.Entry<String, String> var
                :
                environmentVariables.entrySet()) {
            build.addAction(new PublishEnvVarAction(var.getKey(), var.getValue()));
        }
    }

    public String getREGEX() {
        return REGEX;
    }

}
