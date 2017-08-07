/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.azurecli.helpers;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.EnvironmentContributingAction;
import hudson.model.InvisibleAction;

public class PublishEnvVarAction extends InvisibleAction implements EnvironmentContributingAction {

    /**
     * The environment variable key.
     */
    private String key;

    /**
     * The environment variable value.
     */
    private String value;

    /**
     * Constructor.
     *
     * @param key   the environment variable key
     * @param value the environment variable value
     */
    public PublishEnvVarAction(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /* (non-Javadoc)
     * @see hudson.model.EnvironmentContributingAction#buildEnvVars(hudson.model.AbstractBuild, hudson.EnvVars)
     */
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
        env.put(key, value);
    }

}
