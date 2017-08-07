/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.azurecli;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.Run;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Command extends AbstractDescribableImpl<Command> {

    private String script;
    private String exportVariablesString;

    @DataBoundConstructor
    public Command(String script, String exportVariablesString) {
        this.script = script;
        this.exportVariablesString = exportVariablesString;
    }

    public void parseExportedVariables(PrintStream logger, Run<?, ?> build, String output) throws IOException {

        if (exportVariablesString == null) {
            return;
        }
        if (exportVariablesString.trim().equals("")) {
            return;
        }
        logger.println("Transforming to environment variables: " + exportVariablesString);
        HashMap<String, String> exportVariablesNames = com.azure.azurecli.helpers.Utils.parseExportedVariables(exportVariablesString);
        HashMap<String, String> exportVariables = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        JsonNode rootNode = mapper.readTree(output);
        for (Map.Entry<String, String> var
                :
                exportVariablesNames.entrySet()) {

            String value = rootNode.at(var.getKey()).asText();
            exportVariables.put(var.getValue(), value);
        }
        com.azure.azurecli.helpers.Utils.setEnvironmentVariables(build, exportVariables);
    }

    public String getScript() {
        return script;
    }

    public String getExportVariablesString() {
        return exportVariablesString;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Command> {
        public String getDisplayName() {
            return "Command";
        }
    }
}

