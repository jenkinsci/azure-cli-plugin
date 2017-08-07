package com.azure.azurecli;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

public class Command extends AbstractDescribableImpl<Command> {

    public String script;

    @DataBoundConstructor
    public Command(String script) {
        this.script = script;

    }

    public String getScript() {
        return script;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Command> {
        public String getDisplayName() {
            return "test";
        }
    }
}
