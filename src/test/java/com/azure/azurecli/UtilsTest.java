package com.azure.azurecli;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.util.HashMap;
import java.util.List;

public class UtilsTest {

    private static final String VMName = "Ubuntu_0";
    @Rule
    public final EnvironmentVariables environmentVariables
            = new EnvironmentVariables();

    @Before
    public void setup() {
        environmentVariables.set("NAME", VMName);

    }

    @Test
    public void extractTokens() throws Exception {

        String command = "az vm create ${NAME}";

        List<String> tokens = com.azure.azurecli.helpers.Utils.extractTokens(command);
        Assert.assertEquals(tokens.size(), 1);
    }

    @Test
    public void tokenizeText() throws Exception {

        HashMap<String, String> replacements = new HashMap();
        replacements.put("NAME", VMName);
        String command = "az vm create ${NAME}";

        String tokenized = com.azure.azurecli.helpers.Utils.tokenizeText(command, replacements);
        Assert.assertEquals(tokenized, "az vm create " + VMName);
    }

    @Test
    public void parseEnvVars() throws Exception {

        String output = "location | LOCATION , name | NAME, /user.name | username";

        HashMap<String, String> map = com.azure.azurecli.helpers.Utils.parseExportedVariables(output);
        Assert.assertEquals(map.containsKey("location"), true);
        Assert.assertEquals(map.containsKey("name"), true);
        Assert.assertEquals(map.containsKey("/user.name"), true);


    }

    @Test
    public void getEnvVar() throws Exception {

        String envvar = "NAME";
        String var = com.azure.azurecli.helpers.Utils.getEnvVar(null, envvar);
        Assert.assertNotNull(var);
        Assert.assertEquals(var, VMName);

    }

}