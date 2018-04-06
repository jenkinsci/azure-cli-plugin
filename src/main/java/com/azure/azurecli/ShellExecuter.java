/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.azurecli;

import com.azure.azurecli.exceptions.AzureCloudException;
import com.azure.azurecli.exceptions.AzureCredentialsValidationException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ShellExecuter {

    public PrintStream logger;

    public ShellExecuter(PrintStream logger) {
        this.logger = logger;
    }

    public ShellExecuter() {

    }

    public void login(com.azure.azurecli.helpers.CredentialsCache credentialsCache) throws AzureCredentialsValidationException {
        String command = "az login --service-principal -u " + credentialsCache.clientId + " -p " + credentialsCache.clientSecret + " --tenant " + credentialsCache.tenantId;
        try {
            executeAZ(command, false);
            command = "az account set -s " + credentialsCache.subscriptionId;
            executeAZ(command, false);
        } catch (AzureCloudException e) {
            throw new AzureCredentialsValidationException(e.getMessage());
        }
    }

    public String getVersion() throws AzureCloudException {
        String command = "az --version";
        ExitResult result = executeCommand(command);
        if (result.code == 0) {
            return result.output;
        }
        throw AzureCloudException.create("Azure CLI not found");
    }

    public String executeAZ(String command, Boolean printCommand) throws AzureCloudException {
        if (printCommand) {
            logger.println("Running: " + command);
        }
        ExitResult result = executeCommand(command);
        if (result.code == 0) {
            logger.println(result.output);
            return result.output;
        }
        throw AzureCloudException.create(result.output);
    }

    private static class ExitResult {
        public String output;
        public int code;

        ExitResult(String output, int code) {
            this.output = output;
            this.code = code;
        }
    }

    private ExitResult executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        int exitCode = -1;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();

            InputStream stream;

            if (p.exitValue() != 0) {
                stream = p.getErrorStream();
            } else {
                stream = p.getInputStream();
            }
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(stream, "utf-8"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            exitCode = p.exitValue();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return new ExitResult(output.toString(), exitCode);
    }
}
