package com.azure.azurecli;

public class ExitResult {
    public String output;
    public int code;

    public ExitResult(String output, int code) {
        this.output = output;
        this.code = code;
    }
}
