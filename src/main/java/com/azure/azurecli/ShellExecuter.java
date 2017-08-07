package com.azure.azurecli;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellExecuter {

    public ExitResult login(String id, String secret, String tenantId) {
        String command = "az login --service-principal -u " + id + " -p " + secret + " --tenant " + tenantId;
        return executeAZ(command);
    }

    public ExitResult getVersion() {
        String command = "az --version";
        return executeAZ(command);
    }

    public ExitResult executeAZ(String command) {
        return executeCommand(command);

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
                    new BufferedReader(new InputStreamReader(stream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            exitCode = p.exitValue();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return new ExitResult(output.toString(), exitCode);
    }
}
